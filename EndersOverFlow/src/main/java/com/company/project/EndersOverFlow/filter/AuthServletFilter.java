package com.company.project.EndersOverFlow.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.project.EndersOverFlow.model.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthServletFilter implements Filter{
	
	private static final String LOGIN_PAGE = "/member/login";
	
	@Autowired
	com.company.project.EndersOverFlow.service.MemberService memberService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.info("filter 작동");
		HttpSession session =  ((HttpServletRequest)request).getSession();
		// Session으로 유저정보 조회
		String userEmail = (String)session.getAttribute("userEmail");
		String userAuth = (String)session.getAttribute("userAuth");
		
		// Session에 해당 값들이 존재하는지 체크하기
		if (userEmail == null || userAuth == null) {
			log.info("유저 정보에 문제가 있습니다. 로그인 페이지로 돌아갑니다.");
			((HttpServletResponse)response).sendRedirect(LOGIN_PAGE);
		} else {
			// Session에 User정보가 존재한다면 현재 user의 권한 확인
			Member member = memberService.findMember(userEmail);
			boolean authCheck = member.getMBR_LOGINUUID().equals(userAuth);
			System.out.println("유저 정보 확인하기 : " + authCheck);
			if(authCheck) {
				chain.doFilter(request, response);
			} else {
				log.info("유저 정보에 문제가 있습니다. 로그인 페이지로 돌아갑니다.");
				((HttpServletResponse)response).sendRedirect(LOGIN_PAGE);
			}
		}
		log.info("filter 종료");
	}
}
