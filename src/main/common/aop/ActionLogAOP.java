package main.common.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
@Repository
@Aspect
public class ActionLogAOP {
	
	
	@Pointcut("execution(* main.CRM.service..*(..))") //定義環繞通知
	private void pointCutMethod() {
		
	}
	
	@Around("pointCutMethod()") //定義環繞通知
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
	Object object = pjp.proceed();
	return object;
	}
	
	@Before("pointCutMethod()") //定義環繞通知
	public void  doBefore(JoinPoint jp) throws Throwable {
		System.out.println("before:");
		System.out.println(jp.getTarget());
		for(Object o:jp.getArgs())
			System.out.println(o);
	}
	
	@After("pointCutMethod()") //定義環繞通知
	public void  doAfter(JoinPoint jp) throws Throwable {
		System.out.println("before:");
		System.out.println(jp.getTarget());
		for(Object o:jp.getArgs())
			System.out.println(o);
	}
}
