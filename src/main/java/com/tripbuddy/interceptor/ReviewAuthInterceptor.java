package com.tripbuddy.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.tripbuddy.jwt.service.JwtService;
import com.tripbuddy.notify.model.service.NotifyService;
import com.tripbuddy.review.model.service.ReviewService;
import com.tripbuddy.user.model.dto.UserDto;

@Component
public class ReviewAuthInterceptor implements HandlerInterceptor {

	@Autowired
	private ReviewService reviewService;
	@Autowired
	private JwtService jwtService;

	private Map<String, HttpMethod> includePath;

	public ReviewAuthInterceptor() {
		super();
		includePath = new HashMap<String, HttpMethod>();

		{
			// ReviewAuth
			includePath.put("/reviews", HttpMethod.PUT);
			includePath.put("/reviews/[0-9]+", HttpMethod.DELETE);
			includePath.put("/reviews/rating", HttpMethod.POST);
		}

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		boolean cancelFlag = true;
		boolean ratingFlag = false;

		for (Map.Entry<String, HttpMethod> entry : includePath.entrySet()) {
			if (request.getServletPath().matches(entry.getKey()) && entry.getValue().matches(request.getMethod())) {
				if (request.getServletPath().matches("/reviews/rating"))
					ratingFlag = true;
				cancelFlag = false;
				break;
			}
		}

		if (cancelFlag)
			return true;

		Map<String, Object> req = (Map<String, Object>) request.getAttribute("reqBody");
		int reviewId = 0;
		if (req != null)
			reviewId = (int) req.get("reviewId");
		else {
			System.out.println(request.getServletPath());
			reviewId = Integer.parseInt(request.getServletPath().split("/")[2]);
			
		}
		;

		if (!jwtService.checkToken(request.getHeader("access-token"))) {
			response.sendError(401);
			return false;
		}
		int userId = (Integer) jwtService.get(null).get("userId");
//		int userId = ((UserDto) request.getSession().getAttribute("userinfo")).getUserId();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("reviewId", reviewId);
		params.put("userId", userId);

		if (ratingFlag) {
			if (reviewService.checkRatingHistory(params) == 0) {
				response.sendError(409);
				return false;
			}
		} else {
			if (reviewService.checkReviewAuthor(params) == 0) {
				response.sendError(403);
				return false;
			}
		}

		return true;
	}
}
