package com.lancefallon.usermgmt.config.security.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.lancefallon.usermgmt.config.exception.model.CustomErrorMessage;
import com.lancefallon.usermgmt.config.exception.model.DatabaseException;
import com.lancefallon.usermgmt.config.security.model.UserPrivileges;

@Service
public class UserRepository extends JdbcDaoSupport {
	
	public UserRepository(@Autowired DataSource dataSource) {
		setDataSource(dataSource);
	}

	public UserPrivileges tryLogin(String username) {
		return getJdbcTemplate().queryForObject("select * from public.user where lower(username) = ? limit 1", new Object[]{username.toLowerCase()}, new RowMapper<UserPrivileges>(){

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
	
	/**
	 * try to update an existing record in the userlogin metadata tbl.
	 * if the entry returns no results, then automatically calls the insert user_login_metadata method
	 * @param username
	 * @param host
	 * @param addr
	 * @return
	 * @throws DatabaseException
	 */
	public boolean updateUserLoginMetaData(String username, String host, String addr) throws DatabaseException{
		int result = getJdbcTemplate().update("update user_login_metadata set last_login = ?, count = (count+1) where username = ? and host = ? and addr = ?", new Date(), username, host, addr);
		if(result < 1){
			result = this.addUserLoginMetaData(username, host, addr);
		}
		return result > 0;
	}
	
	/**
	 * add entry to user login metadata tbl
	 * @param username
	 * @param host
	 * @param addr
	 * @return
	 * @throws DatabaseException
	 */
	public int addUserLoginMetaData(String username, String host, String addr) throws DatabaseException{
	       SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(getJdbcTemplate());
	        // set the table name, primary key column name, and the array of column names
	        jdbcInsert.setTableName("public.user_login_metadata");
	        
	        jdbcInsert.setGeneratedKeyName("id");
	        jdbcInsert.setColumnNames(Arrays.asList("username", "host", "addr", "last_login", "count"));

	        // set the values to be inserted
	        Map<String, Object> parameters = new HashMap<String, Object>();

	        parameters.put("username", username);
	        parameters.put("host", host);
	        parameters.put("addr", addr);
	        parameters.put("last_login", new Date());
	        parameters.put("count", 1);

	        // execute insert
	        Number key = null;
	        try {
	            // perform the insert and return the key
	            key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(
	                    parameters));
	        } catch (Exception e) {
				throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
	        }

	        // return the key (primary key)
	        return ((Number) key).intValue();
	}

}