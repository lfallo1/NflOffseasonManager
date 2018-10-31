package com.lancefallon.usermgmt.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.lancefallon.usermgmt.config.security.service.CustomUserAuthenticationProvider;

@Configuration
public class OAuth2SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserAuthenticationProvider customUserAuthenticationProvider;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("oauth/authorize", "/oauth/token", "/404.html", "/", "/swagger-ui.html", "swagger-ui.html").permitAll();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(customUserAuthenticationProvider);
	}

}
