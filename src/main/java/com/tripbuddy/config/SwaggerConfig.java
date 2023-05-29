package com.tripbuddy.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.security.Principal;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.WebRequest;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
//	http://localhost/swagger-ui/index.html
	@Bean
	public Docket attrsapi() {
		
		final ApiInfo apiInfo = new ApiInfoBuilder()
				.title("TripBuddy")
				.description("<h3>실시간 여행 계획을 돕는 웹 서비스 TripBuddy</h3>")
				.license("MIT License")
				.version("1.0").build(); // 버전 적기
		
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.tripbuddy"))// 하위 모든 bean
				.paths(PathSelectors.ant("/**")) // 디스패쳐의 접근경로인듯
				.build()
				.ignoredParameterTypes( // 필요하지 않은 정보(세션,리퀘스트,리스폰스) 등이 나오지 않게 해준다.
	                    WebRequest.class,
	                    HttpServletRequest.class,
	                    HttpServletResponse.class,
	                    HttpSession.class,
	                    Principal.class,
	                    Locale.class
	            );
		
	}
	
	private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        return produces;
    }

	
	
}
