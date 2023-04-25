package com.xwh.gatewey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xiangwenhao
 * @create 2022-01-15 16:38
 **/

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@RefreshScope
@EnableFeignClients
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
