package com.lancefallon.usermgmt.config.security.service;

import javax.naming.InvalidNameException;
import javax.naming.directory.DirContext;
import javax.naming.ldap.LdapName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

/**
 * interact with ldap server
 * @author lancefallon
 *
 */
@Service
public class LdapService {
	
	private static final String BASE_DN = "ou=patrons,dc=inflinx,dc=com"; //specify the base distinguished name (dn)
	
	@Autowired
	private LdapTemplate ldapTemplate;
	
	/**
	 * find user by a field and their password (in theory, the field should only ever be mail or uid)
	 * @param principal
	 * @param password
	 * @param field
	 * @return
	 */
	public DirContext checkAuth(String principal, String password, String field)  {
		DirContext ctx = null;
		try{
			ContextSource contextSource = ldapTemplate.getContextSource();
			
			//perform actual authentication
			ctx = contextSource.getContext(generateRdn(field ,principal), password);
			return ctx;
		} catch(NamingException | InvalidNameException e){
			return null;
		}
	}
	
	/**
	 * retrieve a value by full rdn
	 * @param attrName
	 * @param attrValue
	 * @return
	 * @throws InvalidNameException
	 */
	private <T>String generateRdn(String attrName, T attrValue) throws InvalidNameException {
		LdapName ldapName = new LdapName(BASE_DN);
		LdapNameBuilder builder = LdapNameBuilder.newInstance(ldapName);
		builder.add(attrName, attrValue);
		return builder.build().toString();
	}
	
}
