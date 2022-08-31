package com.board.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO extends CommonDTO {

	/** 댓글 번호 */
	private Long idx;

	/** 게시글 번호 */
	private Long boardIdx;
	
	/** 댓글 내용 */
	private String content;

	/** 작성자 */
	private String writer;
	
	
}
