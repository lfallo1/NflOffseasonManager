package com.lancefallon.usermgmt.sharing.sql;

import org.springframework.jdbc.core.RowMapper;

import com.lancefallon.usermgmt.player.model.Player;
import com.lancefallon.usermgmt.player.model.Position;
import com.lancefallon.usermgmt.sharing.model.UserFriend;
import com.lancefallon.usermgmt.sharing.model.UserSharePlayer;

public interface UserShareSql {
	
	//user_friend
	static final String USERFRIEND_COL_SENDER = "sender";
	static final String USERFRIEND_COL_RECEIVER = "receiver";
	static final String USERFRIEND_COL_PENDING = "pending";
	static final String USERFRIEND_COL_ACCEPTED = "accepted";

	//user_share_player
	static final String USERSHAREPLAYER_COL_ID = "id";
	static final String USERSHAREPLAYER_COL_USERNAME_SENDER = "username_sender";
	static final String USERSHAREPLAYER_COL_USERNAME_RECEIVER = "username_receiver";
	static final String USERSHAREPLAYER_COL_HAS_VIEWED = "has_viewed";
	static final String USERSHAREPLAYER_COL_DATE = "date";
	static final String USERSHAREPLAYER_COL_PLAYER = "player";
	static final String USERSHAREPLAYER_COL_MESSAGE_BODY = "message_body";
	static final String USERSHAREPLAYER_COL_MEDIA_URL = "message_media_url";

	static final String PLAYER_COL_NAME = "name";
	static final String PLAYER_COL_HEIGHT = "height";
	static final String PLAYER_COL_WEIGHT = "weight";
	static final String PLAYER_COL_YEAR = "year";
	static final String PLAYER_COL_COLLEGE_TEXT = "college_text";
	static final String PLAYER_COL_POSITION_NAME = "position_name";
	
	//get friends by user. kinda convoluted, but union is used and the second piece has receiver / sender swapped.
	//this is just to make the mapping automatic
	static final String FIND_FRIENDS_BY_USER = "select * from user_friend where sender = ? and accepted = true " +
			"union " +
			"select receiver, sender, pending, accepted from user_friend where receiver = ? and accepted = true;";
	
	static final String GET_SHARED_PLAYERS = "select share.*, p.name, p.college_text, p.height, p.weight, p.year, pos.name as position_name from user_share_player share inner join player p on share.player = p.id inner join position pos on p.position = pos.id where username_receiver = ? and date >= ? and has_viewed = ? order by date desc";
	
	//mappers
	RowMapper<UserFriend> USERFRIEND_ROW_MAPPER = (rs, rowNum) -> {
		return new UserFriend(rs.getString(USERFRIEND_COL_SENDER),rs.getString(USERFRIEND_COL_RECEIVER),
				rs.getBoolean(USERFRIEND_COL_PENDING),rs.getBoolean(USERFRIEND_COL_ACCEPTED));
	};
	
	RowMapper<UserSharePlayer> USERSHAREPLAYER_ROW_MAPPER = (rs, rowNum) -> {
		UserSharePlayer userSharePlayer = new UserSharePlayer();
		userSharePlayer.setId(rs.getInt(USERSHAREPLAYER_COL_ID));
		userSharePlayer.setUsernameSender(rs.getString(USERSHAREPLAYER_COL_USERNAME_SENDER));
		userSharePlayer.setUsernameReceiver(rs.getString(USERSHAREPLAYER_COL_USERNAME_RECEIVER));
		userSharePlayer.setHasViewed(rs.getBoolean(USERSHAREPLAYER_COL_HAS_VIEWED));
		userSharePlayer.setDate(rs.getDate(USERSHAREPLAYER_COL_DATE));
		userSharePlayer.setMessageBody(rs.getString(USERSHAREPLAYER_COL_MESSAGE_BODY));
		userSharePlayer.setMessageMediaUrl(rs.getString(USERSHAREPLAYER_COL_MEDIA_URL));
		
		Position position = new Position();
		position.setName(rs.getString(PLAYER_COL_POSITION_NAME));
		
		Player player = new Player();
		player.setId(rs.getInt(USERSHAREPLAYER_COL_PLAYER));
		player.setName(rs.getString(PLAYER_COL_NAME));
		player.setHeight(rs.getDouble(PLAYER_COL_HEIGHT));
		player.setWeight(rs.getDouble(PLAYER_COL_WEIGHT));
		player.setYear(rs.getInt(PLAYER_COL_YEAR));
		player.setCollegeText(rs.getString(PLAYER_COL_COLLEGE_TEXT));
		player.setPosition(position);
		userSharePlayer.setPlayer(player);
		
		return userSharePlayer;
	};;
}
