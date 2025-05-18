package com.boot.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String main() {
//		return "redirect:/question/list";
		return "index";
	}
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	
}
