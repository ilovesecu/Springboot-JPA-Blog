package com.pg.iloveblog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.pg.iloveblog.model.AttachFile;

public interface AttachFileRepository extends JpaRepository<AttachFile, Integer>{
	@Query(value="SELECT * FROM attachfile WHERE uploadPath = (SELECT DATE_FORMAT(DATE_ADD(NOW(), INTERVAL-1 DAY), '%Y\\%m\\%d'))",nativeQuery = true)
	List<AttachFile> findOldFiles();
	@Query(value="SELECT * FROM attachfile WHERE boardId=:boardId",nativeQuery = true)
	List<AttachFile> findWithBoardId(int boardId);
	
	@Modifying //select쿼리가 아닌 update쿼리로 인식
	@Query(value="DELETE FROM attachfile WHERE uuid=:uuid and fileName=:fileName", nativeQuery = true)
	int deleteByUuidAndFileName(String uuid, String fileName);
}
