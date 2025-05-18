package com.boot.app.chat;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/chat")
@Slf4j
@Controller
public class ChatController {

	@MessageMapping("/sendMessage")
	@SendTo("/topic/public") // 구독자에게 전달
	public ChatData sendMessage(ChatData chatData) {
		chatData.setSendTime(LocalDateTime.now());
		return chatData;
	}
	
	@MessageMapping("/userEnter")
	@SendTo("/topic/public") // 구독자에게 전달
	public ChatData newUser(ChatData chatData) {
		
		chatData.setType(ChatData.MessageType.ENTER);
		chatData.setContent(chatData.getSender() + "님이 입장했습니다.");
		chatData.setSendTime(LocalDateTime.now());
		log.info("new user Entered : {}", chatData.getSender());
		
		return chatData;
	}
	
	@MessageMapping("/userDisconnect")
	@SendTo("/topic/public") // 구독자에게 전달
	public ChatData userDisconnect(ChatData chatData) {
		
		chatData.setType(ChatData.MessageType.LEAVE);
		chatData.setContent(chatData.getSender() + "님이 퇴장했습니다.");
		chatData.setSendTime(LocalDateTime.now());
		log.info("user disconnected : {}", chatData.getSender());
		
		return chatData;
	}
	
	@GetMapping("/room")
	public String chatGet( ) {
        return "chat_room";
	}
}
