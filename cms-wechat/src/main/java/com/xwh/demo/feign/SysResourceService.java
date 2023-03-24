package com.xwh.demo.feign;

import com.xwh.core.dto.Result;
import com.xwh.demo.feign.fallback.SysResourceServiceFallbackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author xwh
 **/

@FeignClient(name = "cms-system", fallback = SysResourceServiceFallbackImpl.class)
public interface SysResourceService {

    @PostMapping("/resource/addAll/{service}")
    Result saveResourceIsUpdate(@RequestBody List<String> apiList, @PathVariable String service);

}
