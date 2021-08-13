package com.pg.iloveblog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pg.iloveblog.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer>{
	@Query(value="SELECT\r\n"
			+ " CASE\r\n"
			+ "  WHEN\r\n"
			+ "   DATE_FORMAT(createDate, '%Y-%m-%d') = DATE_FORMAT(NOW(), '%Y-%m-%d')\r\n"
			+ "  THEN\r\n"
			+ "   DATE_FORMAT(createDate, '%H:%i:%s')\r\n"
			+ "  ELSE\r\n"
			+ "   DATE_FORMAT(createDate, '%Y-%m-%d')\r\n"
			+ "  END AS createDate,\r\n"
			+ "  `no`, content, title,`COUNT`,updateDate,userId\r\n"
			+ "  FROM board",nativeQuery = true)
	List<Board> findAllPost(); //사용하지않는다. 쿼리에서 시간을 변경하지 않고 그냥 자바에서 변경하기로함.
	
	@Query(value="SELECT * FROM board ORDER BY NO DESC LIMIT ?,?", nativeQuery = true)
	List<Board> findAllTest(int start, int offset);//사용하지않는다.
}
