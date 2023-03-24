package com.xwh.gatewey.feign.fallback;

import com.xwh.core.dto.Result;
import com.xwh.gatewey.feign.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class AuthServiceFallbackImpl implements AuthService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Result checkToken(@RequestParam(value = "authToken") String authToken) {
		logger.error("调用{}异常:{}", "checkToken", authToken);
		return Result.fail();
	}

	@Override
	public Result checkAuthorize(@RequestParam(value = "userInfoStr") String userInfoStr,
								 @RequestParam(value = "type") String type,
								 @RequestParam(value = "path") String path) {
		logger.error("调用{}异常:{}", "checkAuthorize", userInfoStr);
		return Result.fail();
	}

}
