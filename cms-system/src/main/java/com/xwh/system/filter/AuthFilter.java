//package com.xwh.system.filter;
//
//import com.xwh.core.exception.FailException;
//import com.xwh.core.properties.JwtProperties;
//import com.xwh.system.manager.TokenManager;
//import com.xwh.core.utils.JwtTokenUtil;
//import io.jsonwebtoken.JwtException;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * 对客户端请求的jwt token验证过滤器
// * @author xiangwenhao
// */
//@Component
//@Slf4j
//public class AuthFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Autowired
//    private JwtProperties jwtProperties;
//
//    @Value("${authNoInterceptURL}")
//    private String authNoInterceptURL;
//
//	@Autowired
//	private TokenManager tokenManager;
//
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//    	String servletPath = request.getServletPath();
//        //不拦截/swagger文档
//		if (servletPath.contains("/swagger") || servletPath.contains("/webjars") || servletPath.contains("/api-docs") || servletPath.contains("/doc.html")
//				|| servletPath.contains("/images") || servletPath.contains("/favicon.ico")) {
//            chain.doFilter(request, response);
//            return;
//        }
//        logger.info("接口访问地址："+servletPath);
//		if (servletPath.contains("/" + jwtProperties.getAuthPath())) {
//            chain.doFilter(request, response);
//            return;
//        }
//        //不拦截的地址
//        if(StringUtils.isNotBlank(authNoInterceptURL)){
//        	String [] authNoInterceptURLArr=authNoInterceptURL.split(",");
//        	for(String sniua:authNoInterceptURLArr){
//        		if (servletPath.contains(sniua)){
//        			chain.doFilter(request, response);
//                    return;
//        		}
//        	}
//        }
//
//        final String requestHeader = request.getHeader(jwtProperties.getHeader());
//        String authToken = null;
//        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
//            authToken = requestHeader.substring(7);
//            //验证token是否过期
//            try {
//                String key=tokenManager.getKey(authToken);
//                if (key==null) {
//                    throw new FailException("token过期");
//                }
//                boolean flag = jwtTokenUtil.isTokenExpired(authToken);
//                if (flag) {
//                    throw new FailException("token过期");
//
//                }
//            } catch (JwtException e) {
//                //有异常就是token解析失败
//                throw new FailException("token验证失败");
//            }
//        } else {
//            //header没有带Bearer字段
//            throw new FailException("token验证失败");
//        }
//        chain.doFilter(request, response);
//    }
//}
