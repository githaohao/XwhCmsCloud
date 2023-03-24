package com.xwh.core.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xwh.core.interceptor.RequiredPermissionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.TimeZone;

/**
 *
 * @ClassName: MvcConfig
 * @Description: 权限拦截配置
 */
@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
	@Bean
	public RequiredPermissionInterceptor requiredPermissionInterceptor() {
		return new RequiredPermissionInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(requiredPermissionInterceptor()).addPathPatterns("/**");
	}


	/**
	 * 统一输出风格
	 * See {@link com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy} for details.
	 * @param converters
	 */
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		for (int i = 0; i < converters.size(); i++) {
			if (converters.get(i) instanceof MappingJackson2HttpMessageConverter) {
				ObjectMapper objectMapper = new ObjectMapper();
				// 统一返回数据的输出风格
//				objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategy.SnakeCaseStrategy());
				objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
				MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
				converter.setObjectMapper(objectMapper);
				converters.set(i, converter);
				break;
			}
		}
	}

}
