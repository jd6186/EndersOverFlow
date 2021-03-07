package com.company.project.EndersOverFlow.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.company.project.EndersOverFlow.model.Member;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Controller
@RequestMapping("codeReview")
public class CodeReviewController {
	
	// 기본형
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	com.company.project.EndersOverFlow.service.CodeReviewService codeReviewService;
	@Autowired
	com.company.project.EndersOverFlow.service.MemberService memberService;

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
	@GetMapping("/detail")
	public String goCodeDetailPage(Model model, @RequestParam(value="CR_NO", required=false, defaultValue="0") String CR_NO) {
		logger.info("codeReviewDetail 조회 시작");

		Long requestCR_NO = Long.parseLong(CR_NO);
		CodeReview codeReview = codeReviewService.codeReviewFindById(requestCR_NO);
		System.out.println(codeReview);
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
		CodeReview codeText = codeReviewService.codeReviewFindById(CODE_NO_L);
		System.out.println(codeText);
		logger.info("수정할 codeText가 있습니다. codeUpdate 페이지로 이동");
        return new ModelAndView("code_review/CodeWrite");
	}
	
	// 코드글 등록
	@PostMapping(path = "/doWrite", produces = "application/json; charset=utf8")
	public ResponseEntity doWritePage(@RequestBody Map<String, Object> param, HttpServletRequest request){
		logger.info("doWritePage 진입");
		

		// Session사용해서 유저정보 확인하기
		HttpSession session = request.getSession(true);
		String userUUID = (String) session.getAttribute("userEmail");
		Member meber = memberService.userCheck(userUUID);
		
		Gson gson = new Gson();

		 // jsonPaserPser 클래스 객체를 만들고 해당 객체에 
		JsonParser jparser = new JsonParser();

		// param의 id 오브젝트 -> 문자열 파싱 -> jsonElement 파싱
		String CR_TITLE = param.get("CR_TITLE").toString();
	    String CR_CONTENTS = param.get("CR_CONTENTS").toString(); 
	    System.out.println("서버데이터는");
	    System.out.println(CR_TITLE + " : " + CR_CONTENTS);
		
		CodeReview codeReview = new CodeReview();
		codeReview.setCR_TITLE(CR_TITLE);
		codeReview.setCR_CONTENTS(CR_CONTENTS);
		
		LocalDate nowDate = LocalDate.now();
		codeReview.setCR_CREATEDAY(nowDate);
		codeReview.setCR_UPDATEDAY(nowDate);
		codeReview.setCR_STAR_COUNT(Long.parseLong("0"));
		codeReview.setCR_CREATER(meber);
		codeReview.setCR_ISVIEW("Y");
		if (codeReviewService.save(codeReview) == null) {
			logger.info("doWritePage 종료");
			return new ResponseEntity("null", HttpStatus.OK);
		}
		logger.info("doWritePage 종료");
		return new ResponseEntity("true", HttpStatus.OK);
		
	}
	
}
