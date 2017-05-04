package com.lancefallon.usermgmt.config.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lancefallon.usermgmt.config.exception.model.DatabaseException;
import com.lancefallon.usermgmt.config.security.repository.UserRepository;

@Service
public class UserMetaDataService {

	@Autowired
	private UserRepository userRepository;
	
	public boolean updateUserLoginInfo(String username, String host, String addr) throws DatabaseException{
		return this.userRepository.updateUserLoginMetaData(username, host, addr);
	}
	
}
