package com.xwh.system.properties;

/**
 * @author xiangwenhao
 * @create 2022-01-16 18:42
 **/


import com.wf.captcha.*;
import com.wf.captcha.base.Captcha;
import com.xwh.core.exception.FailException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Objects;

@Data
@Component
public class LoginProperties {

    /**
     * 账号单用户 登录
     */
    private boolean singleLogin = false;

    private LoginCode loginCode;

    /**
     * 用户登录信息缓存
     */
    private boolean cacheEnable;

    public boolean isSingleLogin() {
        return singleLogin;
    }

    public boolean isCacheEnable() {
        return cacheEnable;
    }

    /**
     * 获取验证码生产类
     *
     * @return /
     */
    public Captcha getCaptcha() {
        if (Objects.isNull(loginCode)) {
            loginCode = new LoginCode();
            if (Objects.isNull(loginCode.getCodeType())) {
                loginCode.setCodeType(LoginCodeEnum.arithmetic);
            }
        }
        return switchCaptcha(loginCode);
    }

    /**
     * 依据配置信息生产验证码
     *
     * @param loginCode 验证码配置信息
     * @return /
     */
    private Captcha switchCaptcha(LoginCode loginCode) {
        Captcha captcha;
        synchronized (this) {
            switch (loginCode.getCodeType()) {
                case arithmetic -> {
                    // 算术类型 https://gitee.com/whvse/EasyCaptcha
                    captcha = new FixedArithmeticCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    // 几位数运算，默认是两位
                    captcha.setLen(loginCode.getLength());
                }
                case chinese -> {
                    captcha = new ChineseCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    captcha.setLen(loginCode.getLength());
                }
                case chinese_gif -> {
                    captcha = new ChineseGifCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    captcha.setLen(loginCode.getLength());
                }
                case gif -> {
                    captcha = new GifCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    captcha.setLen(loginCode.getLength());
                }
                case spec -> {
                    captcha = new SpecCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    captcha.setLen(loginCode.getLength());
                }
                default -> throw new FailException("验证码配置信息错误！正确配置查看 LoginCodeEnum ");
            }
        }
        if(StringUtils.isNotBlank(loginCode.getFontName())){
            captcha.setFont(new Font(loginCode.getFontName(), Font.PLAIN, loginCode.getFontSize()));
        }
        return captcha;
    }

    static class FixedArithmeticCaptcha extends ArithmeticCaptcha {
        public FixedArithmeticCaptcha(int width, int height) {
            super(width, height);
        }

        @Override
        protected char[] alphas() {
            // 生成随机数字和运算符
            int n1 = num(1, 10), n2 = num(1, 10);
            int opt = num(3);

            // 计算结果
            int res = new int[]{n1 + n2, n1 - n2, n1 * n2}[opt];
            // 转换为字符运算符
            char optChar = "+-x".charAt(opt);

            this.setArithmeticString(String.format("%s%c%s=?", n1, optChar, n2));
            this.chars = String.valueOf(res);

            return chars.toCharArray();
        }
    }
}
