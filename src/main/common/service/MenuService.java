package main.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.common.bean.Link;

public class MenuService extends BaseService {

	public List<Link> queryManu(Map<String, Object> session,String system){
		
		String role = (String) session.get("role");
		
		List<Link> result =new ArrayList<Link>();

		result.add(new Link("adminList","adminLink","使用者管理"));
		result.add(new Link("elseList","logoutLink","登出"));

		return result;
	}
}
