package com.company.project.EndersOverFlow.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class InterceptorConfig implements WebMvcConfigurer{

	@Autowired
	InterceptorAuthCheck interceptorAuthCheck;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		log.info("InterceptorConfig addInterceptors 시작");
		registry.addInterceptor(interceptorAuthCheck)
	        .addPathPatterns("/")
	        .addPathPatterns("/codeReview/*")
	        .addPathPatterns("/comments/*")
	        .excludePathPatterns("/member/**"); //로그인 쪽은 예외처리를 한다.
		log.info("InterceptorConfig addInterceptors 종료");
	}
}
