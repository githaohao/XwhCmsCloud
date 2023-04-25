package com.xwh.article;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;


/**
 * @author xiangwenhao
 */
@EnableAsync
@RefreshScope
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.xwh"})
public class ArticleApplication{
    public static void main(String[] args) {
        SpringApplication.run(ArticleApplication.class, args);
    }

}

