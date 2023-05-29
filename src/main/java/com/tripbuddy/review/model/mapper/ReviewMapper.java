package com.tripbuddy.review.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tripbuddy.comment.model.dto.CommentDto;
import com.tripbuddy.review.model.dto.ReviewDto;

@Mapper
public interface ReviewMapper {

	void postReview(ReviewDto reviewDto) throws SQLException;
	List<ReviewDto> listReview(Map<String, String> req) throws SQLException;
	ReviewDto getReview(int reviewId) throws SQLException;
	List<ReviewDto> listHotReview() throws SQLException;
	List<CommentDto> listComment(int reviewId) throws SQLException;
	void modifyReview(ReviewDto reviewDto);
	void deleteReview(int reviewId);
	void rateReview(Map<String, Object> req);
	int checkRatingHistory(Map<String, Object> params);
	int checkReviewAuthor(Map<String, Object> params);
	Integer getRate(Map<String, Integer> req);
	List<ReviewDto> getReviews(Map<String, Object> req);
	int totalReviews(Map<String, Object> req);
	float getRateAvg(int reviewId);
	List<ReviewDto> getReviewsById(String id);
}
