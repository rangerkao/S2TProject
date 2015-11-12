package main.common.action;


public class NeedLoginAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NeedLoginAction() {
		// TODO Auto-generated constructor stub
	}
	
	public String NeedLogin(){
		System.out.println("NeedLogin...");
		setSuccess("Please act after login!");
		return SUCCESS;
	}

}
