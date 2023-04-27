package com.xwh.article.feign;

import com.xwh.article.feign.fallback.SystemServiceFallbackImpl;
import com.xwh.core.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户
 *
 * @author xwh
 **/
@FeignClient(name = "cms-system", fallback = SystemServiceFallbackImpl.class)
public interface SystemUserService {


    @GetMapping("/user/{id}")
    public Result findById(@PathVariable String id);


    @PostMapping("/resource/addAll/{service}")
    Result saveResourceIsUpdate(@RequestBody String apiList, @PathVariable String service);

}
