package main.common.aop;


import java.util.Date;
import java.util.Map;

import main.common.dao.ActionDao;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Repository;

import com.opensymphony.xwork2.ActionContext;
@Repository
@Aspect
public class ActionLogAOP {
	
	
	@Pointcut(
			"execution(* main.CRM.service.ApplicationService.insertNew(..)) ||"
			/*+ "execution(* main.CRM.service.SubscriberService.updateSubscriber(..))||"*/
			+ "execution(* main.CRM.service.NameVarifiedService.updateNameVarifiedData(..))||"
			+ "execution(* main.CRM.service.NameVarifiedService.addNameVarifiedData(..))||"
			+ "execution(* main.CRM.service.ExcelService.getExcel(..))||"
			+ "execution(* main.CRM.service.SubscriberService.*(..))") //定義環繞通知
	private void pointCutMethod() {
		
	}
	
	@Around("pointCutMethod()") //定義環繞通知
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("ActionLogAOP");
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
		String result = (object==null ?"":object.toString());
		new ActionDao().insertAction(account, params, function, result); 
		return object;
	}
}
