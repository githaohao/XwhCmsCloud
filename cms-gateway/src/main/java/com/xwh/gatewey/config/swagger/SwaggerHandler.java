package com.xwh.gatewey.config.swagger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.handler.AsyncPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xiangwenhao
 */
@RestController
public class SwaggerHandler {

    @Autowired(required=false)
    SwaggerResourcesProvider swaggerResources;
    @Autowired(required=false)
    SecurityConfiguration securityConfiguration;
    @Autowired(required=false)
    UiConfiguration uiConfiguration;

    @GetMapping("/swagger-resources/configuration/security")
    public Mono<ResponseEntity<SecurityConfiguration>> securityConfiguration() {
        return Mono.just(new ResponseEntity<>(securityConfiguration, HttpStatus.OK));
    }

    @GetMapping("/swagger-resources/configuration/ui")
    public Mono<ResponseEntity<UiConfiguration>> uiConfiguration() {
        return Mono.just(new ResponseEntity<>(uiConfiguration, HttpStatus.OK));
    }

    @GetMapping("/swagger-resources")
    public Mono<ResponseEntity<?>> swaggerResources() {
        return Mono.just((new ResponseEntity<>(swaggerResources.get(), HttpStatus.OK)));
    }
}
