package main.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import main.common.bean.Link;

@Service
public class SystemMenuService {

	public List<Link> querySystemMenu(){
		List<Link> result = new ArrayList<Link>();
		
		result.add(new Link(null,"DVRSSystem","DVRS"));
		result.add(new Link(null,"CRMSystem","CRM"));
		
		return result;
	}
}
