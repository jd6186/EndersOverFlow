package com.company.project.EndersOverFlow.repository;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;
import com.company.project.EndersOverFlow.model.Comments;


@Repository 
public interface CommentsRepository extends JpaRepository<Comments, Long> { 
}

