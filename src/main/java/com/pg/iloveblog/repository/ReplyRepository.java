package com.pg.iloveblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pg.iloveblog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{

}
