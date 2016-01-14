package main.CRM.action;

import java.util.List;

import javax.annotation.Resource;

import org.json.JSONObject;

import main.CRM.bean.AddonService;
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
	
	@Resource
	SubscriberService subscriberService;
	
	public String queryListById(){
		System.out.println("queryListById...");
		System.out.println("input="+input);
		try {
			List<Subscriber> sList = subscriberService.queryListById(input);
			setSuccess(sList);
		} catch (Exception e) {
			errorHandle(e);
		}
		
		return SUCCESS;
	}
	
	public String queryListByName(){
		System.out.println("queryListByName...");
		System.out.println("input="+input);
		try {
			List<Subscriber> sList = subscriberService.queryListByName(input);
			setSuccess(sList);
		} catch (Exception e) {
			errorHandle(e);
		}
		
		return SUCCESS;
	}
	
	public String queryListByVLN(){
		System.out.println("queryListByVLN...");
		System.out.println("input="+input);
		try {
			List<Subscriber> sList = subscriberService.queryListByVLN(input);
			setSuccess(sList);
		} catch (Exception e) {
			errorHandle(e);
		}
		return SUCCESS;
	}
	
	public String queryListByS2tMisidn(){
		System.out.println("queryListByS2tMisidn...");
		System.out.println("input="+input);
		try {
			List<Subscriber> sList = subscriberService.queryListByS2tMisidn(input);
			setSuccess(sList);
		} catch (Exception e) {
			errorHandle(e);
		}
		
		return SUCCESS;
	}

	public String queryListByChtMsisdn(){
		System.out.println("queryListByChtMsisdn...");
		System.out.println("input="+input);
		try {
			List<Subscriber> sList = subscriberService.queryListByChtMsisdn(input);
			setSuccess(sList);
		} catch (Exception e) {
			errorHandle(e);
		}
		
		return SUCCESS;
	}
	
	public String queryListByMainMsisdn(){
		System.out.println("queryListByChtMsisdn...");
		System.out.println("input="+input);
		try {
			List<Subscriber> sList = subscriberService.queryListByChtMsisdn(input);
			setSuccess(sList);
		} catch (Exception e) {
			errorHandle(e);
		}
		
		return SUCCESS;
	}
	
	public String queryDataById(){
		System.out.println("queryDataById...");
		System.out.println("input="+input);
		try {
			Subscriber s = subscriberService.queryDataById(input);
			setSuccess(s);
		} catch (Exception e) {
			errorHandle(e);
		}
		return SUCCESS;
	}
	
	public String queryServiceIdList(){
		System.out.println("queryServiceIdList...");
		System.out.println("input="+input);
		try {
			List<String> s = subscriberService.queryServiceIdList(input);
			setSuccess(s);
		} catch (Exception e) {
			errorHandle(e);
		}
		return SUCCESS;
	}
	
	public String queryDataByServiceId(){
		System.out.println("queryDataByServiceId...");
		System.out.println("input="+input);
		try {
			Subscriber s = subscriberService.queryDataByServiceId(input);
			setSuccess(s);
		} catch (Exception e) {
			errorHandle(e);
		}
		return SUCCESS;
	}
	
	public String updateSubscriber(){
		System.out.println("updateSubscriber...");
		System.out.println("input="+input);
		JSONObject j= jsonToJSONObject(input);
		Subscriber s = new Subscriber(j);
		try {
			subscriberService.updateSubscriber(s);
			setSuccess(s);
		} catch (Exception e) {
			errorHandle(e);
		} 
		return SUCCESS;
	}
	
	public String queryVLN(){
		System.out.println("queryVLN...");
		System.out.println("input="+input);
		try {
			List<String> vlns = subscriberService.queryVLN(input);
			setSuccess(vlns);
		} catch (Exception e) {
			errorHandle(e);
		} 
		return SUCCESS;
	}
	
	public String queryAddonService(){
		System.out.println("queryAddonService...");
		System.out.println("input="+input);
		try {
			List<AddonService> addons = subscriberService.queryAddonService(input);
			setSuccess(addons);
		} catch (Exception e) {
			errorHandle(e);
		} 
		return SUCCESS;
	}
	
	public String getGPRSStatus(){
		System.out.println("getGPRSStatus...");
		System.out.println("input="+input);
		try {
			String status = subscriberService.getGPRSStatus(input);
			setSuccess(status);
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

	public SubscriberService getSubscriberService() {
		return subscriberService;
	}

	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}	
	
	
}
