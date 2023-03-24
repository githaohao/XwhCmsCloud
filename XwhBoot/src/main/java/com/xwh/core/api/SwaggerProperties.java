package com.xwh.core.api;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {
    private String scanPackage;

    private String title;

    private String description;

    private String version;

    private Boolean enable = true;

    private String developerName;

    private String homepage;

    private String email;
}
