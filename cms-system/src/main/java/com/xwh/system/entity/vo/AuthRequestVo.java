package com.xwh.system.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 认证的请求dto
 *
 * @Date 2019/8/24 14:00
 */

@Getter
@Setter
@ToString
@Schema(description = "用户信息")
public class AuthRequestVo {

	/**
	 * 用户名
	 */
	@Schema(name = "用户名")
    private String username;
    /**
     * 密码
     */
	@Schema(name = "密码")
    private String password;

	/**
     * 验证码
     */
	@Schema(name = "验证码")
    private String captcha;

	/**
	 * 验证码id
	 */
	@Schema(name = "验证码id")
	private String captchaId;

    /**
	 * 登录类型:1.后台系统登录,2.admin
	 */
	@Schema(name = "登录类型:1.后台系统登录")
    private String logintype;

}
