package com.lancefallon.usermgmt.config.security.service;

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
	private PasswordEncoder passwordEncoder;
	
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
			
			//check application's db for the user
			UserPrivileges user = (UserPrivileges) userDetailsService.loadUserByUsername(username.toString());

			if(passwordEncoder.checkpw(password.toString(), user.getPassword())){
				auth = new CustomUserPasswordAuthenticationToken(user.getUsername(),
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
