package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.ReplyComment;



public interface ReplyCommentDAO extends JpaRepository<ReplyComment, Integer> {

	
	@Query("SELECT r FROM ReplyComment r where r.product.id = ?1")
	List<ReplyComment> findByReplyCommentId(Integer keywords) ;
	
}
