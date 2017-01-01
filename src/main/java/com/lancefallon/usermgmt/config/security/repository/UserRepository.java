package com.lancefallon.usermgmt.config.security.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.lancefallon.usermgmt.config.security.model.UserPrivileges;

@Service
public class UserRepository extends JdbcDaoSupport {
	
	public UserRepository(@Autowired DataSource dataSource) {
		setDataSource(dataSource);
	}

	public UserPrivileges tryLogin(String username) {
		return getJdbcTemplate().queryForObject("select * from public.user where username = ? limit 1", new Object[]{username}, new RowMapper<UserPrivileges>(){

			@Override
			public UserPrivileges mapRow(ResultSet rs, int rowNum) throws SQLException {
				UserPrivileges user = new UserPrivileges();
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEnabled(rs.getBoolean("active"));
				user.setAccountNonExpired(true);
				user.setAccountNonLocked(true);
				user.setCredentialsNonExpired(true);
				return user;
			}
			
		});
	}

}