package com.tripbuddy.follow.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tripbuddy.follow.model.dto.FollowDto;
import com.tripbuddy.follow.model.mapper.FollowMapper;

@Service
public class FollowServiceImpl implements FollowService {

	@Autowired
	private FollowMapper followMapper;
	
	@Override
	public void followUser(Map<String, Object> req) throws Exception {
		followMapper.followUser(req);
	}

	@Override
	public void unfollowUser(Map<String, Object> req) throws Exception {
		followMapper.unfollowUser(req);
		
	}

	@Override
	public List<FollowDto> listFollowing(int followerId) throws Exception {
		return followMapper.listFollowing(followerId);
	}

	@Override
	public List<FollowDto> listFollower(int followeeId) throws Exception {
		return followMapper.listFollower(followeeId);
	}

	@Override
	public Map<String, Integer> followInfo(int userId) {
		return followMapper.followInfo(userId);
	}
	
	@Override
	public boolean isFollowing(Map<String, Integer> req) {
		if (followMapper.isFollowing(req)==0)
			return false;
		return true;
	}
	
	@Override
	public boolean isReviewFollowing(Map<String, Integer> req) {
		if (followMapper.isReviewFollowing(req)==0)
			return false;
		return true;
	}

}
