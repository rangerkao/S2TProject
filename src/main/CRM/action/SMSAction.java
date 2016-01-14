package main.CRM.action;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import main.CRM.bean.SMS;
import main.CRM.service.SMSService;
import main.common.action.BaseAction;

public class SMSAction extends BaseAction {

	public SMSAction() throws Exception{
		// TODO Auto-generated constructor stub
	}


	public String s2tMsisdn;
	public String chtMsisdn;
	public String startDate;
	public String endDate;
	
	@Resource
	SMSService sMSService;
	
	public String querySMS(){
		System.out.println("querySMS");
		System.out.println(s2tMsisdn+", "+chtMsisdn+", "+startDate+", "+endDate);
		try {
			List<SMS> list = sMSService.querySMS(s2tMsisdn, chtMsisdn, startDate, endDate);
			setSuccess(list);
		} catch (Exception e) {
			errorHandle(e);
		} 
		return SUCCESS;
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

	public SMSService getsMSService() {
		return sMSService;
	}

	public void setsMSService(SMSService sMSService) {
		this.sMSService = sMSService;
	}
	
	
	
}
