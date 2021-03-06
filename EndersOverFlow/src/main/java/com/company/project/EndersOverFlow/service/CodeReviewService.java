package com.company.project.EndersOverFlow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.project.EndersOverFlow.model.CodeReview;
import com.company.project.EndersOverFlow.repository.CodeReviewRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CodeReviewService {
	@Autowired
	private CodeReviewRepository codeReviewRepository;

	public List<CodeReview> codeReviewFindAll() {
		List<CodeReview> codeReview = new ArrayList<>();
		codeReviewRepository.findAll().forEach(e -> codeReview.add(e));
		return codeReview;
	}

	public Optional<CodeReview> codeReviewFindById(Long CR_NO) {
		Optional<CodeReview> codeReview = codeReviewRepository.findById(CR_NO);
		return codeReview;
	}

	public void codeReviewDeleteById(Long CR_NO) {
		codeReviewRepository.deleteById(CR_NO);
	}

	public CodeReview save(CodeReview codeReview) {
		codeReviewRepository.save(codeReview);
		return codeReview;
	}

	public void codeReviewUpdateById(Long CR_NO, CodeReview codeReview) {
		Optional<CodeReview> codeReviews = codeReviewRepository.findById(CR_NO);
		if (codeReviews.isPresent()) {
			codeReviews.get().setCR_NO(codeReview.getCR_NO());
			codeReviews.get().setCR_TITLE(codeReview.getCR_TITLE());
			codeReviews.get().setCR_CREATER(codeReview.getCR_CREATER());
			codeReviews.get().setCR_CONTENTS(codeReview.getCR_CONTENTS());
			codeReviews.get().setCR_CREATEDAY(codeReview.getCR_CREATEDAY());
			codeReviews.get().setCR_UPDATEDAY(codeReview.getCR_UPDATEDAY());
			codeReviews.get().setCR_ISVIEW(codeReview.getCR_ISVIEW());
			codeReviews.get().setCR_STAR_COUNT(codeReview.getCR_STAR_COUNT());
			codeReviewRepository.save(codeReview);
		}
	}
}
