package com.lancefallon.usermgmt.config.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * helper class to store app properties. this class should be created as a bean,
 * and fill the properties
 * 
 * @author lancefallon
 *
 */
@Component
public class AppProperties {

	@Value("${email.host}")
	private String emailHost;

	@Value("${email.port}")
	private int emailPort;

	@Value("${datasource.url.default}")
	private String dbDefaultUrl;

	@Value("${datasource.url.backup}")
	private String dbBackupUrl;

	@Value("${datasource.username}")
	private String dbUsername;

	@Value("${datasource.password}")
	private String dbPassword;

	@Value("${datasource.driver}")
	private String dbDriver;

	@Value("${flyway.locations}")
	private String flywayLocations;

	public String getEmailHost() {
		return emailHost;
	}

	public int getEmailPort() {
		return emailPort;
	}

	public String getDbDefaultUrl() {
		return dbDefaultUrl;
	}

	public String getDbBackupUrl() {
		return dbBackupUrl;
	}

	public String getDbUsername() {
		return dbUsername;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public String getDbDriver() {
		return dbDriver;
	}

	public String getFlywayLocations() {
		return flywayLocations;
	}

}
