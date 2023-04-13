package com.xwh.demo;

import com.xwh.core.utils.FindClassesByPackage;
import com.xwh.demo.feign.SysResourceService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.core.config.Order;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(99)
@RequiredArgsConstructor
public class ResourceApplicationRunner implements ApplicationRunner {

    final SysResourceService sysResourceService;
    final String service = "demo";
    final String serviceTitle = "微信管理";
    final String servicePack = "com.xwh.demo.controller";


    @Override
    public void run(ApplicationArguments args) {
        String apiByPackage = FindClassesByPackage.findApiByPackage(servicePack, service, serviceTitle);
        sysResourceService.saveResourceIsUpdate(apiByPackage,service);
    }
}
