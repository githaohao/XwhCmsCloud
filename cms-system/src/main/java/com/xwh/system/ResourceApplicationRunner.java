package com.xwh.system;

import com.xwh.core.utils.FindClassesByPackage;
import com.xwh.system.service.SysResourceService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.core.config.Order;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Order(99)
@RequiredArgsConstructor
public class ResourceApplicationRunner implements ApplicationRunner {

    final SysResourceService sysResourceService;

    final String service = "sys";
    final String serviceTitle = "系统管理";
    final String servicePack = "com.xwh.system.controller";
    @Override
    public void run(ApplicationArguments args) {
        String apiByPackage = FindClassesByPackage.findApiByPackage(servicePack, service, serviceTitle);
        sysResourceService.saveResourceIsUpdate(apiByPackage,service);
    }
}
