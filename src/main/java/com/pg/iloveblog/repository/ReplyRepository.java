package com.pg.iloveblog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.pg.iloveblog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{
	@Query(value="SELECT * FROM reply WHERE boardId=:boardId ORDER BY no DESC", nativeQuery = true)
	List<Reply> findAllByBoardNo(int boardId);
	@Modifying
	@Query(value="INSERT INTO reply(content, userId,boardId, createDate, updateDate) VALUES(?,?,?,now(),now())", nativeQuery = true)
	void replySave(String content, int userId, int boardId);
}
