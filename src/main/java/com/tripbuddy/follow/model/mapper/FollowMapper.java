package com.tripbuddy.follow.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tripbuddy.follow.model.dto.FollowDto;

@Mapper
public interface FollowMapper {

	void followUser(Map<String, Object> req);
	void unfollowUser(Map<String, Object> req);
	List<FollowDto> listFollowing(int followerId);
	List<FollowDto> listFollower(int followeeId);
	Map<String, Integer> followInfo(int userId);
	int isFollowing(Map<String, Integer> req);
	int isReviewFollowing(Map<String, Integer> req);
	

}
