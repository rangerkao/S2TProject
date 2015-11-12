package main.DVRS.action;

import java.sql.SQLException;
import java.util.List;

import main.DVRS.bean.Admin;
import main.DVRS.service.AdminService;
import main.common.action.BaseAction;

public class AdminAction extends BaseAction{

	public AdminAction() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 /**
	  * ajax返回结果，也可是其他类型的，这里以String类型为例
	  */
	 
	 private AdminService adminService=new AdminService();
	 private Admin admin;
	 private String mod;
	
	public String queryAdmin() throws SQLException{
		List<Admin> adminList=adminService.queryAdminList();
		return setSuccess(adminList);
	}
	/*
	public String updateAdmin() {
		result=SUCCESS;
		System.out.println( beanToJSONObject(admin));
		System.out.println(	"mod:"+mod);
		int r=0;
		
		try {
			if(mod.equalsIgnoreCase("add")){
				if(adminControl.addAdmin(admin)!=1)
					result="Error To add new data!";
			}else if(mod.equalsIgnoreCase("mod")){
				if(adminControl.modAdmin(admin)!=1)
					result="Error To modify data!";
			}else if(mod.equalsIgnoreCase("del")){
				if(adminControl.delAdmin(admin)!=1)
					result="Error To delete data!";
			}
			actionLogControl.loggerAction(super.getUser().getAccount(), "Admin", "update", mod+":"+ beanToJSONObject(admin), result);
		} catch (Exception e) {
			e.printStackTrace();
			StringWriter s = new StringWriter();
			e.printStackTrace(new PrintWriter(s));
			result = makeResult(null, s.toString());
		}

		return SUCCESS;

	}*/

	//--------------------------//
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public String getMod() {
		return mod;
	}

	public void setMod(String mod) {
		this.mod = mod;
	} 

}
