package com.xwh.demo;

import com.xwh.core.utils.FindClassesByPackage;
import com.xwh.demo.feign.SysResourceService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.core.config.Order;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@Order(99)
@RequiredArgsConstructor
public class ResourceApplicationRunner implements ApplicationRunner {

    @Resource
    @Lazy
    final SysResourceService sysResourceService;
    final String service = "article";
    final String serviceTitle = "demo";
    final String servicePack = "com.xwh.article.controller";

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }


//    @Override
//    public void run(ApplicationArguments args) {
//        List<String> apiByPackage = FindClassesByPackage.findApiByPackage(servicePack, service, serviceTitle);
//        sysResourceService.saveResourceIsUpdate(apiByPackage,service);
//    }
}
