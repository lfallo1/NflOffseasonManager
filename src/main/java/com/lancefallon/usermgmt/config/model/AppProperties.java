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

	/**
	 * getMysqlDbDriver()).url(appConfig.getDbStudentsUrl())
	 * .username(appConfig.getMysqlUsername()).password(appConfig.
	 * getMysqlPassword()).build();
	 */
	@Value("${datasource.mysql.url}")
	private String dbStudentsUrl;

	@Value("${datasource.mysql.username}")
	private String mysqlUsername;

	@Value("${datasource.mysql.password}")
	private String mysqlPassword;

	@Value("${datasource.mysql.driver}")
	private String mysqlDbDriver;

	// @Value("${email.host}")
	private String emailHost;
	//
	// @Value("${email.port}")
	private int emailPort;

	@Value("${datasource.yt.url}")
	private String dbYoutubeAgentUrl;

	@Value("${datasource.url.default}")
	private String dbDefaultUrl;

	@Value("${datasource.username}")
	private String dbUsername;

	@Value("${datasource.password}")
	private String dbPassword;

	@Value("${datasource.driver}")
	private String dbDriver;

	@Value("${nginx.host}")
	private String nginxHost;

	// @Value("${flyway.locations}")
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

	public String getNginxHost() {
		return nginxHost;
	}

	public String getDbYoutubeAgentUrl() {
		return dbYoutubeAgentUrl;
	}

	public String getDbStudentsUrl() {
		return dbStudentsUrl;
	}

	public String getMysqlUsername() {
		return mysqlUsername;
	}

	public String getMysqlPassword() {
		return mysqlPassword;
	}

	public String getMysqlDbDriver() {
		return mysqlDbDriver;
	}

}
