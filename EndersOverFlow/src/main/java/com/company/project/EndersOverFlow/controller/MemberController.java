package com.company.project.EndersOverFlow.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.tags.Param;

import com.company.project.EndersOverFlow.model.Member;

@Controller
@RequestMapping("member")
@SessionAttributes("userEmail")
public class MemberController {
	// 기본형
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	com.company.project.EndersOverFlow.service.MemberService memberService;

	// 로그인 페이지로 이동
	@RequestMapping("login")
	public ModelAndView goLoginPage(@ModelAttribute Member member,
            HttpServletRequest request) throws Exception {
        return new ModelAndView("member_page/Login");
	}
	
	// 실제 로그인 진행
	@PostMapping(path = "/dologin")
	public ResponseEntity doLoginPage(Model model, HttpServletRequest request){
		logger.info("login 페이지 진입");
		
		// 이메일 정보로 맴버 확인하기
		String emailField = (String) request.getAttribute("emailField");
		String passwordField = (String) request.getAttribute("passwordField");
		try {
			Member searchMember = memberService.findMember(emailField);
			// 맴버비밀번호 확인
			if (searchMember.getMBR_PASSWORD().equals(passwordField) && searchMember.getMBR_AUTH().equals("Y")) {
				String uuid = UUID.randomUUID().toString();
				searchMember.setMBR_LOGINUUID(uuid);
				boolean result = memberService.loginUuidUpdate(searchMember);
				if (result) {
					model.addAttribute("userEmail", uuid);
					return new ResponseEntity("login", HttpStatus.OK);
				} else {
		        	return new ResponseEntity("false", HttpStatus.OK);
				}
			} else if (searchMember.getMBR_PASSWORD().equals(passwordField) && searchMember.getMBR_AUTH().equals("N")) {
				return new ResponseEntity("authCheck", HttpStatus.OK);
	        } else {
	        	return new ResponseEntity("false", HttpStatus.OK);
	        }
		} catch (Exception e) {
        	return new ResponseEntity("false", HttpStatus.OK);
		}
		
	}

	// 회원 입력
	@PostMapping(path = "/signUpMember")
	public String signupPage(@Validated Member member){
		logger.info("signupPage 페이지 진입");
		LocalDate nowDate = LocalDate.now();
		member.setMBR_EMAIL(member.getMBR_EMAIL().split("@")[0]);
        member.setMBR_SIGNUP_DATE(nowDate);
        member.setMBR_PASSWORD_UPDATE_DATE(nowDate);
        memberService.doSignUp(member);
		return "redirect:/member/login";
	}


	// 전체 회원 조회
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public String getAllmembers(Model model) {
		logger.info("전체 회원 조회 페이지 진입");
		List<Member> member = memberService.findAll();
		model.addAttribute("memeber", member);
		logger.info("전체 회원 조회 페이지 정상 종료");
		return "redirect:/SignUpPage";
	}

	// 회원번호로 한명의 회원 조회
	@RequestMapping("/emailCheck")
	public ResponseEntity emailCheck(@RequestParam(value="MBR_EMAIL", required=false, defaultValue="value_is_null") String emailField) {
		logger.info("회원 중복 확인 페이지 진입");
		// 이메일 양식 체크
		String[] splitData = emailField.split("@");
		if (!splitData[1].equals("enders.co.kr")) {
			return new ResponseEntity("emailFalse", HttpStatus.OK);
		}
		// 유저 체크
		Member member = memberService.findMember(splitData[0]);
		if (member == null) {
			return new ResponseEntity("true", HttpStatus.OK);
		}
		return new ResponseEntity("false", HttpStatus.OK);
	}
	
	// 회원번호로 회원 수정(mbrNo로 회원을 찾아 Member 객체의 id, name로 수정함)
	@PostMapping(value = "/doMemberUpdate")
	public ResponseEntity updateMember(String MBR_EMAIL, String MBR_PASSWORD, String MBR_NEW_PASSWORD, Member member) {
		logger.info("회원번호로 회원 수정 페이지 진입");
		boolean result = false;
		result = memberService.passwordUpdate(MBR_EMAIL, MBR_PASSWORD, MBR_NEW_PASSWORD, member);
		logger.info("회원번호로 회원 수정 페이지 종료");
		return new ResponseEntity(result, HttpStatus.OK);
	}

	// 회원번호로 회원 삭제
	@DeleteMapping(value = "/{mbrNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity deleteMember(@PathVariable("mbrNo") Long mbrNo) {
		logger.info("회원번호로 회원 삭제 페이지 종료");
		memberService.deleteById(mbrNo);
		logger.info("회원번호로 회원 수정 페이지 종료");
		return new ResponseEntity(HttpStatus.OK);
	}


}
