package main.CRM.action;

import java.util.List;

import javax.annotation.Resource;

import main.CRM.bean.AddonService;
import main.CRM.bean.USPacket;
import main.CRM.service.ElseService;
import main.common.action.BaseAction;

public class ElseAction extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	ElseService elseService;
	
	public String input;
	
	public String queryVLN(){
		System.out.println("queryVLN...");
		System.out.println("input="+input);
		try {
			List<String> vlns = elseService.queryVLN(input);
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
			List<AddonService> addons = elseService.queryAddonService(input);
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
			String status = elseService.getGPRSStatus(input);
			setSuccess(status);
		} catch (Exception e) {
			errorHandle(e);
		}
		return SUCCESS;
	}
	
	public String queryUSPacket(){
		System.out.println("queryUSPacket...");
		System.out.println("input="+input);
		try {
			List<USPacket> uSPacket = elseService.queryUSPacket(input);
			setSuccess(uSPacket);
		} catch (Exception e) {
			errorHandle(e);
		} 
		return SUCCESS;
	}
	
	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public ElseService getElseService() {
		return elseService;
	}

	public void setElseService(ElseService elseService) {
		this.elseService = elseService;
	}

	
}
