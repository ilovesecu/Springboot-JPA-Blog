package com.pg.iloveblog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pg.iloveblog.model.AttachFile;

public interface AttachFileRepository extends JpaRepository<AttachFile, Integer>{
	@Query(value="SELECT * FROM attachfile WHERE uploadPath = (SELECT DATE_FORMAT(DATE_ADD(NOW(), INTERVAL-1 DAY), '%Y\\%m\\%d'))",nativeQuery = true)
	List<AttachFile> findOldFiles();
}
