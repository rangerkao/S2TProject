package main.common.interception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class JsonInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JsonInterceptor() {

	}

	@Override  
    public String intercept(ActionInvocation invocation) throws Exception{
		
		ActionContext actionContext = invocation.getInvocationContext();  
        HttpServletRequest request= (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);  
          
        Map<String, Object> session = actionContext.getSession();  
		String account = (String) session.get("s2t.account");
        System.out.println("JsonInterceptor Session  Account:"+account+",role:"+session.get("s2t.role"));

	
		if(account != null)
			return  invocation.invoke();

		return "needLogin";
	}
		
}
