package com.pg.iloveblog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/summernoteImage/**") // 해당 경로로 접근하면
		        .addResourceLocations("file:///C:/springboot/upload/") //실제로는 해당 경로로 접근하는 것이다. 
		        .setCachePeriod(30); //캐시 지속 시간 30초
		//WebMvcConfigurer.super.addResourceHandlers(registry);
	}
	
}
