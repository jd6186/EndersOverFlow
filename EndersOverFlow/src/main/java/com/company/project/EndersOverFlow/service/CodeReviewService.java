package com.company.project.EndersOverFlow.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<CodeReview> codeReviewFindAll() {
		logger.info("codeReviewFindAll Service 진입");
		List<CodeReview> codeReview = new ArrayList<>();
		codeReviewRepository.findAll().forEach(e -> codeReview.add(e));
		logger.info("codeReviewFindAll Service 종료");
		return codeReview;
	}

	public CodeReview codeReviewFindById(Long CR_NO) {
		logger.info("codeReviewFindById Service 진입");
		CodeReview codeReview = codeReviewRepository.getOne(CR_NO);
		logger.info("codeReviewFindById Service 종료");
		return codeReview;
	}

	public void codeReviewDeleteById(Long CR_NO) {
		logger.info("codeReviewDeleteById Service 진입");
		codeReviewRepository.deleteById(CR_NO);
		logger.info("codeReviewDeleteById Service 진입");
	}

	public CodeReview save(CodeReview codeReview) {
		logger.info("CodeReview save Service 진입");
		try {
			codeReviewRepository.save(codeReview);
			logger.info("CodeReview save Service 종료");
			return codeReview;
		} catch (Exception e) {
			logger.warn("데이터가 없습니다." + e);
			logger.info("CodeReview save Service 종료");
			return codeReview;
		}
	}

	public void codeReviewUpdateById(Long CR_NO, CodeReview codeReview) {
		logger.info("codeReviewUpdateById Service 진입");
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
		logger.info("codeReviewUpdateById Service 종료");
	}
}
