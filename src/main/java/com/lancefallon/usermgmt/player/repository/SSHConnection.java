package com.lancefallon.usermgmt.player.repository;

import java.util.Properties;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SSHConnection {

    private String strSshUser = "vagrant"; // SSH loging username
    private String strSshPassword = "vagrant"; // SSH login password
    private String strSshHost = "127.0.0.1"; // hostname or ip or SSH
    private int nSshPort = 2222; // remote SSH host port number
    private String strRemoteHost = "127.0.0.1"; // hostname or ip of your
                                                                 // database server
    private int nLocalPort = 3307; // local port number use to bind SSH tunnel
    private int nRemotePort = 3306; // remote port number of your database

	private Session session; // represents each ssh session

	public void closeSSH() {
		session.disconnect();
	}

	public SSHConnection() throws Throwable {

	       final JSch jsch = new JSch();
	       Session session = jsch.getSession(strSshUser, strSshHost, nSshPort);
	       session.setPassword(strSshPassword);

	       final Properties config = new Properties();
	       config.put("StrictHostKeyChecking", "no");
	       session.setConfig(config);

	       session.connect();
	      
	       try{
	              session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
	       } catch (com.jcraft.jsch.JSchException e) { /* port already forwarded */ }

	}
}
