package com.pg.iloveblog.dto;

import org.springframework.data.domain.Page;

import com.pg.iloveblog.model.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//페이지번호를 표시하기위한 정보(startPage,endPage등..) + JPA Page<Board>정보
public class BoardPagenationDTO {
	private Page<Board> page;
	private int startPage;
	private int endPage;
	private boolean isNext;
	private boolean isPrev;
	
}
