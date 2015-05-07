package main.common.action;

import main.common.service.LoginService;

public class LoginAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	LoginService loginService = new LoginService();
	
	String account;
	String password;

	
	public void validate() {

	}
	
	
	public String login(){
		System.out.println("account:"+account+",password:"+password);
		if(SUCCESS.equals(loginService.checkAccount(session, account, password))){
			return setResult("Welcome",(String)session.get("user"));
		}else{
			return setFail("The account is not exist or password is Error!", null);
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
	
	
}
