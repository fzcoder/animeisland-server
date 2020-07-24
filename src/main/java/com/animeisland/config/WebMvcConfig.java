package com.animeisland.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@PropertySource(value = {"classpath:application.properties"}, encoding = "utf-8")
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Value("#{'${http.cors.allowedOrigins}'.split(',')}")
    private String[] allowedOrigins;
	
	//解决跨域问题
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedHeaders("*")
			.allowedOrigins(allowedOrigins)
			.allowedMethods("*")
			.maxAge(3600);
	}
}