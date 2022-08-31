package com.board.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jboss.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.board.domain.CovidDTO;

@Service
public class CovidServiceImpl implements CovidService {

	private static String COVID_URL = "http://ncov.mohw.go.kr/bdBoardList_Real.do?brdId=1&brdGubun=13";
	
	@Override
	@PostConstruct // 의존성 주입 후 초기화를 수행. 오직 한 번만 수행된다는 것을 보장
	public List<CovidDTO> getCovidDatas() throws IOException{
		
		List<CovidDTO> covidStatsList = new ArrayList<>();
		Document doc = Jsoup.connect(COVID_URL).get();
		
		Elements contents = doc.getElementsByClass("num midsize").select("tbody tr");
		Elements timeInfo = doc.getElementsByClass("timetable").select("p");
				
		CovidDTO covidStats = null;
		
		for(Element content : contents) {
			covidStats = covidStats.builder()
					.province(content.select("th").text())
					.diffFromPrevDay(content.select("td").get(0).text())
					.totalPatient(content.select("td").get(3).text())
					.death(content.select("td").get(4).text())
					.incidenceRate(content.select("td").get(5).text())
					.build();
			
			covidStatsList.add(covidStats);
		}
		
		String time = timeInfo.text();
		covidStatsList.get(0).setTimeTable(time);

		return covidStatsList;
	}
}
