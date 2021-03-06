package com.company.project.EndersOverFlow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.project.EndersOverFlow.model.Member;
import com.company.project.EndersOverFlow.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
	@Autowired
	private MemberRepository memberRepository;

	public List<Member> findAll() {
		List<Member> members = new ArrayList<>();
		memberRepository.findAll().forEach(e -> members.add(e));
		return members;
	}

	public Optional<Member> findById(Long MBR_NO) {
		Optional<Member> member = memberRepository.findById(MBR_NO);
		return member;
	}

	public void deleteById(Long MBR_NO) {
		memberRepository.deleteById(MBR_NO);
	}

	public Member save(Member member) {
		memberRepository.save(member);
		return member;
	}

	public void updateById(Long MBR_NO, Member member) {
		Optional<Member> e = memberRepository.findById(MBR_NO);
		if (e.isPresent()) {
			e.get().setMbrNo(member.getMbrNo());
			e.get().setId(member.getId());
			e.get().setName(member.getName());
			memberRepository.save(member);
		}
	}
}
