package main.DVRS.service;

import java.sql.SQLException;
import java.util.List;

import main.DVRS.bean.Admin;
import main.DVRS.dao.AdminDao;
import main.common.service.BaseService;

public class AdminService extends BaseService{
	
	public AdminService() throws Exception {
		super();
	}
	
	private AdminDao adminDao=new AdminDao();
	
	public List<Admin> queryAdminList() throws SQLException{
		return adminDao.queryAdminList();
	}
/*	
	public int addAdmin(Admin admin) throws SQLException{
		return adminDao.insert(admin);
	}
	
	public int modAdmin(Admin admin) throws SQLException{
		return adminDao.update(admin);
	}

	public int delAdmin(Admin admin) throws SQLException{
		return adminDao.delete(admin);
	}*/

}
