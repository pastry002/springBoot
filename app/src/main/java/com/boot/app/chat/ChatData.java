package com.boot.app.chat;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatData {
	
	private String sender;
	private String content;
	private MessageType type;
	private LocalDateTime sendTime;
	
	public enum MessageType {
		ENTER, CHAT, LEAVE
	}
}
