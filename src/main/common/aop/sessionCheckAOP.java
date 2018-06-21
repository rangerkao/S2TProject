package main.common.aop;

import java.util.Date;
import java.util.Map;

import main.common.dao.BaseDao;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import com.opensymphony.xwork2.ActionContext;
@Repository
@Aspect
public class sessionCheckAOP{

	@Pointcut("execution(* main.CRM.action..*(..)) || execution(* main.DVRS.action..*(..))") //定義一個切入點,名稱為pointCutMethod(),攔截類的所有方法
	private void pointCutMethod() {
		
	}
	
	@Around("pointCutMethod()") 
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("sessionCheckAOP");
		/*HttpServletRequest request = SysContent.getRequest();  
        HttpServletResponse response = SysContent.getResponse();  
        HttpSession session = SysContent.getSession(); */ 
		//BaseDao.setRequestTime(new Date());
		
		/*ActionContext context = ActionContext.getContext();
		Map<String, Object> session = context.getSession();
		String account = (String) session.get("s2t.account");
		Object o= "needLogin";
		Object[] args=pjp.getArgs();
		if(account != null){
			o = pjp.proceed();
			System.out.println("ACCount:"+account);
		}*/
		return pjp.proceed();
	}

}
