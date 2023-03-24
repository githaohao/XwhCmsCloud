package com.xwh.system.service.impl;

import com.xwh.core.dto.Result;
import com.xwh.core.properties.JwtProperties;
import com.xwh.core.utils.RedisUtils;
import com.xwh.system.service.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author xiangwenhao
 * @create 2022-02-10 02:04
 **/
@Service
@RequiredArgsConstructor
public class CaptchaServiceImpl implements CaptchaService {

    final RedisUtils redisUtils;
    final JwtProperties jwtProperties;

    @Override
    public Result verifyCode(String captcha, String captchaId) {
        if (StringUtils.isNotBlank(captcha) && StringUtils.isNotBlank(captchaId)) {
            String uuid = jwtProperties.getCodeKey() + captchaId;
            String captchaCache = String.valueOf(redisUtils.get(uuid));
            if (captcha.equalsIgnoreCase(captchaCache)) {
                redisUtils.del(uuid);
            } else {
                return Result.fail("验证码错误!");
            }
        }else {
            return Result.fail("验证码不能为空！");
        }
        return Result.success("验证成功");
    }
}
