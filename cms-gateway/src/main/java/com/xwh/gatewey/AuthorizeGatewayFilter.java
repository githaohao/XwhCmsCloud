package com.xwh.gatewey;

import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSON;
import com.xwh.core.dto.Result;
import com.xwh.core.utils.BlankUtils;
import com.xwh.gatewey.feign.SystemUserService;
import com.xwh.gatewey.properties.IgnoreUrlsProperties;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class AuthorizeGatewayFilter implements GlobalFilter, Ordered {
    private final Log logger = LogFactory.getLog(this.getClass());

    //认证的头部
    private static final String AUTHORIZE_TOKEN = "Authorization";
    //UTF-8 字符集
    public static final String UTF8 = "UTF-8";

    @Resource
    @Lazy
    private SystemUserService systemService;

    @Autowired
    private IgnoreUrlsProperties ignoreUrlsProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        String path = request.getPath().value();
        logger.info("请求网关地址：" + path);
        Result res = new Result();
        try {
            if (isIgnoreHttpUrls(path)) {
                return chain.filter(exchange);
            }

            final String authorization = headers.getFirst(AUTHORIZE_TOKEN);
            if (authorization != null && authorization.startsWith("Bearer ")) {
                String authToken = authorization.substring(7);
                Result result = systemService.checkToken(authToken);
                int code = result.getCode();
                // 认证（校验）成功的
                if (code == HttpStatus.HTTP_OK) {
                    String userInfo = String.valueOf(result.getData());
                    // 查询当前的授权
                    ServerHttpRequest.Builder builder = request.mutate();
                    //转发的请求都加上服务间认证token
                    builder.header(AUTHORIZE_TOKEN, authorization);
                    //将jwt token中的用户信息传给服务
                    builder.header("userInfo", userInfo);
                    ServerWebExchange mutableExchange = exchange.mutate().request(builder.build()).build();

                    // 查询是否是超级管理员
                    String isAdmin = "";
                    if (BlankUtils.isNotBlank(userInfo)) {
                        String[] userInfoArr = userInfo.split(";;");
                        if (userInfoArr.length > 3) {
                            isAdmin = userInfoArr[3];
                        }
                    }
                    //超级管理员直接莫仍所有授权
                    if (StringUtils.isNotBlank(isAdmin) && "1".equals(isAdmin)) {
                        return chain.filter(mutableExchange);
                    }
                    // 不是超级管理员查询授权
                    String type = request.getMethod().name().toLowerCase();
                    Result i = systemService.checkAuthorize(userInfo, type, path);

                    if (i.getCode() == HttpStatus.HTTP_OK){
                        return chain.filter(mutableExchange);
                    }

                    res.setCode(401);
                    res.setMessage("没有权限！");
                    logger.info("没有权限！");
                    return unauthorizedResponse(exchange, res);
                } else if (code == HttpStatus.HTTP_BAD_REQUEST) {
                    logger.info("认证失败！");
                    return unauthorizedResponse(exchange, res);
                } else if (code == HttpStatus.HTTP_UNAUTHORIZED) {
                    logger.info("未授权！");
                }
            } else {
                logger.info("认证信息不为空！");
                res.setCode(-1);
                res.setMessage("认证信息不为空！");
                return unauthorizedResponse(exchange, res);
            }

        } catch (Exception e) {
            logger.info("网关异常：" + e);
            res.setCode(-1);
            res.setMessage("网关异常：" + e);
            return unauthorizedResponse(exchange, res);
        }

        return chain.filter(exchange);
    }

    /**
     * @param @param  servletPath
     * @param @return
     * @return boolean
     * @throws
     * @Title：isIgnoreHttpUrls
     * @Description: 请求地址是否为需要忽略token验证的地址
     */
    private boolean isIgnoreHttpUrls(String servletPath) {
        boolean rt = false;
        List<String> ignoreHttpUrls = ignoreUrlsProperties.getHttpUrls();
        for (String ihu : ignoreHttpUrls) {
            if (servletPath.contains(ihu)) {
                rt = true;
                break;
            }
        }
        return rt;
    }

    /**
     * 认证失败时，返回json数据
     *
     * @param
     */
    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, Result result) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        originalResponse.setStatusCode(org.springframework.http.HttpStatus.OK);
        originalResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        byte[] response = null;
        response = JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = originalResponse.bufferFactory().wrap(response);
        return originalResponse.writeWith(Flux.just(buffer));
    }

    @Override
    public int getOrder() {
        return -100;
    }

}
