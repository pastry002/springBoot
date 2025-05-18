package com.boot.app.crawler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CrawlScheduler {
	
	private final CrawlService crawlService;

//	@Scheduled(cron = "0 */3 * * * *")
    @Scheduled(fixedRate = 1000 * 60 * 30, initialDelay = 1000 * 60 * 5)
    public void scheduleCrawl() {
        log.info("crawl start");
        crawlService.crawlData();
        log.info("crawl end");
    }
}
