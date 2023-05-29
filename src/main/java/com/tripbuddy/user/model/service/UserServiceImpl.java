package com.tripbuddy.user.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripbuddy.review.model.dto.ReviewDto;
import com.tripbuddy.user.model.dto.UserDto;
import com.tripbuddy.user.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public List<ReviewDto> listReviewByUser(int userId) throws Exception {
		return userMapper.listReviewByUser(userId);
	}

	@Override
	@Transactional
	public UserDto login(UserDto userDto) throws Exception {
		UserDto ret = userMapper.login(userDto);
		if (ret != null)
			userMapper.updateLastAccess(userDto);
		return ret;
	}

	@Override
	public void signup(UserDto userDto) throws Exception {
		userMapper.signup(userDto);
	}

	@Override
	public void deleteUser(int userId) throws Exception {
		userMapper.deleteUser(userId);
	}

	@Override
	public UserDto getUserInfo(String id) throws Exception {
		return userMapper.getUserInfo(id);
	}

	@Override
	public void modifyUser(UserDto userDto) throws Exception {
		userMapper.modifyUser(userDto);
	}

	@Override
	public int checkUserByEmail(UserDto userDto) throws Exception {
		return userMapper.checkUserByEmail(userDto);
	}

	@Override
	public int checkAdmin(Map<String, Object> params) {
		return userMapper.checkAdmin(params);
	}

	@Override
	public void saveRefreshToken(int userId, String refreshToken) {
		Map<String, Object> req = new HashMap<String, Object>();
		req.put("userId", userId);
		req.put("token", refreshToken);
		userMapper.saveRefreshToken(req);
	}

	@Override
	public void deleteUserToken(int userId) {
		userMapper.deleteUserToken(userId);
	}

	@Override
	public String getRefreshToken(int userId) {
		return userMapper.getRefreshToken(userId);
	}

	@Override
	public int checkId(String id) {
		return userMapper.checkId(id);
	}

}
