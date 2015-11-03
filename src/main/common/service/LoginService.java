package main.common.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.common.bean.Link;
import main.common.bean.User;
import main.common.dao.LoginDao;



public class LoginService extends BaseService{

	
	
	public LoginService() throws Exception{
		super();
		// TODO Auto-generated constructor stub
	}

	String SUCCESS = "success";
	
	LoginDao loginDao = new LoginDao();
	
	public void checkAccount(Map<String, Object> session,String account,String password) throws Exception{

		Boolean result = false;
		
		User user = loginDao.queryUser(account);
		
		if(user==null)
			throw new Exception("Without this account!");
		
		String md5Password = md5Encode(password);
		String DBPassWord = user.getPassword();
		if(md5Password.equals(DBPassWord)){
			session.put("s2t.account", user.getAccount());
			//session.put("s2t.password", user.getPassword());
			session.put("s2t.role", user.getRole());
			
			System.out.println("user password = "+password);
			System.out.println("compare password : "+md5Password+" == "+DBPassWord);
		}else{
			throw new Exception("Password Error!");
		}
	}
	//md5 EncodeTest
	public String md5Encode(String source) throws NoSuchAlgorithmException{
		String input=source;
		 MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes());
        return byteToString1(messageDigest);
	}
	
	public String byteToString1(byte[] source){
		BigInteger number = new BigInteger(1, source);
        String hashtext = number.toString(16);
        // Now we need to zero pad it if you actually want the full 32 chars.
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
	}

}
