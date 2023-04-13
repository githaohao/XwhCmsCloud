package com.xwh.article.feign.fallback;


import com.xwh.article.feign.SysResourceService;
import com.xwh.core.dto.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author xwh
 **/
@Service
public class SysResourceServiceFallbackImpl implements SysResourceService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Result saveResourceIsUpdate(String apiByPackage,String service) {
        logger.error("调用{}异常:{}", "service", service);
        return  Result.fail();
    }
}
