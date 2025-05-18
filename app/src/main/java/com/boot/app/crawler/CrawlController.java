package com.boot.app.crawler;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequestMapping("/crawl")
@RequiredArgsConstructor
@Controller
public class CrawlController {
	
	private final CrawlService crawlService;
	
	@GetMapping("/list")
	public String list(Model model) {
		
		List<CrawlData> crawlDataList = crawlService.getList();
		model.addAttribute("crawlDataList", crawlDataList);
		
		return "crawl_list";
	}
	
}