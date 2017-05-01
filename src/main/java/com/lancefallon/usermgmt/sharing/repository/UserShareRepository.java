package com.lancefallon.usermgmt.sharing.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import com.lancefallon.usermgmt.config.exception.model.CustomErrorMessage;
import com.lancefallon.usermgmt.config.exception.model.DatabaseException;
import com.lancefallon.usermgmt.sharing.model.UserFriend;
import com.lancefallon.usermgmt.sharing.model.UserSharePlayer;
import com.lancefallon.usermgmt.sharing.model.UserSharePlayerDto;
import com.lancefallon.usermgmt.sharing.sql.UserShareSql;

@Service
public class UserShareRepository extends JdbcDaoSupport implements UserShareSql {

	public UserShareRepository(@Autowired DataSource dataSource) {
		setDataSource(dataSource);
	}

	public List<UserFriend> findFriends(OAuth2Authentication auth) throws DatabaseException {
		try{
			return getJdbcTemplate().query(FIND_FRIENDS_BY_USER, new Object[]{auth.getName(), auth.getName()}, USERFRIEND_ROW_MAPPER);
		}
		catch(DataAccessException e){
			throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
		}
	}

	public Integer sharePlayer(String username, String receiverUsername, UserSharePlayerDto shareDto) throws DatabaseException {
	       SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(getJdbcTemplate());
	        // set the table name, primary key column name, and the array of column names
	        jdbcInsert.setTableName("public.user_share_player");
	        
	        jdbcInsert.setGeneratedKeyName("id");
	        jdbcInsert.setColumnNames(Arrays.asList("username_sender", "username_receiver", "has_viewed", 
	        		"date", "player", "message_body", "message_media_url"));

	        // set the values to be inserted
	        Map<String, Object> parameters = new HashMap<String, Object>();

	        parameters.put("username_sender", username);
	        parameters.put("username_receiver", receiverUsername);
	        parameters.put("has_viewed", false);
	        parameters.put("date", Calendar.getInstance().getTime());
	        parameters.put("player", shareDto.getPlayerId());
	        parameters.put("message_body", shareDto.getNotes());
	        parameters.put("message_media_url", shareDto.getMediaUrl());

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

	public List<UserSharePlayer> getSharedPlayers(String name, Date dateParam, Boolean excludeViewed) throws DatabaseException {
		try{
			List<UserSharePlayer> ret = getJdbcTemplate().query(GET_SHARED_PLAYERS, new Object[]{name, dateParam, excludeViewed}, USERSHAREPLAYER_ROW_MAPPER);
			return ret;
		}
		catch(EmptyResultDataAccessException e){
			return new ArrayList<>();
		}
		catch(DataAccessException e){
			throw new DatabaseException(new CustomErrorMessage("nflDraftAppError", e.getMessage()));
		}
	}

	/**
	 * set a list of 
	 * @param username
	 * @param ret
	 */
	public void setSharedPlayersViewed(String username, List<UserSharePlayer> ret) {
		ret.stream().forEach(r->{
			getJdbcTemplate().update("update user_share_player set has_viewed = true where id = ? and username_receiver = ?", r.getId(), username);
		});
	}

}
