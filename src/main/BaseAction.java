package main;

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

	protected Map<String, Object> session;
	protected String result;
	
	
	
	public String setResult(String msg,Object data){
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("msg", msg);
		map.put("data", data);
		
		result = beanToJSONObject(map);
		
		return SUCCESS;
	}
	
	public String setFail(String msg,String error){
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("msg", msg);
		map.put("error", error);
		
		result = beanToJSONObject(map);
		
		return ERROR;
	}
	
	protected String beanToJSONArray(List list){
		JSONArray jo = (JSONArray) JSONObject.wrap(list);
		return jo.toString();
	}
	protected String beanToJSONObject(Object object){
		JSONObject jo = (JSONObject) JSONObject.wrap(object);
		return jo.toString();
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
