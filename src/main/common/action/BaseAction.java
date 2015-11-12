package main.common.action;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.json.JSONArray;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements SessionAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static Map<String, Object> session;
	protected static String result;
	
	
	public static String setSuccess(Object data){
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("data", data);
		
		result = beanToJSONObject(map);
		
		return SUCCESS;
	}
	
	public static String errorHandle(Exception e){
		e.printStackTrace();
		StringWriter s = new StringWriter();
		e.printStackTrace(new PrintWriter(s));
		return setFail(s.toString());
	}
	
	public static String setFail(String msg){
		
		Map<String,String> map = new HashMap<String,String>();
		map.put(ERROR, msg);
		
		result = beanToJSONObject(map);
		
		return SUCCESS;
	}
	
	protected String beanToJSONArray(List list){
		JSONArray jo = (JSONArray) JSONObject.wrap(list);
		return jo.toString();
	}
	protected static String beanToJSONObject(Object object){
		JSONObject jo = (JSONObject) JSONObject.wrap(object);
		return jo.toString();
	}
	protected static JSONObject jsonToJSONObject(String json){
		return new JSONObject(json);
	}
	
	//------------------------------------//
	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;
		
	}
	public Map<String, Object> getSession() {
		return session;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
