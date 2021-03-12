package com.company.project.EndersOverFlow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.company.project.EndersOverFlow.model.Member;
import com.company.project.EndersOverFlow.repository.MemberRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class MemberService{
	// @PersistenceContext 즉, 영속성 컨텍스트는 엔티티를 영구 저장하는 환경으로써 EntityManager로 엔티티를 저장하거나 조회하면 
	// EntityManager는 영속성 컨텍스트에 엔티티를 보관하고 관리한다.
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private MemberRepository memberRepository;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<Member> findAll() {
		logger.info("findAll Service 진입");
		List<Member> members = new ArrayList<>();
		memberRepository.findAll().forEach(e -> members.add(e));
		logger.info("findAll Service 종료");
		return members;
	}

	public Member findMember (String MBR_EMAIL) { 
		logger.info("findMember Service 진입");
		String rowQuery = "select m from ENDERS_MEMBER m where m.MBR_EMAIL='" + MBR_EMAIL + "'"; 
		System.out.println(rowQuery);
		try {
			Member member = entityManager.createQuery(rowQuery, Member.class)
					.getSingleResult();
			logger.info("findMember Service 종료");
			return member;
		} catch (NoResultException e) {
			logger.warn("데이터가 없습니다." + e);
			logger.warn("findMember Service 비정상 종료");
			return null;
		}
	}
	
	public Member userCheck (String MBR_LOGINUUID) { 
		logger.info("memberCheck Service 진입");
		String rowQuery = "select m from ENDERS_MEMBER m where m.MBR_LOGINUUID='"+MBR_LOGINUUID+"'"; 
		try {
			Member member = entityManager.createQuery(rowQuery, Member.class)
					.getSingleResult();
			logger.info("userCheck Service 정상 종료");
			return member;
		}catch (Exception e) {
			logger.warn("데이터가 없습니다." + e);
			logger.warn("userCheck Service 비정상 종료");
			return null;
		}
	}

	public void deleteById(Long MBR_NO) {
		logger.info("deleteById Service 진입");
		memberRepository.deleteById(MBR_NO);
		logger.info("deleteById Service 종료");
	}

	public Member doSignUp(Member member) {
		logger.info("doSignUp Service 진입");
		memberRepository.save(member);
		logger.info("doSignUp Service 종료");
		return member;
	}

	public Member findMemberByUUID (String MBR_UUID) { 
		logger.info("findMember Service 진입");
		String rowQuery = "select m from ENDERS_MEMBER m where m.MBR_LOGINUUID='" + MBR_UUID + "' AND m.MBR_AUTH='N'"; 
		try {
			Member member = entityManager.createQuery(rowQuery, Member.class)
					.getSingleResult();
			logger.info("findMemberByUUID Service 정상 종료");
			return member;
		} catch (NoResultException e) {
			logger.warn("데이터가 없습니다." + e);
			logger.warn("findMemberByUUID Service 비정상 종료");
			return null;
		}
	}
	
	public Boolean passwordUpdate(String MBR_EMAIL, String MBR_PASSWORD, String MBR_NEW_PASSWORD, Member member) {
		logger.info("passwordUpdate Service 진입");
		Optional<Member> e = memberRepository.findById(member.getMBR_NO());
		if (e.get().getMBR_EMAIL().equals(MBR_EMAIL)) {
			if (e.get().getMBR_PASSWORD().equals(MBR_PASSWORD)) {
				LocalDate nowDate = LocalDate.now();
				member.setMBR_PASSWORD(MBR_NEW_PASSWORD);
				member.setMBR_PASSWORD_UPDATE_DATE(nowDate);
				memberRepository.save(member);
				logger.info("passwordUpdate Service 종료");
				return true;
			}
		}
		logger.warn("passwordUpdate Service 실패");
		return false;
	}

	public Boolean loginUuidUpdate(Member member) {
		logger.info("loginUuidUpdate Service 진입");
		boolean result = false;
		try {
			memberRepository.save(member);
			logger.info("loginUuidUpdate Service 종료");
			return true;
		} catch (Exception e) {
			logger.warn("업데이트 실패." + e);
			logger.warn("loginUuidUpdate Service 비정상 종료");
			return false;
		}
	}
}
