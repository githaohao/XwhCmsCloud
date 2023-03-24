package com.xwh.system.validator.impl;

import com.xwh.core.exception.FailException;
import com.xwh.core.utils.RedisUtils;
import com.xwh.system.entity.SysUser;
import com.xwh.system.entity.vo.AuthRequestVo;
import com.xwh.system.service.CaptchaService;
import com.xwh.system.service.SysLoginLogService;
import com.xwh.system.service.SysUserService;
import com.xwh.system.validator.IReqValidator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 账号密码验证
 *
 * @author fhh
 * @date 2019-08-23 12:34
 */
@Service
@RequiredArgsConstructor
public class DbValidator implements IReqValidator {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    final SysUserService sysUserService; // 系统用户信息接口
    final CaptchaService captchaService;

    final SysLoginLogService sysLoginLogService;// 系统用户登录日志接口
    final RedisUtils redisUtils;// 系统用户登录日志接口

    @Override
    public SysUser validate(AuthRequestVo authRequestVo) {
        String loginType = authRequestVo.getLogintype();
        //登录类型
        if (StringUtils.isNotBlank(loginType)) {
            String userName = authRequestVo.getUsername();
            String password = authRequestVo.getPassword();
            // 1  后台系统用户登录
            if ("1".equals(loginType)) {
                // 验证码
                String captcha = authRequestVo.getCaptcha();
                // 验证码id
                String captchaId = authRequestVo.getCaptchaId();
//				Result result = captchaService.verifyCode(captcha, captchaId);
//				if (result.getCode() != HttpStatus.HTTP_OK) {
//					return result;
//				}
                SysUser user = sysUserService.login(userName, password);
                //  不为空表示登录成功
                if (ObjectUtils.isNotEmpty(user)) {
                    // 用户名和密码正确,登录成功
                    sysLoginLogService.saveLoginLog(user.getUsername(), 1, 1, 1, "登录成功！！");
                    logger.info(user.getUsername() + "登录成功！");
                    return user;
                } else {
                    // 登录失败
                    sysLoginLogService.saveLoginLog(userName, 1, 2, 1, "登录失败，用户名或密码错误！");
                    logger.info("登录失败，用户名或密码错误！");
                    throw new FailException("登录失败，用户名或密码错误！");
                }
            } else {//登录类型错误
                logger.info("登录类型错误");
                throw new FailException("登录类型错误");
            }
        } else {//登录类型不能为空
            logger.info("登录类型不能为空");
            throw new FailException("登录类型不能为空");
        }
    }
}
