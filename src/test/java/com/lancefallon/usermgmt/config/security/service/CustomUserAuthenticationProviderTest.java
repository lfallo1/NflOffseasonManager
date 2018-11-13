package com.lancefallon.usermgmt.config.security.service;

import com.lancefallon.usermgmt.config.security.model.CustomUserPasswordAuthenticationToken;
import com.lancefallon.usermgmt.config.security.model.UserPrivileges;
import com.sun.security.auth.UserPrincipal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class CustomUserAuthenticationProviderTest {

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    CustomUserAuthenticationProvider customUserAuthenticationProvider;

    @Test
    void testAuthenticateFails() {
        UserPrivileges userDetails = new UserPrivileges();
        userDetails.setUsername("testuser");
        userDetails.setPassword("secret");

        Mockito.when(this.userDetailsService.loadUserByUsername(Mockito.anyString())).thenReturn(userDetails);
        Mockito.when(this.passwordEncoder.checkpw(Mockito.anyString(), Mockito.anyString())).thenReturn(false);

        Authentication authentication = new CustomUserPasswordAuthenticationToken(new MockPrincipal("testuser", "secret"), new MockCredentials("user"));
        Authentication result = this.customUserAuthenticationProvider.authenticate(authentication);

        assertNull(result);
    }

    @Test
    void testAuthenticateSuccess() {

    }

    @Test
    void supports() {
    }

    private class MockPrincipal{
        String username;
        String password;

        public MockPrincipal(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    private class MockCredentials {
        String credentials;
        public MockCredentials(String credentials) {
            this.credentials = credentials;
        }

        public String getCredentials() {
            return credentials;
        }

        public void setCredentials(String credentials) {
            this.credentials = credentials;
        }
    }
}