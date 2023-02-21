package com.krugerstarlab.service.comment;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.krugerstarlab.entity.Comment;
import com.krugerstarlab.repository.CommentRepository;


@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository CommentRepository;


	


	private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
	
	public CommentServiceImpl(CommentRepository CommentRepository ) {
		this.CommentRepository = CommentRepository;


	}

	

	@Override
	public Comment getCommentById(Long commentId) {
		return CommentRepository.findById(commentId).orElse(null);
	}

	@Override
	public Comment createComment(Comment comment) {
		return CommentRepository.save(comment);
	}

	@Override
	public Comment updateComment(Long CommentId, Comment comment) {
		Comment existingComment = getCommentById(CommentId);
		if (existingComment == null) {
			return null;
		}
		existingComment.setContent(comment.getContent());

		return CommentRepository.save(existingComment);
	}

	@Override
	public boolean deleteCommentById(Long commentId) {
		try {
		CommentRepository.deleteById(commentId);
		return true;
		}catch(Exception e) {
			logger.error(e.toString());
			return false;
		}
	}

	




	


}
