package com.board.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder // 데이터의 순서에 상관없이 객체생성, null check, 불필요한 생성자의 제거 가능
@Getter
@Setter
public class CovidDTO {
	
	/** 기준시간 */
	private String timeTable;
	
	/** 시도명 */
	private String province;
	
	/** 전일대비환자증감 */
	private String diffFromPrevDay;

	/** 확진환자 */
	private String totalPatient;

	/** 사망자 */
	private String death;

	/** 발생률 */
	private String incidenceRate;
	
}
