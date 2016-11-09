package main.CRM.action;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import main.CRM.bean.QosBean;
import main.CRM.service.QosService;
import main.common.action.BaseAction;


public class QosAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QosAction() throws Exception {
		super();
	}
	
	String imsi;
	String msisdn;
	String activeDate;
	String cancelDate;
	@Resource
	QosService qosService;
	
	public String queryQos(){
		System.out.println("queryQos");
		System.out.println("msisdn="+msisdn+",imsi="+imsi);
		try {
			List<QosBean> qosList = qosService.queryQos(imsi, msisdn,activeDate,cancelDate);
			setSuccess(qosList);
		} catch (SQLException e) {
			errorHandle(e);
		} catch (Exception e) {
			errorHandle(e);
		}
		
		return SUCCESS;
	}
	

	
	//********************************************************//
	
	public String getImsi() {
		return imsi;
	}


	public void setImsi(String imsi) {
		this.imsi = imsi;
	}


	public String getMsisdn() {
		return msisdn;
	}


	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}



	public QosService getQosService() {
		return qosService;
	}



	public void setQosService(QosService qosService) {
		this.qosService = qosService;
	}



	public String getActiveDate() {
		return activeDate;
	}



	public void setActiveDate(String activeDate) {
		this.activeDate = activeDate;
	}



	public String getCancelDate() {
		return cancelDate;
	}



	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}

	
	
}
