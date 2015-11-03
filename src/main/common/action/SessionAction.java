package main.common.action;

import main.BaseAction;

public class SessionAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SessionAction() throws Exception{
		// TODO Auto-generated constructor stub
	}

	public String querySession(){
		
		System.out.println(session.get("s2t.role"));
		return setResult(SUCCESS, session);
	}
}
