package com.tripbuddy.comment.model.service;

import java.util.Map;

import com.tripbuddy.comment.model.dto.CommentDto;

public interface CommentService {

	void postComment(CommentDto commentDto) throws Exception;

	void modifyComment(CommentDto commentDto) throws Exception;

	void deleteComment(int commentId) throws Exception;

	int checkCommentAuthor(Map<String, Object> params);

}
