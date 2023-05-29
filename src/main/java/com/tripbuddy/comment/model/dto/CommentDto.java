package com.tripbuddy.comment.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "comment바구니", description = "comment 정보")
public class CommentDto {

	@ApiModelProperty(example = "3")
	@ApiParam(value = "유저 ID", required = true)
	private int userId;
	@ApiModelProperty(value = "이름", notes = "이곳에 이름을 넣어주세요", example = "홍길동", required = true)
	@ApiParam(value = "리뷰 아이디")
	private int reviewId;
	private int commentId;
	private String registerTime;
	private String userName;
	private String content;
	private boolean isMine;

	public CommentDto() {
		super();
	}
	
	public CommentDto(int userId, int reviewId, int commentId, String registerTime, String userName, String content,
			boolean isMine) {
		super();
		this.userId = userId;
		this.reviewId = reviewId;
		this.commentId = commentId;
		this.registerTime = registerTime;
		this.userName = userName;
		this.content = content;
		this.isMine = isMine;
	}

	public boolean isMine() {
		return isMine;
	}

	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
