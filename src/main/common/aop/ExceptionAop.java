package main.common.aop;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.stereotype.Repository;
@Repository
@Aspect
public class ExceptionAop{

	@AfterThrowing(pointcut = "execution(* main.CRM.service..*(..)) || execution(* main.DVRS.service..*(..))", throwing = "ex")
	private void pointCutMethod(Throwable ex) {
		System.out.println("Exception caught!");
		//ex.printStackTrace();
	}
}
