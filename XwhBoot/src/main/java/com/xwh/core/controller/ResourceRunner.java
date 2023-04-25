package com.xwh.core.controller;

import lombok.Data;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(99)
@Data public class ResourceRunner implements ApplicationRunner {

//    @Value("${project.resource.name}")
//    private String name;
//
//    @Value("${project.resource.packages}")
//    private String packages;
//
//    @Value("${project.resource.title}")
//    private String title;
//

    @Override
    public void run(ApplicationArguments args) {
//        if (StringUtils.isEmpty(packages) || StringUtils.isEmpty(name) || StringUtils.isEmpty(title)) {
            return; // Skip if either service names or controller package is empty
//        }
//        List<String> apiByPackage = FindClassesByPackage.findApiByPackage(services, services, "系统管理");
//        sysResourceService.saveResourceIsUpdate(apiByPackage, services);
    }
}
