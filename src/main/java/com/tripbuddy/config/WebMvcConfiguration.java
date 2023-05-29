package com.tripbuddy.config;

import java.util.Arrays;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.tripbuddy.chat.handler.SocketHandler;
import com.tripbuddy.interceptor.AdminAuthInterceptor;
import com.tripbuddy.interceptor.CommentAuthInterceptor;
import com.tripbuddy.interceptor.MemoAuthInterceptor;
import com.tripbuddy.interceptor.NoticeAuthInterceptor;
import com.tripbuddy.interceptor.NotifyAuthInterceptor;
import com.tripbuddy.interceptor.PlanAuthInterceptor;
import com.tripbuddy.interceptor.ReviewAuthInterceptor;
import com.tripbuddy.interceptor.SessionAuthInterceptor;

@Configuration
@EnableWebMvc
@EnableWebSocket
@MapperScan(basePackages = {"com.tripbuddy.**.mapper"})
@ComponentScan(basePackages = {"com.tripbuddy.chat.**"})
public class WebMvcConfiguration implements WebMvcConfigurer, WebSocketConfigurer {

	@Autowired
	private SocketHandler socketHandler;
	private final List<String> sessionAuthPatterns = Arrays.asList(
			"/**");
	
	private final List<String> planAuthPatterns = Arrays.asList(
			"/memos/**", "/notifys/invite", "/plans/*", "/plans", "/plans/*/users");
	
	private final List<String> memoAuthPatterns = Arrays.asList(
			"/memos/*");
	
	private final List<String> adminAuthPatterns = Arrays.asList(
			"/notices", "/notices/*");
	
	private final List<String> noticeAuthPatterns = Arrays.asList(
			"/notices", "/notices/*");
	
	private final List<String> notifyAuthPatterns = Arrays.asList(
			"/notifys");
	
	private final List<String> reviewAuthPatterns = Arrays.asList(
			"/reviews", "/reviews/*", "/reviews/rating");
	
	private final List<String> commentAuthPatterns = Arrays.asList(
			"/comments", "/comments/*");
	
	private SessionAuthInterceptor sessionAuthInterceptor;
	private PlanAuthInterceptor planAuthInterceptor;
	private MemoAuthInterceptor memoAuthInterceptor;
	private AdminAuthInterceptor adminAuthInterceptor;
	private NoticeAuthInterceptor noticeAuthInterceptor;
	private NotifyAuthInterceptor notifyAuthInterceptor;
	private ReviewAuthInterceptor reviewAuthInterceptor;
	private CommentAuthInterceptor commentAuthInterceptor;
	

	public WebMvcConfiguration(SessionAuthInterceptor sessionAuthInterceptor, PlanAuthInterceptor planAuthInterceptor,
			MemoAuthInterceptor memoAuthInterceptor, AdminAuthInterceptor adminAuthInterceptor, NoticeAuthInterceptor noticeAuthInterceptor,
			NotifyAuthInterceptor notifyAuthInterceptor, ReviewAuthInterceptor reviewAuthInterceptor, CommentAuthInterceptor commentAuthInterceptor) {
		super();
		this.sessionAuthInterceptor = sessionAuthInterceptor;
		this.planAuthInterceptor = planAuthInterceptor;
		this.memoAuthInterceptor = memoAuthInterceptor;
		this.adminAuthInterceptor = adminAuthInterceptor;
		this.noticeAuthInterceptor = noticeAuthInterceptor;
		this.notifyAuthInterceptor = notifyAuthInterceptor;
		this.reviewAuthInterceptor = reviewAuthInterceptor;
		this.commentAuthInterceptor = commentAuthInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(sessionAuthInterceptor).addPathPatterns(sessionAuthPatterns);
		registry.addInterceptor(planAuthInterceptor).addPathPatterns(planAuthPatterns);
		registry.addInterceptor(memoAuthInterceptor).addPathPatterns(memoAuthPatterns);
		registry.addInterceptor(adminAuthInterceptor).addPathPatterns(adminAuthPatterns);
		registry.addInterceptor(noticeAuthInterceptor).addPathPatterns(noticeAuthPatterns);
		registry.addInterceptor(notifyAuthInterceptor).addPathPatterns(notifyAuthPatterns);
		registry.addInterceptor(reviewAuthInterceptor).addPathPatterns(reviewAuthPatterns);
		registry.addInterceptor(commentAuthInterceptor).addPathPatterns(commentAuthPatterns);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");

		registry.addResourceHandler("/swagger-ui/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**").allowedOrigins("http://localhost:8080", "http://70.12.103.81:8080")
		registry.addMapping("/**").allowedOrigins("http://localhost:8080", "http://172.30.1.44:8080")
		.allowedMethods("GET", "POST", "PUT", "DELETE")
		.allowedHeaders("*")
		.allowCredentials(true);
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//		registry.addHandler(socketHandler, "/chat").setAllowedOrigins("http://70.12.103.81:8080");
		registry.addHandler(socketHandler, "/chat").setAllowedOrigins("http://172.30.1.44:8080");
	}
}
