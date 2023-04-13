package com.xwh.article.feign;

import com.xwh.article.feign.fallback.SysResourceServiceFallbackImpl;
import com.xwh.core.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author xwh
 **/

@FeignClient(name = "cms-system", fallback = SysResourceServiceFallbackImpl.class)
public interface SysResourceService {

    @PostMapping("/resource/addAll/{service}")
    Result saveResourceIsUpdate(@RequestBody String apiList, @PathVariable String service);

}
