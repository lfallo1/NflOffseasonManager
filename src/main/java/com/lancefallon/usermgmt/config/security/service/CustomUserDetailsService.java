package com.lancefallon.usermgmt.config.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lancefallon.usermgmt.config.security.model.UserPrivileges;
import com.lancefallon.usermgmt.config.security.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserPrivileges user = this.userRepository.tryLogin(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("username not found");
		}
		
		user.setAuthenticated(true);
		user.setUsername(username);
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setAuthenticated(true);
		user.setEnabled(true);
		final List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		user.setAuthorities(grantedAuthorities);
		return user;
	}

}
