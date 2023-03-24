package com.xwh.gatewey.config.swagger;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: SwaggerProvider
 * @Description: swagger聚合后，路由 等信息定义
 * @date 2020年1月18日 下午3:24:02
 */
@Component("swagger3")
@EnableConfigurationProperties(SwaggerProperties.class)
@Primary
public class SwaggerProvider implements SwaggerResourcesProvider {
    final RouteLocator routeLocator;
    final GatewayProperties gatewayProperties;
    public static final String OAS_URI = "v2/api-docs";

    @Resource
    private SwaggerProperties swaggerProperties;

    public SwaggerProvider(RouteLocator routeLocator, GatewayProperties gatewayProperties) {
        this.routeLocator = routeLocator;
        this.gatewayProperties = gatewayProperties;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        Set<String> routes = new HashSet<>();
        //取出Spring Cloud Gateway中的route
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
        //结合application.yml中的路由配置，只获取有效的route节点
        gatewayProperties.getRoutes().stream().filter(
                routeDefinition -> (
                        routes.contains(routeDefinition.getId()) && swaggerProperties.isShow(routeDefinition.getId())
                )
        ).forEach(routeDefinition -> routeDefinition.getPredicates().stream()
                .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                .forEach(
                        predicateDefinition ->
                                resources.add(
                                        swaggerResource(
                                                routeDefinition.getId(),
                                                predicateDefinition
                                                        .getArgs()
                                                        .get(
                                                                NameUtils
                                                                        .GENERATED_NAME_PREFIX
                                                                        + "0")
                                                        .replace(
                                                                "**",
                                                                OAS_URI)))));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("3.0");
        return swaggerResource;
    }
}
