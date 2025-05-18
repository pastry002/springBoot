package com.boot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import com.boot.app.crawler.CrawlService;

@Configuration
public class SchedulerConfig {

	private final CrawlService crawlingService;
	
	@Autowired
    public SchedulerConfig(CrawlService crawlingService) {
        this.crawlingService = crawlingService;
    }

    @Scheduled(fixedRate = 1000 * 60 * 30, initialDelay = 1000 * 60 * 10) // 1시간 (3600 * 1000 밀리초)마다 실행
    public void doCrawling() {
        System.out.println("크롤링 시작: " + new java.util.Date());
        crawlingService.crawlData();
        System.out.println("크롤링 완료: " + new java.util.Date());
    }
}
