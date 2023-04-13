package com.xwh.article.feign;

import com.xwh.article.feign.fallback.SysUserServiceFallbackImpl;
import com.xwh.core.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 用户
 *
 * @author xwh
 **/
@FeignClient(name = "cms-system", fallback = SysUserServiceFallbackImpl.class)
public interface SysUserService {


    @GetMapping("/user/{id}")
    public Result findById(@PathVariable String id);
}
