package com.tripbuddy.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.tripbuddy.comment.model.service.CommentService;
import com.tripbuddy.jwt.service.JwtService;
import com.tripbuddy.review.model.service.ReviewService;
import com.tripbuddy.user.model.dto.UserDto;

@Component
public class CommentAuthInterceptor implements HandlerInterceptor {

	@Autowired
	private CommentService commentService;
	@Autowired
	private JwtService jwtService;
	
	private Map<String, HttpMethod> includePath;
	
	public CommentAuthInterceptor() {
		super();
		includePath = new HashMap<String, HttpMethod>();
				
		{
			// CommentAuth
			includePath.put("/comments", HttpMethod.PUT);
			includePath.put("/comments/[0-9]+", HttpMethod.DELETE);
		}
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		boolean cancelFlag = true;
		
		for (Map.Entry<String, HttpMethod> entry : includePath.entrySet()) {
		    if (request.getServletPath().matches(entry.getKey()) && entry.getValue().matches(request.getMethod())) {
		    	cancelFlag = false;
		    	break;
		    }
		}
		
		if (cancelFlag) return true;
		
		Map<String, Object> req = (Map<String, Object>) request.getAttribute("reqBody");
		int commentId = 0;
		if (req != null)
			commentId = (int) req.get("commentId");
		else commentId = Integer.parseInt(request.getServletPath().split("/")[2]);
		

		if (!jwtService.checkToken(request.getHeader("access-token"))) {
			response.sendError(401);
			return false;
		}
		int userId = (Integer) jwtService.get(null).get("userId");
//		int userId =  ((UserDto)request.getSession().getAttribute("userinfo")).getUserId();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("commentId", commentId);
		params.put("userId", userId);
		
		if (commentService.checkCommentAuthor(params) == 0) {
			response.sendError(403);
			return false;
		}
		
		return true;
	}
}
