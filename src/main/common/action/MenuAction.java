package main.common.action;

import java.util.List;

import main.BaseAction;
import main.common.bean.Link;
import main.common.service.MenuService;

public class MenuAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	MenuService menuService = new MenuService();
	
	String role;
	String system;
	
	public String queryMenu(){
		List<Link> menu = menuService.queryManu(role,system);
		return setResult(SUCCESS, menu);
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
	

	
}
