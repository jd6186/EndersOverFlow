package com.company.project.EndersOverFlow.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.project.EndersOverFlow.model.Comments;
import com.company.project.EndersOverFlow.repository.CommentsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class CommentsService {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private CommentsRepository commentsRepository;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<Comments> commentsFindAll() {
		logger.info("commentsFindAll Service 진입");
		List<Comments> comments = new ArrayList<>();
		commentsRepository.findAll().forEach(e -> comments.add(e));
		logger.info("commentsFindAll Service 진입");
		return comments;
	}

	public Optional<Comments> commentsFindById(Long CM_NO) {
		logger.info("commentsFindById Service 진입");
		Optional<Comments> comments = commentsRepository.findById(CM_NO);
		logger.info("commentsFindById Service 진입");
		return comments;
	}

	public void commentsDeleteById(Long CM_NO) {
		logger.info("commentsDeleteById Service 진입");
		commentsRepository.deleteById(CM_NO);
		logger.info("commentsDeleteById Service 진입");
	}

	public Comments save(Comments comments) {
		logger.info("Comments save Service 진입");
		commentsRepository.save(comments);
		logger.info("Comments save Service 진입");
		return comments;
	}

	public void commentsUpdateById(Long CM_NO, Comments comments) {
		logger.info("commentsUpdateById Service 진입");
		Optional<Comments> commentsOptional = commentsRepository.findById(CM_NO);
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
		logger.info("commentsUpdateById Service 진입");
	}
	
	public List<Comments> commentsFindAllByCrNo(Long CR_NO) {
		logger.info("commentsFindByCrNo Service 진입");
		String rowQuery = "select m from COMMENTS m where m.CM_CR_NO='"+CR_NO+"'"; 
		try {
			List<Comments> comments = entityManager.createQuery(rowQuery, Comments.class)
					.getResultList();
			logger.info("commentsFindByCrNo Service 진입");
			return comments;
		}catch (Exception e) {
			logger.warn("데이터가 없습니다." + e);
			logger.info("commentsFindByCrNo Service 진입");
			return null;
		}
	}
}
