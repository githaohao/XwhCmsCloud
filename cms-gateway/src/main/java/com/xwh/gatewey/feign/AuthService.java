package com.xwh.gatewey.feign;

import com.xwh.core.dto.Result;
import com.xwh.gatewey.feign.fallback.AuthServiceFallbackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cms-system", fallback = AuthServiceFallbackImpl.class)
public interface AuthService {
	/**
	 * token 校验
	 *
	 * @param authToken tokenid
	 * @return AjaxResult
	 */
	@RequestMapping(value = { "/auth/checkToken" }, method = RequestMethod.POST)
    Result checkToken(@RequestParam(value = "authToken") String authToken);



	/**
	 * token 校验
	 *
	 * @param authToken tokenid
	 * @return AjaxResult
	 */
	@PostMapping("/auth/checkAuthorize")
    Result checkAuthorize(@RequestParam(value = "userInfoStr") String userInfoStr,
                          @RequestParam(value = "type") String type,
                          @RequestParam(value = "path") String path);

}
