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
import com.tripbuddy.user.model.dto.UserDto;

@Component
public class NotifyAuthInterceptor implements HandlerInterceptor {

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private NotifyService notifyService;
	
	private Map<String, HttpMethod> includePath;
	
	public NotifyAuthInterceptor() {
		super();
		includePath = new HashMap<String, HttpMethod>();
				
		{
			// NotifyAuth
			includePath.put("/notifys", HttpMethod.PUT);
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
		int notifyId = 0;
		if (req != null)
			notifyId = (int) req.get("notifyId");
		else notifyId = Integer.parseInt(request.getServletPath().split("/")[2]);
		
//		int userId =  ((UserDto)request.getSession().getAttribute("userinfo")).getUserId();
		if (!jwtService.checkToken(request.getHeader("access-token"))) {
			response.sendError(401);
			return false;
		}
		int userId = (Integer) jwtService.get(null).get("userId");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("notifyId", notifyId);
		params.put("userId", userId);
		if (notifyService.checkNotifyReceiver(params) == 0) {
			response.sendError(403);
			return false;
		}
		
		return true;
	}
}
