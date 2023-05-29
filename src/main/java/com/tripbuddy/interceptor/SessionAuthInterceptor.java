package com.tripbuddy.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.tripbuddy.jwt.service.JwtService;
import com.tripbuddy.user.model.dto.UserDto;

@Component
public class SessionAuthInterceptor implements HandlerInterceptor {
	
	@Autowired
	private JwtService jwtService;
	
	private Map<String, HttpMethod> excludePath;
	
	public SessionAuthInterceptor() {
		super();
		excludePath = new HashMap<String, HttpMethod>();
		
		{
			excludePath.put("/chat(.*?)", HttpMethod.GET);
			excludePath.put("/chat(.*?)", HttpMethod.POST);
			excludePath.put("/chat(.*?)", HttpMethod.PATCH);
			excludePath.put("/chat(.*?)", HttpMethod.PUT);
			excludePath.put("/notices", HttpMethod.GET);
			excludePath.put("/users/auth/regen/[0-9]+", HttpMethod.GET);
			excludePath.put("/users/check/[0-9a-z]+", HttpMethod.GET);
			excludePath.put("/notices/[0-9]+", HttpMethod.GET);
			excludePath.put("/reviews(.*?)", HttpMethod.GET);
			excludePath.put("/users/login", HttpMethod.POST);
			excludePath.put("/users", HttpMethod.POST);
			excludePath.put("/users/email", HttpMethod.POST);
			excludePath.put("/follow/[0-9]+", HttpMethod.GET);
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
				
		if (request.getServletPath().startsWith("/chat")) System.out.println("chat recv");
		if (request.getServletPath().startsWith("/swagger") || request.getServletPath().startsWith("/v2") || request.getServletPath().startsWith("/chat")) return true;
		
		if (DispatcherType.REQUEST.equals(request.getDispatcherType())) {

		boolean cancelFlag = false;
		for (Map.Entry<String, HttpMethod> entry : excludePath.entrySet()) {
		    if (request.getServletPath().matches(entry.getKey()) && entry.getValue().matches(request.getMethod())) {
		    	cancelFlag = true;
		    	break;
		    }
		}
		
		if (cancelFlag) return true;
		
//		HttpSession session = request.getSession();
//		UserDto userDto = (UserDto) session.getAttribute("userinfo");
		if (jwtService.checkToken(request.getHeader("access-token"))) return true;
		else {
			response.sendError(401);
			return false;
		}
//		System.out.println(request.getLocalPort());
//		if(userDto == null) {
//			response.sendError(500);
//			return false;
//		}
//		
//		return true;
//		}
//		
//		return false;
		}
		return false;
	}
}
