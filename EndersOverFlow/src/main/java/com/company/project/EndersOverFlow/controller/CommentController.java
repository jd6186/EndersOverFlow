package com.company.project.EndersOverFlow.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		
		String CM_CONTENTS = request.getParameter("CM_CONTENTS")==null ? "None" : request.getParameter("CM_CONTENTS");
		if (CM_CONTENTS.equals("None")) {
			logger.info("doCommentWrite CM_CONTENTS_null로 인해 실패");
			return new ResponseEntity("CM_CONTENTS_null", HttpStatus.OK);
		}

		// 새글작성인지 댓글 수정인지 구분하기
		String EditCommentNum = request.getParameter("EditCommentNum")==null ? "None" : request.getParameter("EditCommentNum");
		if (!EditCommentNum.equals("None")) {
			logger.info("Comment Edit Start");
			long reqEditCM_NO = Long.parseLong(EditCommentNum);
			Optional<Comments> searchEditComment = commentService.commentsFindById(reqEditCM_NO);
			Comments editComment = searchEditComment.orElse(null);
			editComment.setCM_CONTENTS(CM_CONTENTS);
			commentService.save(editComment);
			return new ResponseEntity("Edit_Success", HttpStatus.OK);
		}
		
		
		
		String reqCM_CR_NO = request.getParameter("CM_CR_NO")==null ? "None" : request.getParameter("CM_CR_NO");
		long longCM_CR_NO = 0L;
		CodeReview codeReview = new CodeReview();
		if (reqCM_CR_NO.equals("None")) {
			logger.info("doCommentWrite CM_CR_NO_null로 인해 실패");
			return new ResponseEntity("CM_CR_NO_null", HttpStatus.OK);
		} else {
			longCM_CR_NO = Long.parseLong(reqCM_CR_NO);
			codeReview = codeReviewService.codeReviewFindById(longCM_CR_NO);
		}
		
		// TextLine에 댓을을 작성하는지 아니면 전체 글에 대한 댓글을 작성한 것인지 구분하는 작업
		String CM_INDEX = request.getParameter("CM_INDEX")==null ? "None" : request.getParameter("CM_INDEX");
		long index = 999999999999999999L;
		if (!CM_INDEX.equals("None")) {
			index = Long.parseLong(CM_INDEX);
			// Text Line에 댓글이 달리면 더 이상 글 수정이 불가능하게 만들기 위해 댓글이 달렸다는 것을 알림
			codeReview.setCR_QUE_COMMENTYN("Y");
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
			logger.warn("doWritePage save_null");
			return new ResponseEntity("save_null", HttpStatus.OK);
		}
		if (codeReview.getCR_COMMENTYN().equals("N")) {
			codeReview.setCR_COMMENTYN("Y");
		}
		
		codeReviewService.save(codeReview);
		logger.info("doCommentPage 정상 종료");
		return new ResponseEntity("true", HttpStatus.OK);
	}
	
	// 댓글 삭제
	@GetMapping(path = "/deleteComment")
	public ResponseEntity deleteCommentPage(HttpServletRequest request, @RequestParam(value="commentNum", required=false, defaultValue="value_is_null") String CM_NO){
		logger.info("doCommentPage 진입");
		long reqCM_NO = Long.parseLong(CM_NO);
		Optional<Comments> comment = commentService.commentsFindById(reqCM_NO);
		Comments deleteComment = comment.orElse(null);
		if (deleteComment != null) {
			deleteComment.setCM_ISVIEW("N");
			// 해당 댓글이 안보이게 한 뒤 저장
			commentService.save(deleteComment);
			
			// 현재 달린 댓글에 대한 전체글 번호를 조회하고 해당 글에 작성된 전체 댓글을 조사하기
			CodeReview crNo = deleteComment.getCM_CR_NO();
			List<Comments> crNoComments = commentService.commentsFindAllByCrNo(crNo.getCR_NO());
			
			// 전체 글에 댓글이 있는지 없는지 체크. 없다면 값 바꾸기
			if (crNoComments == null) {
				logger.info("글 전체에 댓글이 없습니다. COMMENTYN 값을 변경합니다.");
				crNo.setCR_COMMENTYN("N");
				crNo.setCR_QUE_COMMENTYN("N");
				codeReviewService.save(crNo);
			} else {
				// textLine안에 댓글이 있는지 체크 없다면 값 바꾸기
				boolean isTextLine = true;
				for (Comments item : crNoComments) {
					// 999999999999999999L은 전체 글에 대한 댓글
					if(item.getCM_INDEX() == 999999999999999999L) {
						continue;
					} else {
						isTextLine = false;
						break;
					}
				}
				System.out.println("위치위취취위치");
				System.out.println(isTextLine);
				if (isTextLine) {
					logger.info("Text Line에 댓글이 없습니다. CR_QUE_COMMENTYN 값을 변경합니다.");
					crNo.setCR_QUE_COMMENTYN("N");
					codeReviewService.save(crNo);
				}
			}
			logger.info("doCommentPage 정상 종료");
			return new ResponseEntity("true", HttpStatus.OK);
		} else {
			return new ResponseEntity("delete_fail", HttpStatus.OK);
		}
	}
}
