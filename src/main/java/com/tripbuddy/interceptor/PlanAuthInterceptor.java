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
import com.tripbuddy.plan.model.service.PlanService;
import com.tripbuddy.user.model.dto.UserDto;
import com.tripbuddy.util.ReadableRequestWrapper;

@Component
public class PlanAuthInterceptor implements HandlerInterceptor {
	
	@Autowired
	private PlanService planService;
	@Autowired
	private JwtService jwtService;
	
	private Map<String, HttpMethod> includePath;
	
	public PlanAuthInterceptor() {
		super();
		includePath = new HashMap<String, HttpMethod>();
				
		{
			// PlanAuth
			includePath.put("/memos", HttpMethod.POST);
			includePath.put("/memos/[0-9]+/[0-9]+", HttpMethod.GET);
			includePath.put("/notifys/invite", HttpMethod.POST);
			includePath.put("/plans/[0-9]+", HttpMethod.GET);
			includePath.put("/plans", HttpMethod.PUT);
			includePath.put("/plans/[0-9]+/users", HttpMethod.DELETE);
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
		int planId = 0;
		if (req != null)
			planId = (int) req.get("planId");
		else planId = Integer.parseInt(request.getServletPath().split("/")[2]);
		

		if (!jwtService.checkToken(request.getHeader("access-token"))) {
			response.sendError(401);
			return false;
		}
		int userId = (Integer) jwtService.get(null).get("userId");
		
//		int userId =  ((UserDto)request.getSession().getAttribute("userinfo")).getUserId();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("planId", planId);
		params.put("userId", userId);
		if (planService.checkPlanMember(params) == 0) {
			response.sendError(403);
			return false;
		}
		
		return true;
	}
}
