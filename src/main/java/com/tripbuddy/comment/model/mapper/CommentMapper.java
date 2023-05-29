package com.tripbuddy.comment.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tripbuddy.comment.model.dto.CommentDto;

@Mapper
public interface CommentMapper {

	List<CommentDto> listComment(int reviewId) throws SQLException;

	void postComment(CommentDto commentDto) throws SQLException;

	void modifyComment(CommentDto commentDto) throws SQLException;

	void deleteComment(int commentId) throws SQLException;

	int checkCommentAuthor(Map<String, Object> params);

}
