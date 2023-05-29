package com.tripbuddy.follow.model.service;

import java.util.List;
import java.util.Map;

import com.tripbuddy.follow.model.dto.FollowDto;

public interface FollowService {

	void followUser(Map<String, Object> req) throws Exception;

	void unfollowUser(Map<String, Object> req) throws Exception;

	List<FollowDto> listFollowing(int followerId) throws Exception;

	List<FollowDto> listFollower(int followeeId) throws Exception;

	Map<String, Integer> followInfo(int userId);

	boolean isFollowing(Map<String, Integer> req);

	boolean isReviewFollowing(Map<String, Integer> req);

}
