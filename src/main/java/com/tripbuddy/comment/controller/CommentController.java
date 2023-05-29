package com.tripbuddy.comment.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripbuddy.comment.model.dto.CommentDto;
import com.tripbuddy.comment.model.service.CommentService;
import com.tripbuddy.jwt.service.JwtService;
import com.tripbuddy.user.model.dto.UserDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/comments")
@Api(tags = { "댓글 컨트롤러" })
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	@Autowired
	private JwtService jwtService;
	
	@ApiOperation(value = "리뷰 댓글 작성", notes = "리뷰에 대한 댓글을 작성합니다.")
    @ApiImplicitParam(name = "commentDto",
    value = "reviewId(required), content(required)",
    required = true ,
    dataType = "commentDto",
    dataTypeClass = Object.class,
    paramType = "body",
    example = " { \"reviewId\" : 27 "
    		+ ", \"content\" : \"정말 멋지고 좋은 리뷰입니다.\" }")
	@PostMapping
	public ResponseEntity<Object> postComment(@RequestBody CommentDto commentDto, HttpServletRequest request) {
		try {

			if (!jwtService.checkToken(request.getHeader("access-token"))) {
				return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
			}
			int userId = (Integer) jwtService.get(null).get("userId");
//			int userId = ((UserDto)(session.getAttribute("userinfo"))).getUserId();
			commentDto.setUserId(userId);
			commentService.postComment(commentDto);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	@ApiOperation(value = "리뷰 댓글 수정", notes = "리뷰에 대한 댓글을 수정합니다.")
    @ApiImplicitParam(name = "commentDto",
    value = "reviewId(required), content(required)",
    required = true ,
    dataType = "commentDto",
    dataTypeClass = Object.class,
    paramType = "body",
    example = " { \"reviewId\" : \"27\" "
    		+ ", \"content\" : \"정말 좋은 리뷰입니다.\" }" )
	@PutMapping
	public ResponseEntity<Object> modifyComment(@RequestBody CommentDto commentDto, HttpServletRequest request) {
		try {

			if (!jwtService.checkToken(request.getHeader("access-token"))) {
				return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
			}
			int userId = (Integer) jwtService.get(null).get("userId");
//			int userId = ((UserDto)(session.getAttribute("userinfo"))).getUserId();
			commentDto.setUserId(userId);
			commentService.modifyComment(commentDto);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	@ApiOperation(value = "댓글 삭제하기", notes = "리뷰에 대한 댓글을 삭제합니다.")
	@ApiImplicitParam(name = "commentId", value = "댓글의 기본 키(commentId) ", required = true ,dataType = "int", dataTypeClass = Integer.class , paramType = "path" , example = "3" )
	@DeleteMapping("/{commentId}")
	public ResponseEntity<Object> deleteComment(@PathVariable("commentId") int commentId) {
		try {
			commentService.deleteComment(commentId);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	

}
