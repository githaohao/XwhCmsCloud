package com.xwh.gatewey.config.swagger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @ClassName: SwaggerProperties
 * @Description: swagger聚合配置
 * @date 2020年1月18日 下午3:30:32
 */
@ConfigurationProperties("com.xwh.swagger")
@RefreshScope
public class SwaggerProperties {

	// Swagger返回JSON文档的接口路径（全局配置）
	private String apiDocsPath = "v2/api-docs";

	// Swagger文档版本（全局配置）
	private String swaggerVersion = "3.0";

	// 自动生成文档的路由名称，设置了generateRoutes之后，ignoreRoutes不生效
	private Set<String> generateRoutes = new HashSet<>();

	// 不自动生成文档的路由名称，设置了generateRoutes之后，本配置不生效
	private Set<String> ignoreRoutes = new HashSet<>();

	/**
	 * 是否显示该路由
	 */
	public boolean isShow(String route) {
		int generateRoutesSize = generateRoutes.size();
		int ignoreRoutesSize = ignoreRoutes.size();

		if (generateRoutesSize > 0 && !generateRoutes.contains(route)) {
			return false;
		}

        return ignoreRoutesSize <= 0 || !ignoreRoutes.contains(route);
    }

	public String getApiDocsPath() {
		return apiDocsPath;
	}

	public void setApiDocsPath(String apiDocsPath) {
		this.apiDocsPath = apiDocsPath;
	}

	public String getSwaggerVersion() {
		return swaggerVersion;
	}

	public void setSwaggerVersion(String swaggerVersion) {
		this.swaggerVersion = swaggerVersion;
	}

	public Set<String> getGenerateRoutes() {
		return generateRoutes;
	}

	public void setGenerateRoutes(Set<String> generateRoutes) {
		this.generateRoutes = generateRoutes;
	}

	public Set<String> getIgnoreRoutes() {
		return ignoreRoutes;
	}

	public void setIgnoreRoutes(Set<String> ignoreRoutes) {
		this.ignoreRoutes = ignoreRoutes;
	}
}
