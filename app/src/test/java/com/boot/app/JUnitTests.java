package com.boot.app;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.boot.app.question.QuestionService;

@SpringBootTest
public class JUnitTests {
	
	@Autowired
	private QuestionService questionService;
	
//	@Test
//	void testJpa() {
//		for (int i = 1; i <= 300; i++) {
//			String subject = String.format("테스트 데이터 [%03d]", i);
//			String content = "test data";
//			this.questionService.create(subject, content, null);
//		}
//	}
	
//	@Test
//	public void crawlingSelenium(String url) throws InterruptedException, IOException {
//		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
//
//		WebDriver driver = new ChromeDriver();
//
//		driver.get("<https://www.naver.com/>");
//
//		System.out.println(driver.getPageSource());
//
//		driver.quit();
//	}
}
