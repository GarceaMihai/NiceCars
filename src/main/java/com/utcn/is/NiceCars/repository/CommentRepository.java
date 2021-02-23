package com.utcn.is.NiceCars.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utcn.is.NiceCars.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
