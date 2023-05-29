package com.tripbuddy.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class RequestWrappingFilter implements Filter {
	
	private Map<String, HttpMethod> includePath;
	
	public RequestWrappingFilter() {
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
			
			// MemoAuth
			includePath.put("/memos/[0-9]+", HttpMethod.DELETE);
			
			// AdminAuth
			includePath.put("/notices", HttpMethod.POST);
			includePath.put("/notices/[0-9]+", HttpMethod.DELETE);
			
			// NoticeAuth
			includePath.put("/notices", HttpMethod.PUT);
			includePath.put("/notices/[0-9]+", HttpMethod.DELETE);
			
			// NotifyAuth
			includePath.put("/notifys", HttpMethod.PUT);
			
			// ReviewAuth
			includePath.put("/reviews", HttpMethod.PUT);
			includePath.put("/reviews/[0-9]+", HttpMethod.DELETE);
			includePath.put("/reviews/rating", HttpMethod.POST);
			
			// CommentAuth
			includePath.put("/comments", HttpMethod.PUT);
			includePath.put("/comments/[0-9]+", HttpMethod.DELETE);
			
		}
	}



	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		for (Map.Entry<String, HttpMethod> entry : includePath.entrySet()) {
		    if (httpRequest.getRequestURI().matches(entry.getKey()) && entry.getValue().matches(httpRequest.getMethod())) {
		    	ReadableRequestWrapper wrapper = new ReadableRequestWrapper((HttpServletRequest) request);
				request.setAttribute("reqBody", wrapper.getReqBody());
				chain.doFilter(wrapper, response);
				return;
		    }
		}
		
		
		chain.doFilter(request, response);
		
	}

}
