package main.CRM.action;


import java.sql.SQLException;
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
	
	public String s2tMsisdn;
	public String chtMsisdn;
	public String startDate;
	public String endDate;
	

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
	
	public String querySMS(){
		System.out.println("querySMS");
		System.out.println("input="+input);
		try {
			List<SMS> list = subscriberService.querySMS(s2tMsisdn, chtMsisdn, startDate, endDate);
			setResult("success", list);
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

	public String getS2tMsisdn() {
		return s2tMsisdn;
	}

	public void setS2tMsisdn(String s2tMsisdn) {
		this.s2tMsisdn = s2tMsisdn;
	}

	public String getChtMsisdn() {
		return chtMsisdn;
	}

	public void setChtMsisdn(String chtMsisdn) {
		this.chtMsisdn = chtMsisdn;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
	
}
