package main.common.action;

import javax.annotation.Resource;

import main.common.service.LogoutService;


public class LogoutAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	LogoutService logoutService;
	
	public String logout(){
		
		logoutService.logout(session);
		
		return SUCCESS;
	}

	public LogoutService getLogoutService() {
		return logoutService;
	}

	public void setLogoutService(LogoutService logoutService) {
		this.logoutService = logoutService;
	}
	
	
}
