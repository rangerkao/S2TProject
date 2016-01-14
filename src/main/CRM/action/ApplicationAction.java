package main.CRM.action;

import java.util.List;

import javax.annotation.Resource;

import main.CRM.bean.ApplicationData;
import main.CRM.service.ApplicationService;
import main.common.action.BaseAction;

public class ApplicationAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ApplicationAction() throws Exception{
		// TODO Auto-generated constructor stub
	}
	
	String serviceid;
	String type;
	String verifiedDate;
	
	
	@Resource
	ApplicationService applicationService;
	
	public String queryAppByServiceId(){
		System.out.println("queryByServiceId");
		System.out.println("serviceid="+serviceid);
		try {
			List<ApplicationData> aList = applicationService.queryByServiceId(serviceid);
			setSuccess(aList);
		} catch (Exception e) {
			errorHandle(e);
		}
		
		return SUCCESS;
	}
	
	public String insertNew(){
		System.out.println("insertNew");
		System.out.println("type:"+type+", serviceid:"+serviceid+", verifiedDate:"+verifiedDate);
		try {
			if(applicationService.insertNew(type, serviceid, verifiedDate)){
				setSuccess(SUCCESS);
			}else{
				setFail(ERROR);
			}
			
		} catch (Exception e) {
			errorHandle(e);
		}
		
		return SUCCESS;
	}

	public String getServiceid() {
		return serviceid;
	}

	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVerifiedDate() {
		return verifiedDate;
	}

	public void setVerifiedDate(String verifiedDate) {
		this.verifiedDate = verifiedDate;
	}

	public ApplicationService getApplicationService() {
		return applicationService;
	}

	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	
	
}
