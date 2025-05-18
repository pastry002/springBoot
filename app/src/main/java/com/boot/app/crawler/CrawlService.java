package com.boot.app.crawler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrawlService {

	private final CrawlDataRepository crawlDataRepository;
	
	public List<CrawlData> getList() {
		List<CrawlData> crawlDataList = crawlDataRepository.findAll(Sort.by(Sort.Direction.DESC, "volume"));
		return crawlDataList;
	}
	
    @Transactional
    public void crawlData() {
    	
    	WebDriverManager.chromedriver().setup();
    	//WebDriverManager.chromedriver().driverVersion("136.0.7103.94").setup();
    	ChromeOptions options = new ChromeOptions();
    	options.addArguments("--headless"); // GUI 없이 실행 (백그라운드 실행)
    	options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
    	options.addArguments("--no-sandbox"); // 보안기능 비활성화
    	options.addArguments("--disable-gpu"); // 하드웨어 가속 비활성화
    	options.addArguments("--disable-dev-shm-usage");
    	options.addArguments("--ignore-ssl-errors=yes");
        options.addArguments("--ignore-certificate-errors");
//    	options.addArguments("--start-maximized");
//    	options.addArguments("--disable-notifications");
        options.setBinary(System.getenv("CHROME_BIN")); // Docker 환경 변수에서 Chrome 실행 파일 경로 가져오기
    	
    	WebDriver driver = new ChromeDriver(options);
        
        String url = "https://finance.naver.com/sise/sise_market_sum.naver";
        Actions actions = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor)driver;

        try {
            driver.get(url);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            log.info("페이지 로딩 완료: " + url);
//            js.executeScript("window.scrollBy(0,500)", "");
            List<CrawlData> crawlDataList = new ArrayList<>();
            
            // 체크박스 해제
            WebElement checkBox1 = driver.findElement(By.xpath("//div[@class='subcnt_sise_item_top sub810t']//label[normalize-space()='외국인비율']"));
            WebElement checkBox2 = driver.findElement(By.xpath("//div[@class='subcnt_sise_item_top sub810t']//label[normalize-space()='상장주식수']"));
            
            checkBox1.click();
            checkBox2.click();
            driver.findElement(By.xpath("//div[@class='item_btn']/a[1]")).click();
            log.info("적용하기 클릭 완료");
            Thread.sleep(300);
            
//            WebElement table = driver.findElement(By.xpath("//table[@class='type_2']"));
            List<WebElement> tdList = driver.findElements(By.xpath("//div[@class='box_type_l']//table//td[@class='no']"));
            
            crawlDataList = crawlList(tdList);
            crawlDataRepository.saveAll(crawlDataList);
            
            driver.quit();

        } catch (Exception e) {
            System.err.println("크롤링 중 오류 발생: " + e.getMessage());
        } 
    }
    
    private List<CrawlData> crawlList(List<WebElement> tdList) {
    	List<CrawlData> resultList = new ArrayList<>();
    	
    	for(WebElement td : tdList) {
        	WebElement trEl = td.findElement(By.xpath("./parent::tr"));
        	
        	WebElement companyEl = trEl.findElement(By.xpath("./td[2]"));
        	WebElement priceEl = trEl.findElement(By.xpath("./td[3]"));
        	WebElement fluctuationRateEl = trEl.findElement(By.xpath("./td[5]"));
        	WebElement volumeEl = trEl.findElement(By.xpath("./td[8]"));
        
        	String company = companyEl.getText();
        	int price = Integer.parseInt(priceEl.getText().replaceAll("[^0-9]", ""));
        	String fluctuationRate = fluctuationRateEl.getText();
        	int volume = Integer.parseInt(volumeEl.getText().replaceAll("[^0-9]", ""));
        	
        	log.info("종목명 :: {}", company);
        	log.info("현재가 :: {}", price);
        	log.info("등락률 :: {}", fluctuationRate);
        	log.info("시가총액 :: {}", volume);
        	log.info("==============================");
        	
        	CrawlData crawlData = CrawlData.builder()
        			.company(company)
        			.currentPrice(price)
        			.fluctuationRate(fluctuationRate)
        			.volume(volume)
        			.crawledTime(LocalDateTime.now())
        			.build();
        	
        	resultList.add(crawlData);
        }
    	return resultList;
    }

}