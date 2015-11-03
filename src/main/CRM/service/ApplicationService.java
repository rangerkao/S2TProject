package main.CRM.service;

import java.sql.SQLException;
import java.util.List;

import main.CRM.bean.ApplicationData;
import main.CRM.dao.ApplicationDao;
import main.common.service.BaseService;

public class ApplicationService  extends BaseService{

	public ApplicationService()throws Exception{
		// TODO Auto-generated constructor stub
	}
	
	ApplicationDao applicationDao = new ApplicationDao();
	
	public List<ApplicationData> queryByServiceId(String serviceId) throws SQLException{
		return applicationDao.queryByServiceId(serviceId);
	}
	
	public boolean insertNew(String type,String serviceid,String verifiedDate) throws SQLException{
		return applicationDao.insertNew(type, serviceid, verifiedDate);
	}

}
