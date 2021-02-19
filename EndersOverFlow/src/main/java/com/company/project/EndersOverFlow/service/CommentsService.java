package com.company.project.EndersOverFlow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.project.EndersOverFlow.model.Comments;
import com.company.project.EndersOverFlow.repository.CommentsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentsService {
	@Autowired
	private CommentsRepository commentsRepository;

	public List<Comments> codeReviewFindAll() {
		List<Comments> codeReview = new ArrayList<>();
		commentsRepository.findAll().forEach(e -> codeReview.add(e));
		return codeReview;
	}

	public Optional<Comments> codeReviewFindById(Long CR_NO) {
		Optional<Comments> codeReview = commentsRepository.findById(CR_NO);
		return codeReview;
	}

	public void codeReviewDeleteById(Long CR_NO) {
		commentsRepository.deleteById(CR_NO);
	}

	public Comments save(Comments codeReview) {
		commentsRepository.save(codeReview);
		return codeReview;
	}

	public void codeReviewUpdateById(Long CR_NO, Comments comments) {
		Optional<Comments> commentsOptional = commentsRepository.findById(CR_NO);
		if (commentsOptional.isPresent()) {
			commentsOptional.get().setCM_NO(comments.getCM_NO());
			commentsOptional.get().setCR_NO(comments.getCR_NO());
			commentsOptional.get().setCM_CREATER(comments.getCM_CREATER());
			commentsOptional.get().setCM_CONTENTS(comments.getCM_CONTENTS());
			commentsOptional.get().setCM_CREATEDAY(comments.getCM_CREATEDAY());
			commentsOptional.get().setCM_ISVIEW(comments.getCM_UPDATEDAY());
			commentsOptional.get().setCM_UPDATEDAY(comments.getCM_ISVIEW());
			commentsOptional.get().setCM_STAR_COUNT(comments.getCM_STAR_COUNT());
			commentsRepository.save(comments);
		}
	}
}
