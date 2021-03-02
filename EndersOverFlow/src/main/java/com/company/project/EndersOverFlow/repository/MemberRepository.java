package com.company.project.EndersOverFlow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.company.project.EndersOverFlow.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

//	Member findById(String mBR_EMAIL);
}
