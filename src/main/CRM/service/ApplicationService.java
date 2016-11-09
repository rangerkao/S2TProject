package main.CRM.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import main.CRM.bean.ApplicationData;
import main.CRM.dao.ApplicationDao;
import main.common.service.BaseService;

@Service
public class ApplicationService  extends BaseService{

	public ApplicationService()throws Exception{
		// TODO Auto-generated constructor stub
	}
	@Resource
	ApplicationDao applicationDao;
	
	public List<ApplicationData> queryByServiceId(String serviceId) throws Exception{
		List<ApplicationData> result = applicationDao.queryByServiceId(serviceId);
		return result;
	}
	public List<ApplicationData> queryByDate(String date) throws Exception{
		List<ApplicationData> result = applicationDao.queryByDate(date);
		return result;
	}
	
	public boolean insertNew(String type,String serviceid,String verifiedDate) throws Exception{
		boolean result =  applicationDao.insertNew(type, serviceid, verifiedDate);
		return result;
	}

	public ApplicationDao getApplicationDao() {
		return applicationDao;
	}

	public void setApplicationDao(ApplicationDao applicationDao) {
		this.applicationDao = applicationDao;
	}

	
	
}
