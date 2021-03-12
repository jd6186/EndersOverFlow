package com.company.project.EndersOverFlow.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig
{
	@Bean
	public FilterRegistrationBean getFilterRegistrationBean()
	{
		FilterRegistrationBean registrationBean = new FilterRegistrationBean(new AuthServletFilter());
		// registrationBean.addUrlPatterns("/*"); // 서블릿 등록 빈 처럼 패턴을 지정해 줄 수 있다.

	    registrationBean.addUrlPatterns("/");
	    registrationBean.addUrlPatterns("/codeReview/*");
	    registrationBean.addUrlPatterns("/comment/*");
	    registrationBean.addUrlPatterns("/member/doMemberUpdate/*");
		return registrationBean;
	}
}