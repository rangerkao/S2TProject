package main.DVRS.service;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import main.DVRS.bean.Admin;
import main.DVRS.dao.AdminDao;
import main.common.service.BaseService;
@Service
public class AdminService extends BaseService{
	
	public AdminService() throws Exception {
		super();
	}
	
	@Resource
	private AdminDao adminDao;
	
	public List<Admin> queryAdminList() throws Exception{
		List<Admin>  result = adminDao.queryAdminList();
		return result;
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

	public AdminDao getAdminDao() {
		return adminDao;
	}

	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}

	
}
