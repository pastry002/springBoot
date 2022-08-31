package com.board.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.board.domain.AttachDTO;
import com.board.exception.AttachFileException;

@Component
public class FileUtils {

		private final String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
		
		private final String uploadPath = Paths.get("C:", "develop", "upload", today).toString();
		
//		서버에 생성할 파일명을 처리할 랜덤문자열 반환
		private final String getRandomString() {
			return UUID.randomUUID().toString().replaceAll("-", "");
		}
		
//		서버에 첨부할 파일생성, 업로드 파일목록 반환
		public List<AttachDTO> uploadFiles(MultipartFile[] files, Long boardIdx){
			
			List<AttachDTO> attachList = new ArrayList<>();
			
			File dir = new File(uploadPath);
			if(dir.exists() == false) { // 해당 디렉터리가 존재하지 않을 경우 부모를 포함한 모든 디렉터리 생성
				dir.mkdirs();
			}
			
			for(MultipartFile file : files) {
				
				if(file.getSize() < 1) {
					continue;
				}
				
				try {
					// 파일 확장자
					final String extension = FilenameUtils.getExtension(file.getOriginalFilename());
					//서버에 저장할 파일명
					final String saveName = getRandomString() + "." + extension;
					
					// 업로드 경로에 파일 생성
					File target = new File(uploadPath, saveName);
					// target에 담긴 정보로 파일 생성
					file.transferTo(target);				
					
					AttachDTO attach = new AttachDTO();
					attach.setBoardIdx(boardIdx);
					attach.setOriginalName(file.getOriginalFilename());
					attach.setSaveName(saveName);
					attach.setSize(file.getSize());
					
					attachList.add(attach);
					
				} catch (IOException e) {
					
					e.printStackTrace();
					throw new AttachFileException("[" + file.getOriginalFilename() + "] failed to save file");
			
				} catch (Exception e) {
					
					e.printStackTrace();
					throw new AttachFileException("[" + file.getOriginalFilename() + "] failed to save file");
				}
			}
			return attachList;
		}
}
