package com.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.board.constant.Method;
import com.board.domain.AttachDTO;
import com.board.domain.BoardDTO;
import com.board.service.BoardService;
import com.board.util.Utils;

@Controller
public class BoardController extends Utils {
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping(value = "/board/write.do")
	public String openBoardWrite(@ModelAttribute("params") BoardDTO params, @RequestParam(value="idx", required = false) Long idx, Model model) {
		
		if(idx == null) {
				model.addAttribute("board", new BoardDTO());
		}else {
				BoardDTO board = boardService.getBoardDetail(idx);
				if(board == null || "Y".equals(board.getDeleteYn())) { 
						return showMessageWithRedirect ("존재하지 않는 게시글이거나 이미 삭제된 게시글입니다.", "/board/list.do", Method.GET, null, model);
				}
				model.addAttribute("board", board);
				
				List<AttachDTO> fileList = boardService.getAttachFileList(idx);
				model.addAttribute("fileList", fileList);
		}
		
		return "board/write";
	}
	
	@PostMapping(value = "/board/register.do")
	public String registerBoard(final BoardDTO params, final MultipartFile[] files, Model model) {
			
			Map<String, Object> pagingParams = getPagingParams(params);
		
			try {
					boolean isRegistered = boardService.registerBoard(params, files);
					if(isRegistered == false) {
						
						return showMessageWithRedirect("게시글 등록에 실패하였습니다.", "/board/list.do", Method.GET, pagingParams, model);
				}
			} catch (DataAccessException e) {
			
					e.printStackTrace();
					return showMessageWithRedirect("데이터베이스 처리 과정에서 문제가 발생하였습니다.", "/board/list.do",Method.GET, pagingParams, model);
						
			} catch (Exception e) {
				
					e.printStackTrace();
					return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/board/list.do",Method.GET, pagingParams, model);
			}
			
			return showMessageWithRedirect("게시글 등록이 완료되었습니다.", "/board/list.do",Method.GET, pagingParams, model);
	}
	
	@GetMapping(value = "/board/list.do")
	public String openBoardList(@ModelAttribute("params") BoardDTO params, Model model) { 
		// @ModelAttribute로 전달받은 객체를 자동으로 뷰까지 전달. 
		// "criteria" = alias. 뷰에서 ${criteria.currentPageNo}과 같은 방식으로 객체에 접근 가능
		List<BoardDTO> boardList = boardService.getBoardList(params);
		model.addAttribute("boardList", boardList);
		
		return "board/list";
	}
	
	@GetMapping(value = "/board/view.do")
	public String openBoardDetail(@ModelAttribute("params") BoardDTO params, @RequestParam(value = "idx", required = false) Long idx, Model model) {
		
		if(idx == null) {
			return showMessageWithRedirect ("올바르지 않은 접근입니다.", "/board/list.do", Method.GET, null, model);
		}
		
		BoardDTO board = boardService.getBoardDetail(idx);
		if(board == null || "Y".equals(board.getDeleteYn())) {
			return showMessageWithRedirect ("없는 게시글이거나 이미 삭제된 게시글입니다.", "/board/list.do", Method.GET, null, model);
		}
		
		model.addAttribute("board", board);
		
		return "board/view";
	}
	
	@PostMapping(value = "/board/delete.do")
	public String deleteBoard(@ModelAttribute("params") BoardDTO params, @RequestParam(value = "idx", required = false) Long idx, Model model) {
			
		if(idx == null) {
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/board/list.do", Method.GET, null, model);
		}
		
		Map<String, Object> pagingParams = getPagingParams(params);
		
		try {
				boolean isDeleted = boardService.deleteBoard(idx);
				
				if(isDeleted == false) {
					
					return showMessageWithRedirect("게시글 삭제에 실패하였습니다.", "/board/list.do", Method.GET, pagingParams, model);
			}
		} catch (DataAccessException e) {
				
				e.printStackTrace();
				return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/board/list.do", Method.GET, pagingParams, model);
							
		} catch (Exception e) {
				
				e.printStackTrace();
				return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/board/list.do", Method.GET, pagingParams, model);
		}
		
		return showMessageWithRedirect("게시글 삭제가 완료되었습니다.", "/board/list.do", Method.GET, pagingParams, model);
	}
	
	
}
