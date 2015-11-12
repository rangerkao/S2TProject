package main.DVRS.action;

import java.sql.SQLException;

import main.common.action.BaseAction;

public class SmsAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SmsAction() {
		// TODO Auto-generated constructor stub
	}
	
	public String querySms(){
		try {
			throw new Exception("Action Test!");
		} catch (SQLException e) {
			return errorHandle(e);
		} catch (Exception e) {
			return errorHandle(e);
		}
	}

}
