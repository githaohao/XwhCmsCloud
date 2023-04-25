package com.xwh.system.controller;

import com.xwh.core.controller.BaseController;
import com.xwh.core.dto.Result;
import com.xwh.core.properties.JwtProperties;
import com.xwh.core.utils.BlankUtils;
import com.xwh.core.utils.JwtTokenUtil;
import com.xwh.core.utils.TokenUtil;
import com.xwh.system.entity.SysUser;
import com.xwh.system.entity.vo.AuthRequestVo;
import com.xwh.system.manager.TokenManager;
import com.xwh.system.service.SysLoginLogService;
import com.xwh.system.service.SysRoleResourceService;
import com.xwh.system.service.SysUserRoleService;
import com.xwh.system.service.SysUserService;
import com.xwh.system.validator.impl.DbValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "系统:系统授权")
@Slf4j
public class LoginController extends BaseController {

    final JwtTokenUtil jwtTokenUtil;
    final TokenManager tokenManager;

    final JwtProperties jwtProperties;
    final DbValidator reqValidator;
    final SysLoginLogService sysLoginLogService;
    final SysUserService sysUserService;
    final SysUserRoleService sysUserRoleService;
    final SysRoleResourceService sysRoleResourceService;


    @PostMapping("login")
    @Operation(summary = "登录验证")
    public Result auth(@RequestBody AuthRequestVo authRequestVo) {
        try {
            SysUser user = reqValidator.validate(authRequestVo);
            final String randomKey = jwtTokenUtil.getRandomKey();
            // 验证成功
            String loginType = authRequestVo.getLogintype();
            String userName = authRequestVo.getUsername();
            String userInfoStr = user.getUserId() + ";;" + userName + ";;" + loginType + ";;" + user.getIsAdmin() + ";;" + user.getTenantId();
            final String token = jwtTokenUtil.generateToken(userInfoStr, randomKey);
            // 存储到redis或者db中
            tokenManager.createRelationship(authRequestVo.getUsername(), token);
            return success().add("token", "Bearer " + token);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("系统异常(检查 redis 或 mysql 是否正常 )！" + e);
            return fail();
        }
    }


    @Operation(summary = "获取当前用户信息")
    @PostMapping("/info")
    public Result userInfo() {
        String token = TokenUtil.getToken();
        // 权限验证通过
        Result result = checkToken(token);
        if (result.getCode() != 200) {
            return result;
        }
        String username = jwtTokenUtil.getUsernameFromToken(token);
        // 获取用户信息
        Map<String, Object> loginDatas = sysUserService.getLoginDatas(username, true);
        return success().add(propertyDel(loginDatas, "menuId", "parentId", "status", "weight", "name", "password", "isAdmin"));
    }


    @Operation(summary = "用户角色的权限查询")
    @PostMapping("/checkAuthorize")
    public Result checkAuthorize(String userInfoStr, String type, String path) {
        String userId = "";
        if (BlankUtils.isNotBlank(userInfoStr)) {
            String[] userInfoArr = userInfoStr.split(";;");
            if (userInfoArr.length > 0) {
                userId = userInfoArr[0];
            }
        }
        //通过用户id 查询当前用户是否有权限
        int i = sysRoleResourceService.checkAuthByUserId(userId, type, path);
        if (i > 0) {
            return success();
        }
        return fail();

    }


    @Operation(summary = "权限验证")
    @PostMapping("/checkToken")
    public Result checkToken(String authToken) {
        String msg = "token未进行认证！";
        if (StringUtils.isNotBlank(authToken)) {
            try {
                String key = tokenManager.getKey(authToken);
                // 是否进行了认证操作
                // 不为空的时候则进行了验证操作
                if (BlankUtils.isNotBlank(key)) {
                    boolean flag = jwtTokenUtil.isTokenExpired(authToken);
                    if (flag) {
                        // 过期则返回为true
                        msg = "token已过期！";
                        log.error(msg);
                        return fail(msg);
                    } else {
                        String userInfoStrFromToken = jwtTokenUtil.getUserInfoStrFromToken(authToken);
                        return success().add(userInfoStrFromToken);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("token失败认证！" + e);
            }
        }
        log.error(msg);
        return fail(msg);

    }

    /**
     * 注销
     *
     * @return
     */
    @PostMapping(value = "/logout")
    @Operation(summary = "用户注销", description = "用户注销")
    public Result logout() {
        // TODO JWT不能从服务端destroy token， logout目前只能在客户端的cookie 或
        // localStorage/sessionStorage remove token
        // TODO
        // 准备用jwt生成永久的token，再结合redis来实现Logout。具体是把token的生命周期交给redis来管理，jwt只负责生成token
        int code = 1;
        String msg = "用户退出失败！";
        try {
            // 多端登录，会有多个同一用户名但token不一样的键值对在redis中存在，所以只能通过token删除
            String authToken = TokenUtil.getToken();
            if (BlankUtils.isNotBlank(authToken)) {
                String userInfoStrFromToken = jwtTokenUtil.getUserInfoStrFromToken(authToken);
                int loginType = TokenUtil.getLoginType(userInfoStrFromToken);
                String username = TokenUtil.getUsernameFromToken(userInfoStrFromToken);
                // 注销成功
                tokenManager.delRelationshipByToken(authToken);
                log.info("用户退出登录成功！");
                sysLoginLogService.saveLoginLog(username, 2, 1, loginType, "用户退出登录成功！");
                return success();
            } else {
                log.info("用户退出登录失败！");
                return fail("用户退出登录失败！");
            }
        } catch (Exception e) {
            log.error("服务内部异常！" + e);
            return fail("服务内部异常！");
        }
    }
}
