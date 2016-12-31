package com.lancefallon.usermgmt.config.security.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lancefallon.usermgmt.config.security.model.UserPrivileges;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserPrivileges user = new UserPrivileges();
		user.setAuthenticated(true);
		user.setDbList(Arrays.asList("default", "backup"));
		user.setUserID(1);
		user.setUsername(username);
		user.setDefaultDB("default");
		user.setPresentDB("default");
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setAuthenticated(true);
		user.setEnabled(true);
		final List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		String role = username.equals("lfallo1") ? "ROLE_ADMIN" : "ROLE_USER";
		grantedAuthorities.add(new SimpleGrantedAuthority(role));
		user.setAuthorities(grantedAuthorities);
//		if (user == null) {
//			throw new UsernameNotFoundException("username not found");
//		}
		return user;
	}

}
