package main.common.action;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;

import main.BaseAction;
import main.common.service.LoginService;

public class LoginAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	LoginService loginService = new LoginService();

	
	String account;
	String password;
	String msg;
	
	public void validate() {

	}
	
	
	public String login(){
	
		System.out.println("account:"+account+",password:"+password);
		result="進行登陸";
		
		try {
			if(SUCCESS.equals(loginService.checkAccount(session, account, password))){
				msg = "Welcome";
				return setResult("Welcome",(String)session.get("user"));
			}else{
				msg = "The account is not exist or password is Error!";
				return setFail("The account is not exist or password is Error!", null);
			}
		} catch (NoSuchAlgorithmException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			return setFail("login occured error!", sw.toString());
		}
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
	
}
