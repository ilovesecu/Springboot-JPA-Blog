package com.pg.iloveblog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pg.iloveblog.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	@Query(value="SELECT * FROM user WHERE id = ?",nativeQuery = true)
	Optional<User> findUserId(String userId);
}
