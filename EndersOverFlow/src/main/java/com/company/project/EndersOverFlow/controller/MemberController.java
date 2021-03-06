package com.company.project.EndersOverFlow.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.company.project.EndersOverFlow.model.Member;

@Controller
@RequestMapping("member")
public class MemberController {
	// 기본형
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	com.company.project.EndersOverFlow.service.MemberService memberService;

	// 모든 회원 조회
//	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE }) 
//	public ResponseEntity<List<Member>> getAllmembers(Model model) { 
//		List<Member> member = memberService.findAll(); 
//		model.addAllAttributes(member);
//		return new ResponseEntity<List<Member>>(member, HttpStatus.OK); 
//	} 
	// 로그인 페이지로 이동
	@RequestMapping("login")
	public String goLoginPage(Model model) {
		return "member_page/Login";
	}

	// 회원가입 페이지로 이동
	@RequestMapping("signup")
	public String goSignUpPage(Model model) {
		System.out.println(model.getAttribute("memeber"));
		return "member_page/SignUpPage";
	}

	// 회원가입 페이지로 이동
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public String getAllmembers(Model model) {
		List<Member> member = memberService.findAll();
		model.addAttribute("memeber", member);
		System.out.println(model.getAttribute("memeber"));
		return "member_page/SignUpPage";
	}

	// 회원번호로 한명의 회원 조회
	@GetMapping(value = "/{mbrNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Member> getMember(@PathVariable("mbrNo") Long mbrNo) {
		Optional<Member> member = memberService.findById(mbrNo);
		return new ResponseEntity<Member>(member.get(), HttpStatus.OK);
	}

	// 회원번호로 회원 삭제
	@DeleteMapping(value = "/{mbrNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> deleteMember(@PathVariable("mbrNo") Long mbrNo) {
		memberService.deleteById(mbrNo);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	// 회원번호로 회원 수정(mbrNo로 회원을 찾아 Member 객체의 id, name로 수정함)
	@PutMapping(value = "/{mbrNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Member> updateMember(@PathVariable("mbrNo") Long mbrNo, Member member) {
		memberService.updateById(mbrNo, member);
		return new ResponseEntity<Member>(member, HttpStatus.OK);
	}

	// 회원 입력
	@PostMapping
	public ResponseEntity<Member> save(Member member, HttpServletRequest httpServletRequest) {
		return new ResponseEntity<Member>(memberService.save(member), HttpStatus.OK);
	}

	// 회원 입력
	@RequestMapping(value = "/saveMember", method = RequestMethod.GET)
	public ResponseEntity<Member> save(HttpServletRequest req, Member member) {
		return new ResponseEntity<Member>(memberService.save(member), HttpStatus.OK);
	}
}
