package com.lancefallon.usermgmt.config.security.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.sql.DataSource;

import com.lancefallon.usermgmt.config.security.model.UserRole;
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

	public static final String FIND_USER_BY_USERNAME = "select * from public.user where lower(username) = ? limit 1";

	private static final String GET_USER_ROLES_BY_USERNAME = "select * from public.user_role where lower(username) = ?";

	private static final RowMapper<UserRole> USER_ROLE_ROW_MAPPER = (rs, rowNum) -> new UserRole(rs.getString("username"), rs.getString("role"));

	private static final RowMapper<UserPrivileges> USER_ROW_MAPPER = (rs, rowNum) -> {
		UserPrivileges user = new UserPrivileges();
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		user.setEnabled(rs.getBoolean("active"));
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		return user;
	};

	public UserRepository(@Autowired DataSource dataSource) {
		setDataSource(dataSource);
	}

	public UserPrivileges tryLogin(String username) {
		return getJdbcTemplate().queryForObject(FIND_USER_BY_USERNAME, new Object[]{username.toLowerCase()}, USER_ROW_MAPPER);
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

    public List<UserRole> getUserRoles(UserPrivileges user) {
		return getJdbcTemplate()
				.query(GET_USER_ROLES_BY_USERNAME, new Object[]{user.getUsername().toLowerCase()}, USER_ROLE_ROW_MAPPER);
    }
}