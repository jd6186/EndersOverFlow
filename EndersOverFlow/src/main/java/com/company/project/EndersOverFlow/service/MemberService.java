package com.company.project.EndersOverFlow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.company.project.EndersOverFlow.model.Member;
import com.company.project.EndersOverFlow.repository.MemberRepository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Service
@SessionAttributes("userEmail")
public class MemberService{
	@PersistenceContext
	// @PersistenceContext 즉, 영속성 컨텍스트는 엔티티를 영구 저장하는 환경으로써 EntityManager로 엔티티를 저장하거나 조회하면 
	// EntityManager는 영속성 컨텍스트에 엔티티를 보관하고 관리한다.
	private EntityManager entityManager;

	
	@Autowired
	private MemberRepository memberRepository;

	public List<Member> findAll() {
		System.out.println("findAll Service 진입");
		List<Member> members = new ArrayList<>();
		memberRepository.findAll().forEach(e -> members.add(e));
		return members;
	}

	public Member findMember (String MBR_EMAIL) { 
		System.out.println("findMember Service 진입");
		String rowQuery = "select m from ENDERS_MEMBER m where m.MBR_EMAIL='" + MBR_EMAIL + "' AND m.MBR_AUTH='Y'"; 
		try {
			Member member = entityManager.createQuery(rowQuery, Member.class)
					.getSingleResult();
			return member;
		} catch (NoResultException e) {
			System.out.println("데이터가 없습니다." + e);
			return null;
		}
	}
	
	public Member userCheck (String MBR_LOGINUUID) { 
		System.out.println("memberCheck Service 진입");
		String rowQuery = "select m from ENDERS_MEMBER m where m.MBR_LOGINUUID='"+MBR_LOGINUUID+"'"; 
		try {
			Member member = entityManager.createQuery(rowQuery, Member.class)
					.getSingleResult();
			return member;
		}catch (Exception e) {
			System.out.println("데이터가 없습니다." + e);
			return null;
		}
	}

	public void deleteById(Long MBR_NO) {
		System.out.println("deleteById Service 진입");
		memberRepository.deleteById(MBR_NO);
	}

	public Member doSignUp(Member member) {
		System.out.println("doSignUp Service 진입");
		memberRepository.save(member);
		return member;
	}

	public Boolean passwordUpdate(String MBR_EMAIL, String MBR_PASSWORD, String MBR_NEW_PASSWORD, Member member) {
		System.out.println("passwordUpdate Service 진입");
		Optional<Member> e = memberRepository.findById(member.getMBR_NO());
		if (e.get().getMBR_EMAIL().equals(MBR_EMAIL)) {
			if (e.get().getMBR_PASSWORD().equals(MBR_PASSWORD)) {
				LocalDate nowDate = LocalDate.now();
				member.setMBR_PASSWORD(MBR_NEW_PASSWORD);
				member.setMBR_PASSWORD_UPDATE_DATE(nowDate);
				memberRepository.save(member);
				return true;
			}
		}
		return false;
	}

	public Boolean loginUuidUpdate(Member member) {
		boolean result = false;
		try {
			memberRepository.save(member);
			return true;
		} catch (Exception e) {
			System.out.println("업데이트 실패");
			return false;
		}
	}
}
