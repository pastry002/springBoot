package com.board;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import com.board.domain.BoardDTO;
import com.board.mapper.BoardMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
public class MapperTests {
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Test
	public void testOfcreate() {
		BoardDTO params = new BoardDTO();
		params.setTitle("제목");
		params.setContent("게시글 내용");
		params.setWriter("테스터");
		
		int result = boardMapper.insertBoard(params);
		System.out.println("결과 = " + result);
	}
	
	@Test
	public void testOfSelectDetail() {
		BoardDTO board = boardMapper.selectBoardDetails((long) 1);
		try {
				//String boardJson = new ObjectMapper().writeValueAsString(board);
				// board에 저장된 게시글 정보롤 Jackson 라이브러리를 통해 JSON으로 변경 후 콘솔에 출력
				String boardJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(board);
				
				System.out.println("========================");
				System.out.println(boardJson);
				System.out.println("========================");
		} catch (Exception e) {
				e.printStackTrace();
		}
	}
	
	@Test
	public void testOfDelete() {
		int result = boardMapper.deleteBoard((long) 1);
		if(result == 1)
		{
			BoardDTO board = boardMapper.selectBoardDetails((long) 1);
			try {
					String boardJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(board);
					
					System.out.println("========================");
					System.out.println(boardJson);
					System.out.println("========================");
				
			} catch (Exception e) {
					e.printStackTrace();
			}
		}
			
	}
	
	@Test
	public void testMultipleInsert() {
		for(int i = 2; i <= 50; i++)
		{
			BoardDTO params = new BoardDTO();
			params.setTitle(i + "번 게시글 제목");
			params.setContent(i + "번 게시글 내용");
			params.setWriter(i + "번 게시글 작성자");
			boardMapper.insertBoard(params);
		}
	}
	/*
	@Test
	public void testSelectList() {
		int boardTotalCount = boardMapper.selectBoardTotalCount();
		if(boardTotalCount > 0) {
			List<BoardDTO> boardList = boardMapper.selectBoardList();
			if(CollectionUtils.isEmpty(boardList) == false) // boardList가 비어있지 않은지 체크
			{
				for(BoardDTO board : boardList)
				{
					System.out.println("==========================");
					System.out.println(board.getTitle());
					System.out.println(board.getContent());
					System.out.println(board.getWriter());
					System.out.println("==========================");
				}
			}
		}
	}
	*/
}
