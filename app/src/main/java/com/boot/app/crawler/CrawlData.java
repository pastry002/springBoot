package com.boot.app.crawler;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
//@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CrawlData {

	@Id
	@Comment(value = "종목명")
	private String company;

	@Comment(value = "현재가")
    private Integer currentPrice;
    
    @Comment(value = "등락률")
    private String fluctuationRate;
    
    @Comment(value = "시가총액")
    private Integer volume;
	
    private LocalDateTime crawledTime;
}
