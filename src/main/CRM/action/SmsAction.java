package main.CRM.action; 

import java.util.List;

import javax.annotation.Resource;

import main.CRM.bean.SMS;
import main.CRM.service.SmsService;
import main.common.action.BaseAction;

public class SmsAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SmsAction() throws Exception{
		// TODO Auto-generated constructor stub
	}


	String s2tMsisdn;
	String chtMsisdn;
	String startDate;
	String endDate;
	String activatedDate;
	String canceledDate;
	
	@Resource
	SmsService smsService;
	
	public String querySMS(){
		System.out.println("querySMS");
		System.out.println(s2tMsisdn+", "+chtMsisdn+", "+startDate+", "+endDate+","+activatedDate+","+canceledDate);
		try {
			List<SMS> list = smsService.querySMS(s2tMsisdn, chtMsisdn, startDate, endDate,activatedDate,canceledDate);
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

	public SmsService getSmsService() {
		return smsService;
	}

	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

	public String getActivatedDate() {
		return activatedDate;
	}

	public void setActivatedDate(String activatedDate) {
		this.activatedDate = activatedDate;
	}

	public String getCanceledDate() {
		return canceledDate;
	}

	public void setCanceledDate(String canceledDate) {
		this.canceledDate = canceledDate;
	}
	
	
}
