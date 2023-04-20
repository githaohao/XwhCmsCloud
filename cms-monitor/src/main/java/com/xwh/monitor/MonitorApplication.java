package com.xwh.monitor;

import com.xwh.monitor.feign.SysResourceService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.Resource;

/**
 * @author xwh
 * 系统监控
 **/
@EnableAsync
@RefreshScope
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.xwh"})
public class MonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorApplication.class, args);
    }

    @Resource
    SysResourceService sysResourceService;

//    @Bean
//    public void updateResources(){
//        final String service = "article";
//        final String serviceTitle = "文章管理";
//        final String servicePack = "com.xwh.article";
//        List<String> apiByPackage = FindClassesByPackage.findApiByPackage(servicePack, service, serviceTitle);
//        sysResourceService.saveResourceIsUpdate(apiByPackage,service);
//    }
}
