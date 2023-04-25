package com.xwh.article;

import com.xwh.article.feign.SystemService;
import com.xwh.core.utils.FindClassesByPackage;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.core.config.Order;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component("resourceApplication")
@Order(99)
@RequiredArgsConstructor
public class ResourceApplicationRunner implements ApplicationRunner {

    final SystemService systemService;
    final String service = "article";
    final String serviceTitle = "文章管理";
    final String servicePack = "com.xwh.article.controller";


    @Override
    public void run(ApplicationArguments args) {
        String apiByPackage = FindClassesByPackage.findApiByPackage(servicePack, service, serviceTitle);
        systemService.saveResourceIsUpdate(apiByPackage,service);
    }
}
