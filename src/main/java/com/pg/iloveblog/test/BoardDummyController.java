package com.pg.iloveblog.test;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pg.iloveblog.dto.BoardPagenationDTO;
import com.pg.iloveblog.model.Board;
import com.pg.iloveblog.repository.BoardRepository;
import com.pg.iloveblog.service.BoardService;

@RestController
public class BoardDummyController {
	@Resource
	private BoardService boardService;
	@Resource
	private BoardRepository boardRepository;
	private static final int DISPLAY_PAGE_NUMBER = 10;
	
	@GetMapping({"/dummy/index"})
	public BoardPagenationDTO index(@PageableDefault(size=3, sort="no", direction = Sort.Direction.DESC)Pageable pageable){
		
		BoardPagenationDTO boards=boardService.글목록(pageable);
		//현재페이지
		int currentPage = boards.getPage().getPageable().getPageNumber()+1; //0부터 시작(+1해준 이유가 0부터 시작해서)
		//총 게시물 수
		long totalContents = boards.getPage().getTotalElements();
		//페이지당 보여지는 수
		int pageSize = boards.getPage().getPageable().getPageSize();
		
		//페이징
		int endPage = (int)Math.ceil((double)currentPage/DISPLAY_PAGE_NUMBER)*DISPLAY_PAGE_NUMBER;
		int startPage = endPage - DISPLAY_PAGE_NUMBER + 1; 
		int realEndPage = (int)Math.ceil((double)totalContents/pageSize);
		System.out.printf("currentPage:%d, totalContents:%d, ",currentPage,totalContents);
		System.out.printf("endPage:%d, startPage:%d, realEndpage:%d \n",endPage,startPage,realEndPage);
		if(realEndPage < endPage) endPage = realEndPage;
		return boards;
	}
	@GetMapping({"/dummy/index/test"})
	public List<Board> indexTest(int start, int offset){
		//return boardService.글목록(pageable);
		return boardRepository.findAllTest(start, offset);
	}
}
