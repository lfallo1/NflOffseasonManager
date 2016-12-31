package com.lancefallon.usermgmt.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

import com.lancefallon.usermgmt.NflDraftApplication;

@Configuration
public class WebInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
		return application.sources(NflDraftApplication.class);
	}
	
}
