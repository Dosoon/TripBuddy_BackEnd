package com.tripbuddy.comment.model.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tripbuddy.comment.model.dto.CommentDto;
import com.tripbuddy.comment.model.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentMapper commentMapper;
	
	@Override
	public void postComment(CommentDto commentDto) throws Exception {
		commentMapper.postComment(commentDto);
	}

	@Override
	public void modifyComment(CommentDto commentDto) throws Exception {
		commentMapper.modifyComment(commentDto);
	}

	@Override
	public void deleteComment(int commentId) throws Exception {
		commentMapper.deleteComment(commentId);
	}

	@Override
	public int checkCommentAuthor(Map<String, Object> params) {
		return commentMapper.checkCommentAuthor(params);
	}

}
