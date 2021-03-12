package com.company.project.EndersOverFlow.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import com.company.project.EndersOverFlow.model.Comments;
import com.company.project.EndersOverFlow.model.Member;

@Controller
@RequestMapping("codeReview")
public class CodeReviewController {

	@Autowired
	com.company.project.EndersOverFlow.service.CodeReviewService codeReviewService;
	@Autowired
	com.company.project.EndersOverFlow.service.MemberService memberService;
	@Autowired
	com.company.project.EndersOverFlow.service.CommentsService commentService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// 코드 리스트 전체 목록 페이지로 이동
	@RequestMapping("/list")
	public String goCodeListPage(Model model) {
		logger.info("codeReviewList 조회 시작");

		List<CodeReview> codeReview = codeReviewService.codeReviewFindAll();
		model.addAttribute("codeReviewList", codeReview);

		logger.info("codeReviewList 조회 종료");
		return "code_review/CodeList";
	}

	// 코드 상세글 페이지로 이동
	@GetMapping("/detail")
	public String goCodeDetailPage(Model model, HttpServletRequest request, @RequestParam(value="CR_NO", required=false, defaultValue="0") String CR_NO) {
		logger.info("codeReviewDetail 조회 시작");

		// 글 전체 리스트 중 유저가 누른 해당 글 조회하기
		Long requestCR_NO = Long.parseLong(CR_NO);
		CodeReview codeReview = codeReviewService.codeReviewFindById(requestCR_NO);
		model.addAttribute("codeReviewDetail", codeReview);
		System.out.println("##################################### 글 전체 리스트 중 유저가 누른 해당 글 조회하기 ######################################");
		

		// Session사용해서 유저정보 확인하기
		HttpSession session = request.getSession(true);
		String userUUID = (String) session.getAttribute("userAuth");
		Member member = memberService.userCheck(userUUID);
		// 글 작성자와 현재 유저가 같은 유저인지 비교하기
		boolean result = (member.getMBR_NO() == codeReview.getCR_CREATER().getMBR_NO()) ? true : false;
		model.addAttribute("isCommentEdit", result);
		// 수정이 가능한 글인지 아닌지 판별하기
		if(result && codeReview.getCR_QUE_COMMENTYN().equals("N")) {
			model.addAttribute("isEdit", result);
		} else {
			model.addAttribute("isEdit", false);
		}
		System.out.println("##################################### 수정이 가능한 글인지 아닌지 판별하기 ######################################");
		
		// 줄바꿈 표현을 위한 리스트 제작
		String[] resultList = codeReview.getCR_CONTENTS().split("&nbspN;");
		model.addAttribute("codeTextList", resultList);
		System.out.println("##################################### 줄바꿈 표현을 위한 리스트 제작 ######################################");
		
		// 댓글이 있는지 체큭하기
		long crNO = codeReview.getCR_NO();
		List<Comments> commentsList = commentService.commentsFindAllByCrNo(crNO);
		model.addAttribute("commentsList", commentsList);
		System.out.println("##################################### 댓글이 있는지 체크하기 ######################################");
		
		// 글 전체 Row수 구하기
		model.addAttribute("codeTextListLength", resultList.length);
		logger.info("codeReviewDetail 조회 종료");
		return "code_review/CodeDetail";
	}

	// 코드글 작성 페이지로 이동
	@RequestMapping("/write")
	public ModelAndView goCodeWritePage(Model model, @ModelAttribute CodeReview codeReview,
            HttpServletRequest request, @RequestParam(value="CR_NO", required=false, defaultValue="value_is_null") String CR_NO) {
		logger.info("codeWrite 페이지로 이동을 위해 데이터 조회 시작");
		// 코드글 번호 획득(없을 경우는 바로 글 작성페이지로 이동)
		
		if (CR_NO.equals("value_is_null")) {
			logger.info("수정할 codeText가 없습니다. codeWrite 페이지로 이동");
			CodeReview dempCodeText = new CodeReview();
			model.addAttribute("EditText", dempCodeText);

	        return new ModelAndView("code_review/CodeWrite");
		}
		
		// 코드글 체크
		Long CODE_NO_L = Long.parseLong(CR_NO);
		CodeReview codeText = codeReviewService.codeReviewFindById(CODE_NO_L);
		System.out.println(codeText);
		model.addAttribute("EditText", codeText);
		

		String[] resultList = codeText.getCR_CONTENTS().split("&nbsp;");
		model.addAttribute("codeTextList", resultList);
		
		logger.info("수정할 codeText가 있습니다. codeUpdate 페이지로 이동");
        return new ModelAndView("code_review/CodeWrite");
	}
	
	// 코드글 등록
	@PostMapping(path = "/doWrite", produces = "application/json; charset=utf8")
	public ResponseEntity doWritePage(HttpServletRequest request){
		logger.info("doWritePage 진입");

		// Session사용해서 유저정보 확인하기
		HttpSession session = request.getSession(true);
		String userUUID = (String) session.getAttribute("userAuth");
		Member meber = memberService.userCheck(userUUID);
	    System.out.println("유저데이터는");
	    System.out.println(meber);
		
		String CR_TITLE = request.getParameter("CR_TITLE")==null?"None":request.getParameter("CR_TITLE");
		String CR_CONTENTS = request.getParameter("CR_CONTENTS")==null?"None":request.getParameter("CR_CONTENTS");
		String CR_TYPE = request.getParameter("CR_TYPE")==null?"None":request.getParameter("CR_TYPE");

		if (CR_TITLE.equals("None") || CR_CONTENTS.equals("None") || CR_TYPE.equals("None")) {
			logger.info("doWritePage 종료");
			return new ResponseEntity("req_value_null", HttpStatus.OK);
		}
		System.out.println("서버데이터는");
	    System.out.println(CR_TITLE + " : " + CR_CONTENTS + " : " + CR_TYPE);
	    
		
		CodeReview codeReview = new CodeReview();
		codeReview.setCR_TITLE(CR_TITLE);
		codeReview.setCR_CONTENTS(CR_CONTENTS);
		
		LocalDate nowDate = LocalDate.now();
		codeReview.setCR_CREATEDAY(nowDate);
		codeReview.setCR_UPDATEDAY(nowDate);
		codeReview.setCR_STAR_COUNT(Long.parseLong("0"));
		codeReview.setCR_CREATER(meber);
		codeReview.setCR_ISVIEW("Y");
		codeReview.setCR_TYPE(CR_TYPE);
		codeReview.setCR_COMMENTYN("N");
		codeReview.setCR_QUE_COMMENTYN("N");
		if (codeReviewService.save(codeReview) == null) {
			logger.info("doWritePage 종료");
			return new ResponseEntity("save_null", HttpStatus.OK);
		}
		logger.info("doWritePage 종료");
		return new ResponseEntity("true", HttpStatus.OK);
	}
	

	// 글 삭제
	@GetMapping(path = "/deleteReview")
	public ResponseEntity deleteReviewPage(HttpServletRequest request, @RequestParam(value="codeReviewNum", required=false, defaultValue="value_is_null") String CR_NO){
		logger.info("deleteReviewPage 진입");
		try {
			if(CR_NO.equals("value_is_null")) {
				return new ResponseEntity("delete_fail", HttpStatus.OK);
			}
			long reqCR_NO = Long.parseLong(CR_NO);
			CodeReview codeReview = codeReviewService.codeReviewFindById(reqCR_NO);
			codeReview.setCR_ISVIEW("N");
			codeReviewService.save(codeReview);
			logger.info("deleteReviewPage 정상종료");
			return new ResponseEntity("true", HttpStatus.OK);
		}catch (Exception e) {
			logger.warn("글 삭제에 실패했습니다. : " + e);
			logger.warn("deleteReviewPage 비정상 종료");
			return new ResponseEntity("delete_fail", HttpStatus.OK);
		}
	}
}
