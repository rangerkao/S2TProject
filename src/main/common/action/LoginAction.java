package main.common.action;

import java.security.NoSuchAlgorithmException;

import main.common.service.LoginService;

public class LoginAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	
	public LoginAction() throws Exception{
		super();
	}


	LoginService loginService = new LoginService();

	
	String account;
	String password;
	String msg;
	
	public void validate() {
		/*if (account == null || "".equals(account))
			addFieldError("acc", "帳號為必填，請輸入帳號");
		if (password == null || "".equals(password))
			addFieldError("psw", "密碼為必填，請輸入密碼");*/
	}

	public String login(){
	
		System.out.println("account:"+account+",password:"+password);
		result="進行登陸";
		
		try {
			if(account==null||password==null)
				throw new Exception("Plaese Login!");
			loginService.checkAccount(session, account, password);
				msg = "Welcome";
				return setSuccess((String)session.get("user"));
		} catch (NoSuchAlgorithmException e) {
			errorHandle(e);
			msg = e.getMessage();
		} catch (Exception e) {
			errorHandle(e);
			msg = e.getMessage();
		}
		
		return ERROR;
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
