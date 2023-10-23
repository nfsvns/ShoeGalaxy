package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.Comment;



public interface CommentDAO extends JpaRepository<Comment, Integer> {

	
	@Query("SELECT c FROM Comment c where c.product.id = ?1")
	List<Comment> findByCommentId(Integer keywords) ;
	
}
