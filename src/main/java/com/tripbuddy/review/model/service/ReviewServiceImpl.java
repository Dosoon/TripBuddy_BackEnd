package com.tripbuddy.review.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripbuddy.comment.model.dto.CommentDto;
import com.tripbuddy.comment.model.mapper.CommentMapper;
import com.tripbuddy.review.model.dto.ReviewDto;
import com.tripbuddy.review.model.mapper.ReviewMapper;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewMapper reviewMapper;
	
	@Override
	public void postReview(ReviewDto reviewDto) throws SQLException {
		reviewMapper.postReview(reviewDto);
	}

	@Override
	public List<ReviewDto> listReview(Map<String, String> req) throws SQLException {
		return reviewMapper.listReview(req);
	}

	@Override
	public ReviewDto getReview(int reviewId) throws SQLException {
		return reviewMapper.getReview(reviewId);
	}

	@Override
	public List<ReviewDto> listHotReview() throws SQLException {
		return reviewMapper.listHotReview();
	}

	@Override
	public List<CommentDto> listComment(int reviewId) throws SQLException {
		return reviewMapper.listComment(reviewId);
	}
	
	@Override
	public void modifyReview(ReviewDto reviewDto) throws Exception {
		reviewMapper.modifyReview(reviewDto);
	}

	@Override
	public void deleteReview(int reviewId) throws Exception {
		reviewMapper.deleteReview(reviewId);
	}

	@Override
	public void rateReview(Map<String, Object> req) throws Exception {
		reviewMapper.rateReview(req);
	}
	
	@Override
	public int getRate(Map<String, Integer> req) {
		return (reviewMapper.getRate(req)!=null) ?  reviewMapper.getRate(req) : 0;
	}

	@Transactional
	@Override
	public int checkRatingHistory(Map<String, Object> params) {
		if (reviewMapper.checkRatingHistory(params) == 0 && reviewMapper.checkReviewAuthor(params) == 0)
			return 1;
		return 0;
	}

	@Override
	public int checkReviewAuthor(Map<String, Object> params) {
		return reviewMapper.checkReviewAuthor(params);
	}
	
	@Override
	public List<ReviewDto> getReviews(Map<String, Object> req) {
		return reviewMapper.getReviews(req);
	}
	
	@Override
	public int totalReviews(Map<String, Object> req) {
		return reviewMapper.totalReviews(req);
	}

	@Override
	public float getRateAvg(int reviewId) {
		return reviewMapper.getRateAvg(reviewId);
	}
	
	@Override
	public List<ReviewDto> getReviewsById(String id) {
		return reviewMapper.getReviewsById(id);
	}

}
