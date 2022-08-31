package com.board.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.board.domain.AttachDTO;
import com.board.domain.BoardDTO;
import com.board.mapper.AttachMapper;
import com.board.mapper.BoardMapper;
import com.board.paging.Pagination;
import com.board.util.FileUtils;

@Service
public class BoardServiceImpl implements BoardService{

		@Autowired
		private BoardMapper boardMapper;
		
		@Autowired
		private AttachMapper attachMapper;
		
		@Autowired
		private FileUtils fileUtils;
		
		@Override
		public boolean registerBoard(BoardDTO params) {
					
			int queryResult = 0;
			
			if(params.getIdx() == null){
					queryResult = boardMapper.insertBoard(params);
			}else{
					queryResult = boardMapper.updateBoard(params);
					
					// 파일이 추가/삭제된 경우
					if("Y".equals(params.getFileChangeYn())) {
						attachMapper.deleteAttach(params.getIdx());
					}
					
					if(CollectionUtils.isEmpty(params.getFileIdxs()) == false) {
						attachMapper.undeleteAttach(params.getFileIdxs());
					}
			
			}
			
			return (queryResult == 1) ? true : false;
		}
		
		@Override
		public boolean registerBoard(BoardDTO params, MultipartFile[] files) {
			
			int queryResult = 1;
			
			if(registerBoard(params) == false) {
				return false;
			}
			
			List<AttachDTO> fileList = fileUtils.uploadFiles(files, params.getIdx());
			if(CollectionUtils.isEmpty(fileList) == false) {
				queryResult = attachMapper.insertAttach(fileList);
				if(queryResult < 1) {
					queryResult = 0;
				}
			}
			
			return (queryResult > 0);
		}
		
		@Override
		public BoardDTO getBoardDetail (Long idx) {
			
			return boardMapper.selectBoardDetails(idx);
		}
		
		@Override
		public boolean deleteBoard(Long idx) {
			
			int queryResult = 0;
			
			BoardDTO board = boardMapper.selectBoardDetails(idx);
			
			if(board != null && "N".equals(board.getDeleteYn())) {
					queryResult = boardMapper.deleteBoard(idx);
			}
			
			return (queryResult == 1) ? true : false;
		}
		
		@Override
		public List<BoardDTO> getBoardList(BoardDTO params){
			
			List<BoardDTO> boardList = Collections.emptyList(); // 비어있는 리스트. add(), get() 사용불가.		결과가 없을 경우 빈 list 리턴하기 위함
			
			int boardTotalCount = boardMapper.selectBoardTotalCount(params);

			Pagination pagination = new Pagination(params);
			pagination.setTotalRecordCount(boardTotalCount);
			
			params.setPagination(pagination);
			
			if(boardTotalCount > 0){
					boardList = boardMapper.selectBoardList(params);
			}
			
			return boardList;
		}
		
		@Override
		public List<AttachDTO> getAttachFileList(Long boardIdx){
			
			int fileTotalCount = attachMapper.selectAttachTotalCount(boardIdx);
			if(fileTotalCount < 1) {
				return Collections.emptyList();
			}
			
			return attachMapper.selectAttachList(boardIdx);
		}
	
}
