package com.board.domain;

import java.util.Date;

import com.board.paging.Criteria;
import com.board.paging.Pagination;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonDTO extends Criteria {

	/** 페이징 정보 */
	private Pagination pagination;

	/** 삭제 여부 */
	private String deleteYn;
	
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone="Asia/Seoul") 
	
	/** 등록일 */
	private Date createTime;

	/** 수정일 */
	private Date updateTime;

	/** 삭제일 */
	private Date deleteTime;
}
