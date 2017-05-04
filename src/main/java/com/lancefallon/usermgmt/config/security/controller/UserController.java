package com.lancefallon.usermgmt.config.security.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lancefallon.usermgmt.config.exception.model.DatabaseException;
import com.lancefallon.usermgmt.config.security.model.CustomUserPasswordAuthenticationToken;
import com.lancefallon.usermgmt.config.security.model.UserPrivileges;
import com.lancefallon.usermgmt.config.security.service.UserMetaDataService;

/**
 * endpoint to retrieve the currently authenticated user
 * 
 * @author lancefallon
 *
 */
@RestController
@RequestMapping("/getuser")
public class UserController {
	
	@Autowired
	private UserMetaDataService userMetaDataService;

	/**
	 * get authenticated user. authentication determined by a valid access_token
	 * 
	 * @param auth
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<UserPrivileges> getUser(OAuth2Authentication auth) {
		try {
			// when retrieving an access token
			CustomUserPasswordAuthenticationToken token = (CustomUserPasswordAuthenticationToken) auth
					.getUserAuthentication();
			return new ResponseEntity<>(token.getUserPrivileges(), HttpStatus.OK);
		} catch (ClassCastException e) {
			// when retrieving a refresh token
			return new ResponseEntity<>((UserPrivileges) auth.getPrincipal(), HttpStatus.OK);
		}
	}
	
	/**
	 * update the user's metadata table
	 * @param auth
	 * @param req
	 * @return
	 * @throws DatabaseException
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Boolean> updateUserMetaData(OAuth2Authentication auth, HttpServletRequest req) throws DatabaseException {
		return new ResponseEntity<>(this.userMetaDataService.updateUserLoginInfo(auth.getName(), req.getRemoteHost(), req.getRemoteAddr()), HttpStatus.OK);
	}

}
