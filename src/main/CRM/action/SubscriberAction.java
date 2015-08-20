package main.CRM.action;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.List;

import main.BaseAction;
import main.CRM.bean.Subscriber;
import main.CRM.service.SubscriberService;

public class SubscriberAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	public SubscriberAction() throws Exception{
		super();
		// TODO Auto-generated constructor stub
	}

	public String input;
	

	SubscriberService subscriberService = new SubscriberService();
	
	public String queryListById(){
		
		System.out.println("input="+input);
		try {
			List<Subscriber> sList = subscriberService.queryListById(input);
			setResult("success", sList);
		} catch (SQLException e) {
			e.printStackTrace();
			StringWriter s = new StringWriter();
			e.printStackTrace(new PrintWriter(s));
			setFail("error", s.toString());
		}
		
		return SUCCESS;
	}
	
	public String queryListByName(){
		
		System.out.println("input="+input);
		try {
			List<Subscriber> sList = subscriberService.queryListByName(input);
			setResult("success", sList);
		} catch (SQLException e) {
			e.printStackTrace();
			StringWriter s = new StringWriter();
			e.printStackTrace(new PrintWriter(s));
			setFail("error", s.toString());
		}
		
		return SUCCESS;
	}
	
	public String queryDataById(){
		
		System.out.println("input="+input);
		try {
			Subscriber s = subscriberService.queryDataById(input);
			setResult("success", s);
		} catch (SQLException e) {
			e.printStackTrace();
			StringWriter s = new StringWriter();
			e.printStackTrace(new PrintWriter(s));
			setFail("error", s.toString());
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
