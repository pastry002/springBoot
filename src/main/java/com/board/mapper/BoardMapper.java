package com.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.board.domain.BoardDTO;
import com.board.paging.Criteria;


@Mapper // XML Mapper에서 메서드의 이름과 일치하는 SQL문 찾아 실행
public interface BoardMapper {

	 public int insertBoard(BoardDTO params);
	 
	 public BoardDTO selectBoardDetails(Long idx);
	 
	 public int updateBoard(BoardDTO params);
	 
	 public int deleteBoard(Long idx);
	 
	 public List<BoardDTO> selectBoardList(BoardDTO params);
	  
	 public int selectBoardTotalCount(BoardDTO params);
}
