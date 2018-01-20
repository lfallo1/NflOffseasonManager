package com.lancefallon.usermgmt.config.security.service;

import com.lancefallon.usermgmt.config.security.model.UserPrivileges;
import com.lancefallon.usermgmt.config.security.model.UserRole;
import com.lancefallon.usermgmt.config.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        //set user props
        user.setAuthenticated(true);
        user.setUsername(user.getUsername());
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setAuthenticated(true);
        user.setEnabled(true);
        user.setAlias(user.getUsername());

        //add user roles
        List<UserRole> userRoles = this.userRepository.getUserRoles(user);
        final List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.addAll(userRoles.stream().map(r -> new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toList()));
        user.setAuthorities(grantedAuthorities);

        //return user
        return user;
    }

}
