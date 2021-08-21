package com.pg.iloveblog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pg.iloveblog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{
	@Query(value="SELECT * FROM reply WHERE boardId=:boardId", nativeQuery = true)
	List<Reply> findAllByBoardNo(int boardId);
}
