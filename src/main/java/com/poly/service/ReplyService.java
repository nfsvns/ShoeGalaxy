package com.poly.service;

import com.poly.entity.Reply;

import java.util.List;

public interface ReplyService {
    List<Reply> findAll();

    Reply findById(Integer id);

    void create(Reply reply);

    Reply update(Reply reply);

    void delete(Integer id);
}
