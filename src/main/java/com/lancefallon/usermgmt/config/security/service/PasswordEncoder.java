package com.lancefallon.usermgmt.config.security.service;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoder {

    /**
     * Encrypt string using bcrypt
     *
     * @param password
     * @return
     */
    public String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    /**
     * compare a password with a hash
     *
     * @param candidate
     * @param hash
     * @return
     */
    public Boolean checkpw(String candidate, String hash) {
        return BCrypt.checkpw(candidate, hash);
    }	
	
}
