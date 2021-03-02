package com.company.project.EndersOverFlow.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.tags.Param;

import com.company.project.EndersOverFlow.model.Member;

@Controller
@RequestMapping("member")
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
	
	@PostMapping(path = "/dologin")
	public String doLoginPage(@Validated String emailField, @Validated String passwordField){
		System.out.println("login 페이지 진입");
        Member searchMember = memberService.findMember(emailField);
        if (searchMember.getMBR_PASSWORD().equals(passwordField)) {
    		return "/index";
        }
		return "redirect:/Login";
	}

	// 회원 입력
	@PostMapping(path = "/signUpMember")
	public String signupPage(@Validated Member member){
		System.out.println("signupPage 페이지 진입");
		// Client단으로 부터 member Data가 정상적으로 넘어오는 것 확인
		LocalDate nowDate = LocalDate.now();
		member.setMBR_EMAIL(member.getMBR_EMAIL().split("@")[0]);
        member.setMBR_SIGNUP_DATE(nowDate);
        member.setMBR_PASSWORD_UPDATE_DATE(nowDate);
        memberService.doSignUp(member);
        System.out.println("여기까진 실행됐네?");
		return "redirect:/Login";
	}


	// 전체 회원 조회
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public String getAllmembers(Model model) {
		System.out.println("전체 회원 조회 페이지 진입");
		List<Member> member = memberService.findAll();
		model.addAttribute("memeber", member);
		System.out.println(model.getAttribute("memeber"));
		return "redirect:/SignUpPage";
	}

	// 회원번호로 한명의 회원 조회
	@RequestMapping("/emailCheck")
	public ResponseEntity emailCheck(@RequestParam(value="MBR_EMAIL", required=false, defaultValue="value_is_null") String emailField) {
		System.out.println("회원 중복 확인 페이지 진입");
		String[] splitData = emailField.split("@");
		Member member = memberService.findMember(splitData[0]);
		if (member == null) {
			return new ResponseEntity("해당 아이디에 대한 member가 존재하지 않습니다.", HttpStatus.OK);
		}
		if (!splitData[1].equals("enders.co.kr")) {
			return new ResponseEntity("엔더스이메일을 사용해주십시요.", HttpStatus.NOT_MODIFIED);
		}
		return new ResponseEntity("해당 이메일은 사용이 불가능합니다.", HttpStatus.BAD_REQUEST);
	}
	
	// 회원번호로 회원 수정(mbrNo로 회원을 찾아 Member 객체의 id, name로 수정함)
	@PostMapping(value = "/doMemberUpdate")
	public ResponseEntity updateMember(String MBR_EMAIL, String MBR_PASSWORD, String MBR_NEW_PASSWORD, Member member) {
		boolean result = false;
		result = memberService.passwordUpdate(MBR_EMAIL, MBR_PASSWORD, MBR_NEW_PASSWORD, member);
		return new ResponseEntity("정보 수정이 완료되었습니다.", HttpStatus.OK);
	}

	// 회원번호로 회원 삭제
	@DeleteMapping(value = "/{mbrNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> deleteMember(@PathVariable("mbrNo") Long mbrNo) {
		memberService.deleteById(mbrNo);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}


}
