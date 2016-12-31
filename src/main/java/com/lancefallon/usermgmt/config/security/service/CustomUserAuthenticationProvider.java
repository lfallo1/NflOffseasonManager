package com.lancefallon.usermgmt.config.security.service;

import javax.naming.directory.DirContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lancefallon.usermgmt.config.security.model.CustomUserPasswordAuthenticationToken;
import com.lancefallon.usermgmt.config.security.model.UserPrivileges;

/**
 * custom handler during auth process
 * @author lancefallon
 *
 */
@Component("customUserAuthenticationProvider")
public class CustomUserAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private LdapService ldapService;

	private static final Logger LOGGER = Logger.getLogger(CustomUserAuthenticationProvider.class);

	/**
	 * try to authenticate
	 */
	@Override
	@Transactional
	public Authentication authenticate(Authentication authentication) {

		LOGGER.info("Inside #CustomUserAuthenticationProvider");

		CustomUserPasswordAuthenticationToken auth = null;
		if (authentication != null) {

			final Object username = authentication.getPrincipal();
			final Object password = authentication.getCredentials();
			
			//try to retrieve user by username/pwd, and also email/pwd
			DirContext ctx = this.ldapService.checkAuth(username.toString(), password.toString(), "uid");
			if(ctx == null){
				ctx = this.ldapService.checkAuth(username.toString(), password.toString(), "mail");
				if(ctx == null){
					return null;
				}
			}
			
			//if a result was returned, check application's db for the user
			UserPrivileges user = (UserPrivileges) userDetailsService.loadUserByUsername(username.toString());

			// if a user was returned, create a new auth object and add the UserPrivileges to the object
			if (user != null) {
				auth = new CustomUserPasswordAuthenticationToken(authentication.getPrincipal(),
						authentication.getCredentials(), user.getAuthorities());
				auth.setUserPrivileges(user);
			}
		}
		
		//return the auth. if null, same as no auth
		return auth;
	}

	@Override
	public boolean supports(final Class<? extends Object> authentication) {
		return (UsernamePasswordAuthenticationToken.class).isAssignableFrom(authentication);
	}

}
