package com.boot.app.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@CrossOrigin
public class AIController {

    private final ChatClient chatClient;

    public AIController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }
    
    @GetMapping("/chatbot_form")
    public ModelAndView getChatbotForm() {
    	ModelAndView mav = new ModelAndView("chatbot_form");
    	return mav;
    }

    @PostMapping("/chat")
    public String chat(@RequestParam("message") String message) {

    	log.info("input message : {}", message);
    	String response =  chatClient.prompt()
                		.user(message)
                		.call()
                		.content();
        return response;
    }

    @GetMapping("/stream")
    public Flux<String> chatWithStream(@RequestParam("message") String message) {
        return chatClient.prompt()
                .user(message)
                .stream()
                .content();
    }

}