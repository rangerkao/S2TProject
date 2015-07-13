package main.common.service;

import java.util.ArrayList;
import java.util.List;

import main.common.bean.Link;

public class SystemMenuService {

	public List<Link> querySystemMenu(){
		List<Link> result = new ArrayList<Link>();
		
		result.add(new Link(null,"DVRSSystem","DVRS"));
		result.add(new Link(null,"CRMSystem","CRM"));
		
		return result;
	}
}
