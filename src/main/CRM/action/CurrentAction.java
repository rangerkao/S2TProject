package main.CRM.action;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import main.CRM.bean.*;
import main.CRM.service.CurrentService;
import main.common.action.BaseAction;


public class CurrentAction extends BaseAction {

	public CurrentAction() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String serviceId;
	String imsi;
	String from;
	String to;
	String suspend;
	
	@Resource
	CurrentService currentService;
	
	
	public String queryCurrentMonth(){
		
		try {
			System.out.println("imsi:"+serviceId+",from:"+serviceId+",to:"+to+",suspend:"+suspend+","+new Date());
			List<CurrentMonth> list = currentService.queryCurrentMonth(serviceId,from.replace("-",""),to.replace("-",""),suspend);
			return setSuccess(list);
		} catch (SQLException e) {
			return errorHandle(e);
		} catch (Exception e) {
			return errorHandle(e);
		}
	}
	
	public String queryCurrentDay(){
		
		try {
			System.out.println("serviceId:"+serviceId+",from:"+from+",to:"+to);
			List<CurrentDay> list = currentService.queryCurrentDay(serviceId,from.replace("-",""),to.replace("-",""));
			return setSuccess(list);
		} catch (SQLException e) {
			return errorHandle(e);
		} catch (Exception e) {
			return errorHandle(e);
		}
	}
	
	/********************************************/

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSuspend() {
		return suspend;
	}

	public void setSuspend(String suspend) {
		this.suspend = suspend;
	}

	public CurrentService getCurrentService() {
		return currentService;
	}

	public void setCurrentService(CurrentService currentService) {
		this.currentService = currentService;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}



	
	
}
