package main.common.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.common.bean.Link;



public class LoginService extends BaseService{

	String SUCCESS = "success";
	
	public String checkAccount(Map<String, Object> session,String account,String password) throws NoSuchAlgorithmException{

		Boolean result = false;
		String role = null;
		session.put("user", account);
		session.put("role", role);
		
		String md5Password = md5Encode(password);
		String DBPassWord = md5Encode("admin");
		
		System.out.println("user password = "+password);
		System.out.println("compare password : "+md5Password+" == "+DBPassWord);
		
		if("admin".equals(account)&&md5Password.equals(DBPassWord))
			result = true;
		
		
		if(result){
			return SUCCESS;
		}
		
		return null;
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
