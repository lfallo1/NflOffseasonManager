package com.lancefallon.usermgmt.config.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lancefallon.usermgmt.config.security.model.CustomUserPasswordAuthenticationToken;
import com.lancefallon.usermgmt.config.security.model.UserPrivileges;

/**
 * endpoint to retrieve the currently authenticated user
 * @author lancefallon
 *
 */
@RestController
@RequestMapping("/getuser")
public class UserController {

	/**
	 * get authenticated user.
	 * authentication determined by a valid access_token
	 * @param auth
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<UserPrivileges> getUser(OAuth2Authentication auth){
		CustomUserPasswordAuthenticationToken token = (CustomUserPasswordAuthenticationToken) auth.getUserAuthentication();
		return new ResponseEntity<>(token.getUserPrivileges(), HttpStatus.OK);
	}
	
}
