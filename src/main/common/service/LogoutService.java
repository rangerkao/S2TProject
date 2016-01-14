package main.common.service;

import java.util.Map;

import org.springframework.stereotype.Service;


@Service
public class LogoutService {

	
	public boolean logout(Map<String, Object> session){
		
		session.remove("s2t");
		
		
		return true;
	}
}
