package com.poly.service;

import com.poly.entity.Comment;
import com.poly.entity.Reply;

import java.util.List;

public interface CommentService {
    List<Comment> findAll();

    Comment findById(Integer id);

    void create(Comment comment);

    Comment update(Comment comment);
    
    List<Reply> findByReplyCommentId(Integer id);

    void delete(Integer id);
}
