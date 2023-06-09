package com.xwh.article.feign.fallback;

import com.xwh.article.feign.SystemUserService;
import com.xwh.core.dto.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author xwh
 **/


public class SystemServiceFallbackImpl implements SystemUserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Result findById(String id){
        logger.error("调用{}异常:{}", "service", id);
        return  Result.fail();
    }

    @Override
    public Result saveResourceIsUpdate(String apiList, String service) {
        logger.error("调用{}异常:{}", "service", apiList);
        return null;
    }
}
