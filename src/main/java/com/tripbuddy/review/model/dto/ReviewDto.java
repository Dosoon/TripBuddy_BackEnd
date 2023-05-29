package com.tripbuddy.review.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tripbuddy.comment.model.dto.CommentDto;
import com.tripbuddy.plan.model.dto.CourseDto;
import com.tripbuddy.plan.model.dto.PlanDto;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewDto {

	private int reviewId;
	private String subject;
	private String content;
	private int userId;
	private String userName;
	private String registTime;
	private int planId;
	private double rating;
	private String thumbnail;
	private PlanDto planDto;
	private List<CommentDto> comments;

	public List<CommentDto> getComments() {
		return comments;
	}

	public void setComments(List<CommentDto> comments) {
		this.comments = comments;
	}

	public ReviewDto() {
		super();
	}

	public ReviewDto(int reviewId, String subject, String content, int userId, String userName, String registTime,
			int planId, double rating, PlanDto planDto, List<CommentDto> comments, String thumbnail) {
		super();
		this.reviewId = reviewId;
		this.subject = subject;
		this.content = content;
		this.userId = userId;
		this.userName = userName;
		this.registTime = registTime;
		this.planId = planId;
		this.rating = rating;
		this.planDto = planDto;
		this.comments = comments;
		this.thumbnail = thumbnail;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRegistTime() {
		return registTime;
	}

	public void setRegistTime(String registTime) {
		this.registTime = registTime;
	}

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public PlanDto getPlanDto() {
		return planDto;
	}

	public void setPlanDto(PlanDto planDto) {
		this.planDto = planDto;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

}
