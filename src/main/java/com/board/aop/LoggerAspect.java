package com.board.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component // 스프링 컨테이너에 Bean으로 등록하기 위함. (Bean >> 개발자가 제어할 수 없는 외부 라이브러리를 Bean으로 등록. Component >> 개발자가 직접 정의한 클래스를 등록)
@Aspect // AOP 기능을 하는 클래스
public class LoggerAspect {

	 	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	 	
	 	//Around : 메서드의 호출 자체를 제어. 호출 이전/이후 모두 적용
	 	//execution 포인트컷(어떤 조인 포인트를 사용할지 결정)을 지정 
	 	//* com.board..controller.*Controller.*(..) : com.board 패키지의 하위 패키지 중 controller로 시작하는 패키지에서 oooController와 같은 패턴의 이름의 클래스 중 파라미터가 0개 이상인 메서드
	 	@Around("execution(* com.board..controller.*Controller.*(..)) or execution(* com.board..service.*Impl.*(..)) or execution(* com.board..mapper.*Mapper.*(..))")
	 	public Object printLog(ProceedingJoinPoint  joinPoint) throws Throwable{ // joinPoint : 어드바이스(실제 부가기능을 구현한 객체)를 적용할 위치
	 				String type = "";
	 				String name = joinPoint.getSignature().getDeclaringTypeName(); // getSignature 실행되는 대상 객체의 메서드에 대한 정보
	 				// 메서드의 정보를 가지고 있는 Signature 객체에 담긴 파일명을 포함한 전체 패키지경로를 name에 담아 로그에 출력
	 				
	 				if(name.contains("Controller") == true) {
	 						type = "Controller ===>   ";
	 				} else if(name.contains("Service") == true) {
	 						type = "ServiceImpl ===>   ";
	 				} else if(name.contains("Mapper") == true) {
	 						type = "Mapper ===>   ";
	 				}
	 				logger.debug(type + name + "." + joinPoint.getSignature().getName() + "()");
	 				return joinPoint.proceed();
	 	}
}
