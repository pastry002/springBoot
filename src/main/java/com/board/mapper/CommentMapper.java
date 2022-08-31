package com.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.board.domain.CommentDTO;



@Mapper // XML Mapper에서 메서드의 이름과 일치하는 SQL문 찾아 실행
public interface CommentMapper {

	 public int insertComment(CommentDTO params);
	 
	 public CommentDTO selectCommentDetails(Long idx);
	 
	 public int updateComment(CommentDTO params);
	 
	 public int deleteComment(Long idx);
	 
	 public List<CommentDTO> selectCommentList(CommentDTO params);
	  
	 public int selectCommentTotalCount(CommentDTO params);
}
