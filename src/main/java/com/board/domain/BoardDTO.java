package com.board.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDTO extends CommonDTO{

	private Long idx;
	
	private String title;
	
	private String content;
	
	private String writer;
	
	private int viewCnt;
	
	private String noticeYn;
	
	private String secretYn;
	
	private String fileChangeYn;
	
	private List<Long> fileIdxs;
	
}
