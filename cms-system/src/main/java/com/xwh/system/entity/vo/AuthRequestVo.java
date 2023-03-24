package com.xwh.system.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 认证的请求dto
 *
 * @Date 2019/8/24 14:00
 */
@ApiModel
@Getter
@Setter
@ToString
public class AuthRequestVo {

	/**
	 * 用户名
	 */
	@ApiModelProperty("用户名")
    private String username;
    /**
     * 密码
     */
	@ApiModelProperty("密码")
    private String password;

	/**
     * 验证码
     */
	@ApiModelProperty("验证码")
    private String captcha;

	/**
	 * 验证码id
	 */
	@ApiModelProperty("验证码id")
	private String captchaId;

    /**
	 * 登录类型:1.后台系统登录,2.admin
	 */
	@ApiModelProperty("登录类型:1.后台系统登录")
    private String logintype;

}
