package com.board.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.board.domain.CovidDTO;
import com.board.service.CovidService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MainController {
	
	private final CovidService covidService;
	
	@GetMapping(value = "/board")
	public String main(Model model) throws IOException {
		
		List<CovidDTO> covidStatsList = covidService.getCovidDatas();
		
		model.addAttribute("covidStats", covidStatsList);

		return "board/covid19";
	}
	
	
}
