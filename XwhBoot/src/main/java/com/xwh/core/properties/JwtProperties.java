package com.xwh.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * jwt相关配置
 * @date 2019-08-23 9:23
 */
@Configuration
@ConfigurationProperties(prefix = JwtProperties.JWT_PREFIX)
@Getter
@Setter
public class JwtProperties {

    public static final String JWT_PREFIX = "jwt";

    private String header = "Authorization";

    private String secret = "defaultSecret";

    private Long expiration = 604800L;

    private String authPath = "auth";

    private String md5Key = "random-key-";

    private String onlineKey = "online-key";

    private String codeKey = "code-key-";

}
