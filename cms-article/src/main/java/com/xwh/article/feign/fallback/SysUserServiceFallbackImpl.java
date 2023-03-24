package com.xwh.article.feign.fallback;

import com.xwh.article.feign.SysUserService;
import com.xwh.core.dto.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @author xwh
 **/

@Service
public class SysUserServiceFallbackImpl implements SysUserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Result findById(String id){
        logger.error("调用{}异常:{}", "service", id);
        return  Result.fail();
    }
}
