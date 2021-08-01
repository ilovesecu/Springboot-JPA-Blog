package com.pg.iloveblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pg.iloveblog.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
}
