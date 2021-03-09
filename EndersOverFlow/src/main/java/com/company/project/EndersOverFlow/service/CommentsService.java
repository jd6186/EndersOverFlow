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

	public List<Comments> commentsFindAll() {
		List<Comments> comments = new ArrayList<>();
		commentsRepository.findAll().forEach(e -> comments.add(e));
		return comments;
	}

	public Optional<Comments> commentsFindById(Long CM_NO) {
		Optional<Comments> comments = commentsRepository.findById(CM_NO);
		return comments;
	}

	public void commentsDeleteById(Long CR_NO) {
		commentsRepository.deleteById(CR_NO);
	}

	public Comments save(Comments comments) {
		commentsRepository.save(comments);
		return comments;
	}

	public void commentsUpdateById(Long CR_NO, Comments comments) {
		Optional<Comments> commentsOptional = commentsRepository.findById(CR_NO);
		if (commentsOptional.isPresent()) {
			commentsOptional.get().setCM_NO(comments.getCM_NO());
			commentsOptional.get().setCM_CR_NO(comments.getCM_CR_NO());
			commentsOptional.get().setCM_CREATER(comments.getCM_CREATER());
			commentsOptional.get().setCM_CONTENTS(comments.getCM_CONTENTS());
			commentsOptional.get().setCM_CREATEDAY(comments.getCM_CREATEDAY());
			commentsOptional.get().setCM_ISVIEW(comments.getCM_ISVIEW());
			commentsOptional.get().setCM_UPDATEDAY(comments.getCM_UPDATEDAY());
			commentsOptional.get().setCM_STAR_COUNT(comments.getCM_STAR_COUNT());
			commentsRepository.save(comments);
		}
	}
}
