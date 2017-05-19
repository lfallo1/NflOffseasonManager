package com.lancefallon.usermgmt.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.lancefallon.usermgmt.player.repository.SSHConnection;

@WebListener
public class MysqlContextListener implements ServletContextListener {

	private SSHConnection conexionssh;

	public MysqlContextListener() {
		super();
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("Context initialized ... !");
		try {
			conexionssh = new SSHConnection();
		} catch (Throwable e) {
			e.printStackTrace(); // error connecting SSH server
		}
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("Context destroyed ... !");
		conexionssh.closeSSH(); // disconnect
	}
}
