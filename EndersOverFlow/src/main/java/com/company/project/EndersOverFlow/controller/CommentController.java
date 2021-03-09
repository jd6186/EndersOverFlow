package com.company.project.EndersOverFlow.controller;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.project.EndersOverFlow.model.CodeReview;
import com.company.project.EndersOverFlow.model.Comments;
import com.company.project.EndersOverFlow.model.Member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
@RequestMapping("comment")
public class CommentController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	com.company.project.EndersOverFlow.service.CommentsService commentService;
	@Autowired
	com.company.project.EndersOverFlow.service.CodeReviewService codeReviewService;
	@Autowired
	com.company.project.EndersOverFlow.service.MemberService memberService;

	

	// 댓글 등록
	@PostMapping(path = "/doComment")
	public ResponseEntity doCommentPage(HttpServletRequest request){
		logger.info("doCommentPage 진입");

		// Session사용해서 유저정보 확인하기
		HttpSession session = request.getSession(true);
		String userUUID = (String) session.getAttribute("userEmail");
		Member meber = memberService.userCheck(userUUID);
	    System.out.println("유저데이터는");
	    System.out.println(meber);
		
		String CM_CONTENTS = request.getParameter("CM_CONTENTS")==null ? "None" : request.getParameter("CM_CONTENTS");
		if (CM_CONTENTS.equals("None")) {
			logger.info("doCommentPage 종료");
			return new ResponseEntity("CM_CONTENTS_null", HttpStatus.OK);
		}
		String reqCM_CR_NO = request.getParameter("CM_CR_NO")==null ? "None" : request.getParameter("CM_CR_NO");
		long longCM_CR_NO = 0L;
		CodeReview codeReview = new CodeReview();
		if (reqCM_CR_NO.equals("None")) {
			logger.info("doCommentPage 종료");
			return new ResponseEntity("CM_CR_NO_null", HttpStatus.OK);
		} else {
			longCM_CR_NO = Long.parseLong(reqCM_CR_NO);
			codeReview = codeReviewService.codeReviewFindById(longCM_CR_NO);
		}
		
		String CM_INDEX = request.getParameter("CM_INDEX")==null ? "None" : request.getParameter("CM_INDEX");
		long index = 999999999999999999L;
		if (!CM_INDEX.equals("None")) {
			index = Long.parseLong(CM_INDEX);
		}
		// 오늘 날짜 조회
		LocalDate nowDate = LocalDate.now();
		
		// 댓글 실제 등록
		Comments comments = new Comments();
		comments.setCM_CONTENTS(CM_CONTENTS);
		comments.setCM_CR_NO(codeReview);
		comments.setCM_CREATER(meber);
		comments.setCM_CREATEDAY(nowDate);
		comments.setCM_UPDATEDAY(nowDate);
		comments.setCM_ISVIEW("Y");
		comments.setCM_STAR_COUNT((long) 0);
		comments.setCM_INDEX(index);
		if (commentService.save(comments) == null) {
			logger.info("doWritePage 종료");
			return new ResponseEntity("save_null", HttpStatus.OK);
		}
		logger.info("doCommentPage 종료");
		return new ResponseEntity("true", HttpStatus.OK);
	}
	
	
	
	
}
