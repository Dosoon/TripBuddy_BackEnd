package com.tripbuddy.user.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.tripbuddy.review.model.dto.ReviewDto;
import com.tripbuddy.user.model.dto.UserDto;

public interface UserService {
	
	UserDto login(UserDto userDto) throws Exception;
	List<ReviewDto> listReviewByUser(int userId) throws Exception;
	void signup(UserDto userDto) throws Exception;
	void deleteUser(int userId) throws Exception;
	UserDto getUserInfo(String id) throws Exception;
	void modifyUser(UserDto userDto) throws Exception;
	int checkUserByEmail(UserDto userDto) throws Exception;
	int checkAdmin(Map<String, Object> params);
	void saveRefreshToken(int userId, String refreshToken);
	void deleteUserToken(int userId);
	String getRefreshToken(int userId);
	int checkId(String id);

}
