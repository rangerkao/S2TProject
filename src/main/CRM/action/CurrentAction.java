package main.CRM.action;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import main.CRM.bean.CurrentMonth;
import main.CRM.service.CurrentService;
import main.DVRS.bean.CurrentDay;
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

	String imsi;
	String from;
	String to;
	String suspend;
	
	@Resource
	CurrentService currentService;
	
	
	public String queryCurrentMonth(){
		
		try {
			System.out.println("imsi:"+imsi+",from:"+from+",to:"+to+",suspend:"+suspend+","+new Date());
			List<CurrentMonth> list = currentService.queryCurrentMonth(imsi,from.replace("-",""),to.replace("-",""),suspend);
			return setSuccess(list);
		} catch (SQLException e) {
			return errorHandle(e);
		} catch (Exception e) {
			return errorHandle(e);
		}
	}
	
	public String queryCurrentDay(){
		
		try {
			System.out.println("imsi:"+imsi+",from:"+from+",to:"+to);
			List<CurrentDay> list = currentService.queryCurrentDay(imsi,from.replace("-",""),to.replace("-",""));
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



	
	
}
