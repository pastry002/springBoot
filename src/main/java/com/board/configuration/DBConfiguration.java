package com.board.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration // 클래스를 자바 기반 설정파일로 인식
@PropertySource("classpath:/application.properties") // 참조할 properties 파일의 위치
@EnableTransactionManagement // 스프링에서 제공하는 어노테이션 기반 트랜잭션을 활성화
public class DBConfiguration {

	@Autowired // Bean으로 등록된 인스턴스를 클래스에 주입
	private ApplicationContext applicationContext;
	
	@Bean 
	public PlatformTransactionManager transactionManager() { 
					return new DataSourceTransactionManager(dataSource());
	}
	
	
	@Bean // 컨테이너에 의해 관리되는 Bean으로 등록 (Configuration 클래스의 메서드 레벨에서만 지정 가능)
	@ConfigurationProperties(prefix = "spring.datasource.hikari") // prefix에 해당하는 설정을 모두 읽어 해당 메서드에 바인딩
	public HikariConfig hikariConfig() {
		return new HikariConfig(); // 커넥션 풀 라이브러리
	}
	
	@Bean
	public DataSource dataSource() { // dataSource : dataSource 객체 생성. 커넥션 풀을 지원하기 위한 인터페이스
			return new HikariDataSource(hikariConfig());
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception{
		// SqlSessionFactoryBean : myBatis - spring간 연동 모듈
		// xml mapper, 설정 파일 위치 등을 지정하고 getObject 메서드가 리턴하는 SqlSessionFactory 생성
			
			SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
			factoryBean.setDataSource(dataSource());
			factoryBean.setMapperLocations(applicationContext.getResources("classpath:/mappers/**/*Mapper.xml"));
			factoryBean.setTypeAliasesPackage("com.board.*");
			factoryBean.setConfiguration(mybatisConfg());
			return factoryBean.getObject();
	}
	
	@Bean
	//sqlSessionTemplate : sqlSessionFactory를 통해 생성됨. SQL의 실행에 필요한 모든 메서드를 갖는 객체
	public SqlSessionTemplate sqlSession() throws Exception {
			return new SqlSessionTemplate(sqlSessionFactory());
	}
	
	@Bean
	@ConfigurationProperties(prefix = "mybatis.configuration")
	public org.apache.ibatis.session.Configuration mybatisConfg(){
		return new org.apache.ibatis.session.Configuration();
	}
}
