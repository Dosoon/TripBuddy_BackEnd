package com.tripbuddy.follow.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FollowDto {

	private int followerId;
	private int followeeId;
	private String followerName;
	private String followeeName;
	private String followerProfileImg;
	private String followeeProfileImg;
	private int followBack;

	public FollowDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getFollowerId() {
		return followerId;
	}

	public void setFollowerId(int followerId) {
		this.followerId = followerId;
	}

	public int getFolloweeId() {
		return followeeId;
	}

	public void setFolloweeId(int followeeId) {
		this.followeeId = followeeId;
	}

	public String getFollowerName() {
		return followerName;
	}

	public void setFollowerName(String followerName) {
		this.followerName = followerName;
	}

	public String getFolloweeName() {
		return followeeName;
	}

	public void setFolloweeName(String followeeName) {
		this.followeeName = followeeName;
	}

	public int getFollowBack() {
		return followBack;
	}

	public void setFollowBack(int followBack) {
		this.followBack = followBack;
	}

	public String getFollowerProfileImg() {
		return followerProfileImg;
	}

	public void setFollowerProfileImg(String followerProfileImg) {
		this.followerProfileImg = followerProfileImg;
	}

	public String getFolloweeProfileImg() {
		return followeeProfileImg;
	}

	public void setFolloweeProfileImg(String followeeProfileImg) {
		this.followeeProfileImg = followeeProfileImg;
	}
}
