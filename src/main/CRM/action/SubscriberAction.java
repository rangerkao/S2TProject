package main.CRM.action;


import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import main.BaseAction;
import main.CRM.bean.SMS;
import main.CRM.bean.Subscriber;
import main.CRM.service.SubscriberService;

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
		
		System.out.println("input="+input);
		try {
			List<Subscriber> sList = subscriberService.queryListById(input);
			setResult("success", sList);
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
			setResult("success", sList);
		} catch (SQLException e) {
			errorHandle(e);
		}
		
		return SUCCESS;
	}
	
	public String queryListByS2tMisidn(){
		
		System.out.println("input="+input);
		try {
			List<Subscriber> sList = subscriberService.queryListByS2tMisidn(input);
			setResult("success", sList);
		} catch (SQLException e) {
			errorHandle(e);
		}
		
		return SUCCESS;
	}

	public String queryListByChtMsisdn(){
		
		System.out.println("input="+input);
		try {
			List<Subscriber> sList = subscriberService.queryListByChtMsisdn(input);
			setResult("success", sList);
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
			setResult("success", s);
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
			setResult("success", s);
		} catch (SQLException e) {
			errorHandle(e);
		}
		return SUCCESS;
	}
	
	public String queryDataByServiceId(){
		System.out.println("input="+input);
		try {
			Subscriber s = subscriberService.queryDataByServiceId(input);
			setResult("success", s);
		} catch (SQLException e) {
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
