package com.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.board.domain.AttachDTO;



@Mapper // XML Mapper에서 메서드의 이름과 일치하는 SQL문 찾아 실행
public interface AttachMapper {

	 public int insertAttach(List<AttachDTO> attachList);
	 
	 public AttachDTO selectAttachDetail(Long idx);
	 
	 public int deleteAttach(Long boardIdx);
	 
	 public List<AttachDTO> selectAttachList(Long boardIdx);
	 
	 public int selectAttachTotalCount(Long boardIdx);

	 public int undeleteAttach(List<Long> idxs);
}
