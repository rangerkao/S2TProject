package main.common.action;

import java.util.List;

import main.common.bean.Link;
import main.common.service.MenuService;

public class ManuAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	MenuService menuService = new MenuService();
	
	String system;
	
	public String queryMenu(){
	
		List<Link> menu = menuService.queryManu(session, system);
		
		
		return setResult(SUCCESS, menu);
	}
	
}
