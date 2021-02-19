package com.company.project.EndersOverFlow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.company.project.EndersOverFlow.model.CodeReview;

@Repository
public interface CodeReviewRepository extends JpaRepository<CodeReview, Long> {
}
