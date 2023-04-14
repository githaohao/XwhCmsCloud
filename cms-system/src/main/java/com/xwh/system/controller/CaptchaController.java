package com.xwh.system.controller;

import cn.hutool.core.util.IdUtil;
import com.wf.captcha.base.Captcha;
import com.xwh.core.controller.BaseController;
import com.xwh.core.dto.Result;
import com.xwh.core.properties.JwtProperties;
import com.xwh.core.utils.RedisUtils;
import com.xwh.system.properties.LoginCodeEnum;
import com.xwh.system.properties.LoginProperties;
import com.xwh.system.service.CaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 * @ClassName: CaptchaController
 * @Description: 验证码相关接口
 */
@RestController
@RequestMapping("/captcha")
@RequiredArgsConstructor
@Api(tags = "系统:验证码")
public class CaptchaController extends BaseController {

	final LoginProperties loginProperties;
	final JwtProperties jwtProperties;
	final CaptchaService captchaService;
	final RedisUtils redisUtils;
	/**
	 * @throws Exception
	 *
	 * @Title：captcha
	 * @Description: 获取验证码
	 * @date 2020年1月12日 上午11:15:01
	 * @param @param request
	 * @param @param response
	 * @return void
	 * @throws
	 */
	@ApiOperation("获取验证码")
	@GetMapping(value = "get")
	public Result getCode() {
		// 获取运算的结果
		Captcha captcha = loginProperties.getCaptcha();
		String uuid = jwtProperties.getCodeKey() + IdUtil.simpleUUID();
		//当验证码类型为 arithmetic时且长度 >= 2 时，captcha.text()的结果有几率为浮点型
		String captchaValue = captcha.text();
		if (captcha.getCharType() - 1 == LoginCodeEnum.arithmetic.ordinal() && captchaValue.contains(".")) {
			captchaValue = captchaValue.split("\\.")[0];
		}
		// 保存
		redisUtils.set(uuid, captchaValue, loginProperties.getLoginCode().getExpiration(), TimeUnit.MINUTES);
		// 验证码信息
		Map<String, Object> imgResult = new HashMap<String, Object>(2) {{
			put("img", captcha.toBase64());
			put("uuid", uuid);
		}};
		return success("").add(imgResult);
	}

	@GetMapping(value = "/verify")
	@ApiOperation("验证验证码")
	public Result verifyCode(String captcha,String captchaId) {
		return captchaService.verifyCode(captcha,captchaId);
	}

}
