package com.lancefallon.usermgmt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.lancefallon.usermgmt.config.interceptor.ApiInterceptor;

/**
 * extend WebMvcConfigurerAdapter, to add interceptors.
 * could potentially override more methods in future 
 */
@Configuration
public class WebConfigurer extends WebMvcConfigurerAdapter {

	@Autowired
	private ApiInterceptor apiInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(apiInterceptor).addPathPatterns("/api*/**");
		super.addInterceptors(registry);
	}

}
