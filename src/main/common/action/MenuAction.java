package main.common.action;

import java.util.List;

import javax.annotation.Resource;

import main.common.bean.Link;
import main.common.service.MenuService;

public class MenuAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	MenuService menuService;
	
	String role;
	String system;
	
	public String queryMenu(){
		List<Link> menu = menuService.queryManu(role,system);
		return setSuccess(menu);
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	

	
	
}
