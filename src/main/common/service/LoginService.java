package main.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.common.bean.Link;



public class LoginService extends BaseService{

	String SUCCESS = "success";
	
	public String checkAccount(Map<String, Object> session,String account,String password){

		Boolean result = true;
		String role = null;
		session.put("user", account);
		session.put("role", role);
		
		if(result){
			return SUCCESS;
		}
		
		return null;
	}

}
