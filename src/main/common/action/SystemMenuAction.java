package main.common.action;

import java.util.List;

import main.BaseAction;
import main.common.bean.Link;
import main.common.service.SystemMenuService;

public class SystemMenuAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	SystemMenuService systemMenuService = new SystemMenuService();
	public String querySystemMenu(){
		
		List<Link> menuList = systemMenuService.querySystemMenu();
		return setResult("success",menuList);
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
	
	
}
