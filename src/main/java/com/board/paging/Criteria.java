package com.board.paging;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Criteria {

		/* 현재 페이지 번호 */
		private int currentPageNo;

		/* 페이지당 출력 데이터 */
		private int recordsPerPage;
		
		/* 페이지 사이즈 */
		private int pageSize;
		
		/* 검색 키워드 */
		private String searchKeyword;
		
		/* 검색 유형 */
		private String searchType;

		public Criteria() {
			this.currentPageNo = 1;
			this.recordsPerPage = 15;
			this.pageSize = 10;
		}
		
		public String makeQueryString(int pageNo) { // Criteria 클래스의 멤버변수들을 쿼리스트링 형태로 반환
			
			UriComponents uriComponents = UriComponentsBuilder.newInstance()
																		.queryParam("currentPageNo", pageNo)
																		.queryParam("recordsPerPage", recordsPerPage)
																		.queryParam("pageSize", pageSize)
																		.queryParam("searchType", searchType)
																		.queryParam("searchKeyword", searchKeyword)
																		.build()
																		.encode();
			
			return uriComponents.toUriString();
		}
}

