package com.xwh.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author xiangwenhao
 * @create 2022-01-15 01:15
 **/

@SpringBootApplication(scanBasePackages = {"com.xwh"})
@EnableAsync
@RefreshScope
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}
