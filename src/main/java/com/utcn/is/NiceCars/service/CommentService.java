package com.utcn.is.NiceCars.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utcn.is.NiceCars.model.Comment;
import com.utcn.is.NiceCars.repository.CommentRepository;

@Service
public class CommentService {
	@Autowired
	private CommentRepository commentRepository;

	public Comment save(Comment comment) {
		return commentRepository.save(comment);
	}

	public Comment findById(Long id) {
		return commentRepository.findById(id).get();
	}

	public void delete(Long id) {
		commentRepository.deleteById(id);
	}

}
