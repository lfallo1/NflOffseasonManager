package com.lancefallon.usermgmt.config.security;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.lancefallon.usermgmt.config.security.model.CustomUserPasswordAuthenticationToken;
import com.lancefallon.usermgmt.config.security.model.UserPrivileges;

@Component
public class CustomTokenEnhancer implements TokenEnhancer {

	private static final Logger LOGGER = Logger.getLogger(CustomTokenEnhancer.class);

	private List<TokenEnhancer> delegates = Collections.emptyList();

	public void setTokenEnhancers(List<TokenEnhancer> delegates) {
		this.delegates = delegates;
	}

	/**
	 * Creating customized response token
	 * 
	 * @param accessToken
	 * @param authentication
	 * @return OAuth2AccessToken
	 */
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		LOGGER.info("Inside enhance of CustomTokenEnhancer");

		final DefaultOAuth2AccessToken tempResult = (DefaultOAuth2AccessToken) accessToken;

		if (authentication != null) {
			
			UserPrivileges userPrivileges = null;
			try{
				//when retrieving an access token
				userPrivileges = ((CustomUserPasswordAuthenticationToken) authentication
						.getUserAuthentication()).getUserPrivileges();
			} catch(ClassCastException e){
				//when retrieving a refresh token
				userPrivileges = (UserPrivileges) authentication.getPrincipal();
			}
			userPrivileges.setPassword("");

			final Map<String, Object> additionalInformation = new HashMap<String, Object>();

			additionalInformation.put("user_details", userPrivileges);
			tempResult.setAdditionalInformation(additionalInformation);

		}
		OAuth2AccessToken result = tempResult;
		for (TokenEnhancer enhancer : delegates) {
			result = enhancer.enhance(result, authentication);
		}
		return result;
	}

}
