package com.lancefallon.usermgmt.player.repository;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SSHConnection {

//	private final static String S_PATH_FILE_PRIVATE_KEY = "C:\\Users\\Val\\.ssh\\privatekeyputy.ppk";
//	private final static String S_PATH_FILE_KNOWN_HOSTS = "C:\\Users\\Val\\.ssh\\known_hosts";
	private final static String S_PASS_PHRASE = "mypassphrase";
	private final static int LOCAl_PORT = 3307;
	private final static int REMOTE_PORT = 3306;
	private final static int SSH_REMOTE_PORT = 2222;
	private final static String SSH_USER = "vagrant";
	private final static String SSH_REMOTE_SERVER = "root";
	private final static String MYSQL_REMOTE_SERVER = "127.0.0.1";

	private Session sesion; // represents each ssh session

	public void closeSSH() {
		sesion.disconnect();
	}

	public SSHConnection() throws Throwable {

		//TODO

	}
}
