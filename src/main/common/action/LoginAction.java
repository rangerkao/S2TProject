package main.common.action;

import main.BaseAction;
import main.common.service.LoginService;

public class LoginAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	LoginService loginService = new LoginService();
	
	class User{
		String account;
		String password;
	};
	
	String account;
	String password;
	User user=null;

	
	public void validate() {

	}
	
	
	public String login(){
		if(user==null){
			result="沒有資料";
			System.out.println("沒有資料");
			System.out.println("account:"+account+",password:"+password);
			return SUCCESS;
		}
			
		System.out.println("account:"+user.account+",password:"+user.password);
		result="進行登陸";
		if(SUCCESS.equals(loginService.checkAccount(session, user.account, user.password))){
			return setResult("Welcome",(String)session.get("user"));
		}else{
			return setFail("The account is not exist or password is Error!", null);
		}
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
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
