package main.common.aop;


import java.util.Map;

import main.CRM.bean.Subscriber;
import main.common.dao.ActionDao;

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
	
	
	@Pointcut("execution(* main.CRM.service.ApplicationService.insertNew(..)) ||execution(* main.CRM.service.SubscriberService.updateSubscriber(..))") //定義環繞通知
	private void pointCutMethod() {
		
	}
	
	@Around("pointCutMethod()") //定義環繞通知
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		
	ActionContext context = ActionContext.getContext();
	Map<String, Object> session = context.getSession();
	String account = (String) session.get("s2t.account");
	
	String params = "";
	for(Object s:pjp.getArgs())
		params+=s+",";
	
	if(!"".equals(params))
		params=params.substring(0,params.length()-1);
	
	String function = pjp.getStaticPart().toString();
	
	Object object = pjp.proceed();
	String result = object.toString();
	
	ActionDao.insertAction(account, params, function, result);
	return object;
	}
}
