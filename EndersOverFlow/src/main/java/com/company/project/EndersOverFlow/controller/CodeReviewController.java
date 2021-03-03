package com.company.project.EndersOverFlow.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.project.EndersOverFlow.model.CodeReview;

@Controller
@RequestMapping("codeReview")
public class CodeReviewController {
	// 기본형
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	com.company.project.EndersOverFlow.service.CodeReviewService codeReviewService;

	// 코드 리스트 전체 목록 페이지로 이동
	@RequestMapping("list")
	public String goCodeListPage(Model model) {
		logger.info("codeReviewList 조회 시작");

		List<CodeReview> codeReview = codeReviewService.codeReviewFindAll();
		model.addAttribute("codeReviewList", codeReview);

		logger.info("codeReviewList 조회 종료");
		return "code_review/CodeList";
	}

	// 코드 상세글 페이지로 이동
	@RequestMapping("detail")
	public String goCodeDetailPage(Model model) {
		logger.info("codeReviewDetail 조회 시작");

		Long requestData = (long) 1;
		Optional<CodeReview> codeReview = codeReviewService.codeReviewFindById(requestData);
		model.addAttribute("codeReviewDetail", codeReview);

		logger.info("codeReviewDetail 조회 종료");
		return "code_review/CodeDetail";
	}

	// 코드글 작성 페이지로 이동
	@RequestMapping("write")
	public String goCodeReviewDetailPage(Model model) {
		logger.info("codeWrite 페이지로 이동 시작");
		return "code_review/CodeWrite";
	}
}
