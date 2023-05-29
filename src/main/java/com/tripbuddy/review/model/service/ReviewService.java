package com.tripbuddy.review.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.tripbuddy.comment.model.dto.CommentDto;
import com.tripbuddy.review.model.dto.ReviewDto;

public interface ReviewService {
	
	void postReview(ReviewDto reviewDto) throws Exception;
	List<ReviewDto> listReview(Map<String, String> req) throws Exception;
	ReviewDto getReview(int reviewId) throws Exception;
	List<ReviewDto> listHotReview() throws Exception;
	List<CommentDto> listComment(int reviewId) throws Exception;
	void modifyReview(ReviewDto reviewDto) throws Exception;
	void deleteReview(int reviewId) throws Exception;
	void rateReview(Map<String, Object> req) throws Exception;
	int checkRatingHistory(Map<String, Object> params);
	int checkReviewAuthor(Map<String, Object> params);
	int getRate(Map<String, Integer> req);
	List<ReviewDto> getReviews(Map<String, Object> req);
	int totalReviews(Map<String, Object> req);
	float getRateAvg(int reviewId);
	List<ReviewDto> getReviewsById(String id);
	
}
