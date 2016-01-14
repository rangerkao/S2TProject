package main.common.action;

import java.util.List;

import javax.annotation.Resource;

import main.common.bean.Link;
import main.common.service.SystemMenuService;

public class SystemMenuAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	SystemMenuService systemMenuService;
	public String querySystemMenu(){
		
		List<Link> menuList = systemMenuService.querySystemMenu();
		return setSuccess(menuList);
	}
	public String goSystemMenu(){
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 */
	public String DVRS(){return "DVRS";}
	public String CRM(){return "CRM";}
	public SystemMenuService getSystemMenuService() {
		return systemMenuService;
	}
	public void setSystemMenuService(SystemMenuService systemMenuService) {
		this.systemMenuService = systemMenuService;
	}
	
	
}
