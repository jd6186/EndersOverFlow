package com.company.project.EndersOverFlow.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.company.project.EndersOverFlow.model.CodeReview;
import com.fasterxml.jackson.core.JsonParser;

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
	@RequestMapping("/write")
	public ModelAndView goCodeWritePage(@ModelAttribute CodeReview codeReview,
            HttpServletRequest request, @RequestParam(value="CODE_NO", required=false, defaultValue="value_is_null") String CODE_NO) {
		logger.info("codeWrite 페이지로 이동을 위해 데이터 조회 시작");
		// 코드글 번호 획득(없을 경우는 바로 글 작성페이지로 이동)
		if (CODE_NO.equals("value_is_null")) {
			logger.info("수정할 codeText가 없습니다. codeWrite 페이지로 이동");
	        return new ModelAndView("code_review/CodeWrite");
		}
		
		// 코드글 체크
		Long CODE_NO_L = Long.parseLong(CODE_NO);
		Optional<CodeReview> codeText = codeReviewService.codeReviewFindById(CODE_NO_L);
		System.out.println(codeText);
		logger.info("수정할 codeText가 있습니다. codeUpdate 페이지로 이동");
        return new ModelAndView("code_review/CodeWrite");
	}
	
	// 코드글 등록
	@PostMapping(path = "/doWrite")
	public ResponseEntity doWritePage(@RequestBody JSONObject jsonObject) throws JSONException{
		logger.info("doWritePage 진입");
		if (jsonObject.get("CR_TITLE") == null) {
			return new ResponseEntity("false", HttpStatus.BAD_REQUEST);
		}
		if (jsonObject.get("CR_TITLE") == null || jsonObject.get("CR_CONTENTS") == null ) {
			return new ResponseEntity("false", HttpStatus.OK);
		}
		
		CodeReview codeReview = new CodeReview();
		codeReview.setCR_TITLE((String) jsonObject.get("CR_TITLE"));
		codeReview.setCR_CONTENTS((String) jsonObject.get("CR_CONTENTS"));
		
		LocalDate nowDate = LocalDate.now();
		codeReview.setCR_CREATEDAY(nowDate);
		codeReview.setCR_UPDATEDAY(nowDate);
		codeReview.setCR_STAR_COUNT(Long.parseLong("0"));
		codeReview.setCR_CREATER(codeReview.getCR_CREATER());
		codeReview.setCR_ISVIEW("Y");
		if (codeReviewService.save(codeReview) == null) {
			logger.info("doWritePage 종료");
			return new ResponseEntity("null", HttpStatus.OK);
		}
		logger.info("doWritePage 종료");
		return new ResponseEntity("true", HttpStatus.OK);
		
	}
	
}
