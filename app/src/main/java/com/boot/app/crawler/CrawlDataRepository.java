package com.boot.app.crawler;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CrawlDataRepository extends JpaRepository<CrawlData, String> {

	@Transactional
	@Modifying
	@Query(value = "truncate something_table", nativeQuery = true)
	void truncateSomethingTable();
}
