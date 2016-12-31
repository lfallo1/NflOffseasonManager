package com.lancefallon.usermgmt.config.security.service;

import java.util.Map;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

import com.lancefallon.usermgmt.config.security.model.CustomUserPasswordAuthenticationToken;

@Component("authService")
public class RestAuthAnnotationService {
	
	public Boolean isSelf(OAuth2Authentication auth, String username){
		if(auth == null){
			return false;
		}
		CustomUserPasswordAuthenticationToken token = (CustomUserPasswordAuthenticationToken) auth.getUserAuthentication();
		return username.equalsIgnoreCase(token.getUserPrivileges().getUsername());
	}
	
	public Boolean verifyMap(Map<Integer, String> filmsMap, OAuth2Authentication auth){
		System.out.println("map size: " + filmsMap.size());
		CustomUserPasswordAuthenticationToken token = (CustomUserPasswordAuthenticationToken) auth.getUserAuthentication();
		System.out.println(token.getUserPrivileges().getUsername());
		return true;
	}
}
