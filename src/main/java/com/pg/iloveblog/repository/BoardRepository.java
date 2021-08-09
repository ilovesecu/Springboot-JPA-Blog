package com.pg.iloveblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pg.iloveblog.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer>{

}
