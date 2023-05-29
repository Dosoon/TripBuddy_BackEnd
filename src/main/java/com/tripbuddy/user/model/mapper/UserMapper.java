package com.tripbuddy.user.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tripbuddy.review.model.dto.ReviewDto;
import com.tripbuddy.user.model.dto.UserDto;

@Mapper
public interface UserMapper {

	List<ReviewDto> listReviewByUser(int userId) throws SQLException;
	UserDto login(UserDto userDto) throws SQLException;
	void signup(UserDto userDto) throws SQLException;
	void deleteUser(int userId) throws SQLException;
	UserDto getUserInfo(String userId) throws SQLException;
	void modifyUser(UserDto userDto) throws SQLException;
	int checkUserByEmail(UserDto userDto) throws SQLException;
	int checkAdmin(Map<String, Object> params);
	void updateLastAccess(UserDto userDto);
	void saveRefreshToken(Map<String, Object> req);
	void deleteUserToken(int userId);
	String getRefreshToken(int userId);
	int checkId(String id);
}
