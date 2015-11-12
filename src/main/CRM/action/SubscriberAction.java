package main.CRM.action;


import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONString;

import main.CRM.bean.SMS;
import main.CRM.bean.Subscriber;
import main.CRM.service.SubscriberService;
import main.common.action.BaseAction;

public class SubscriberAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	public SubscriberAction() throws Exception{
		super();
	}

	public String input;

	SubscriberService subscriberService = new SubscriberService();
	
	public String queryListById(){
		System.out.println("queryListById...");
		System.out.println("input="+input);
		try {
			List<Subscriber> sList = subscriberService.queryListById(input);
			setSuccess(sList);
		} catch (SQLException e) {
			errorHandle(e);
		}
		
		return SUCCESS;
	}
	
	public String queryListByName(){
		System.out.println("queryListByName");
		System.out.println("input="+input);
		try {
			List<Subscriber> sList = subscriberService.queryListByName(input);
			setSuccess(sList);
		} catch (SQLException e) {
			errorHandle(e);
		}
		
		return SUCCESS;
	}
	
	public String queryListByS2tMisidn(){
		
		System.out.println("input="+input);
		try {
			List<Subscriber> sList = subscriberService.queryListByS2tMisidn(input);
			setSuccess(sList);
		} catch (SQLException e) {
			errorHandle(e);
		}
		
		return SUCCESS;
	}

	public String queryListByChtMsisdn(){
		
		System.out.println("input="+input);
		try {
			List<Subscriber> sList = subscriberService.queryListByChtMsisdn(input);
			setSuccess(sList);
		} catch (SQLException e) {
			errorHandle(e);
		}
		
		return SUCCESS;
	}
	
	public String queryDataById(){
		System.out.println("queryDataById");
		System.out.println("input="+input);
		try {
			Subscriber s = subscriberService.queryDataById(input);
			setSuccess(s);
		} catch (SQLException e) {
			errorHandle(e);
		}
		return SUCCESS;
	}
	
	public String queryServiceIdList(){
		System.out.println("queryServiceIdList");
		System.out.println("input="+input);
		try {
			List<String> s = subscriberService.queryServiceIdList(input);
			setSuccess(s);
		} catch (SQLException e) {
			errorHandle(e);
		}
		return SUCCESS;
	}
	
	public String queryDataByServiceId(){
		System.out.println("input="+input);
		try {
			Subscriber s = subscriberService.queryDataByServiceId(input);
			setSuccess(s);
		} catch (SQLException e) {
			errorHandle(e);
		}
		return SUCCESS;
	}
	
	public String updateSubscriber(){
		System.out.println("input="+input);
		JSONObject j= jsonToJSONObject(input);
		Subscriber s = new Subscriber(j);
		try {
			subscriberService.updateSubscriber(s);
			setSuccess(s);
		} catch (SQLException e) {
			errorHandle(e);
		} catch (Exception e) {
			errorHandle(e);
		}
		return SUCCESS;
	}

	/******************************************************************/
	
	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}	
}
