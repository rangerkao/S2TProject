package main.common.aop;


import java.util.Map;

import main.CRM.bean.Subscriber;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.opensymphony.xwork2.ActionContext;
@Repository
@Aspect
public class ActionLogAOP {
	
	
	@Pointcut("execution(* main.CRM.service..*(..)) ||execution(* main.CRM.service.SubscriberService.updateSubscriber(..))") //定義環繞通知
	private void pointCutMethod() {
		
	}
	
	@Around("pointCutMethod()") //定義環繞通知
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
	Object object = pjp.proceed();
	System.out.println("Class:"+pjp.getStaticPart());
	for(Object s:pjp.getArgs())
		System.out.println("args:"+s);
	System.out.println("Object:"+object);
	return object;
	}
	
	@Before("pointCutMethod()") //定義環繞通知
	public void  doBefore(JoinPoint jp) throws Throwable {
		//System.out.println("before:");
		//System.out.println(jp.getTarget());
		
	}
	
	@After("pointCutMethod()") //定義環繞通知
	public void  doAfter(JoinPoint jp) throws Throwable {
		/*System.out.println("before:");
		System.out.println(jp.getTarget());
		for(Object o:jp.getArgs())
			System.out.println(o);*/
	}
	/*@AfterReturning(pointcut ="pointCutMethod()",returning="returnVal")
	public void doAfterReturning(JoinPoint jp,String returnVal){
		System.out.println("returnVal:"+returnVal);
		ActionContext context = ActionContext.getContext();
		Map<String, Object> session = context.getSession();
		String account = (String) session.get("s2t.account");
		System.out.println("account:"+account);
		System.out.print("Param:");
		for(Object o:jp.getArgs())
			System.out.print(o+",");
		System.out.println();
		System.out.println("Class:"+jp.getStaticPart());
		System.out.println("Annotation driven:After returning "+jp.getSignature().getName()+"()");
		
	}*/
}
