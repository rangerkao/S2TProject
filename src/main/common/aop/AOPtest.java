package main.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;


@Aspect
public class AOPtest {
	/**
	* 定義切入點
	* 第一個*表示方法的傳回值,這裏使用萬用字元,只有傳回值符合條件的才攔截,(!void表示有傳回值)
	* 第一個..表示com.royzhou.aop包及其子包
	* 倒數第二個*表示包下的所有JAVA類都被攔截
	* 最後一個*表示類的所有方法都被攔截
	* (..)表示方法的參數可以任意多個如[(java.lang.String,java.lang.Integer)表示第一個參數是String,第二個參數是int的方法才會被攔截]
	*/
	@Pointcut("execution(* main.common.action.LoginAction.login())") //定義一個切入點,名稱為pointCutMethod(),攔截類的所有方法
	private void pointCutMethod() {
	}

	@Before("pointCutMethod()") //定義前置通知
	public void doBefore() {
	System.out.println("前置通知");
	}

	@AfterReturning("pointCutMethod()") //定義後置通知
	public void doAfterReturning() {
	System.out.println("後置通知");
	}

	@AfterThrowing("pointCutMethod()") //定義例外通知
	public void doAfterException() {
	System.out.println("異常通知");
	}

	@After("pointCutMethod()") //定義最終通知
	public void doAfter() {
	System.out.println("最終通知");
	}

	@Around("pointCutMethod()") //定義環繞通知
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
	System.out.println("進入方法");
	Object object = pjp.proceed(); //必須執行pjp.proceed()方法,如果不執行此方法,業務bean的方法以及後續通知都不執行
	System.out.println("退出方法");
	return object;
	}
}
