package main.CRM.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import main.CRM.bean.NameVarified;
import main.CRM.dao.NameVarifiedDao;
import main.common.service.BaseService;

@Service
public class NameVarifiedService extends BaseService{
	
	@Resource
	private NameVarifiedDao nameVarifiedDao;
	
	public List<NameVarified> queeryNameVarifiedData(String serviceId) throws Exception{
		List<NameVarified> result = nameVarifiedDao.queeryNameVarifiedData(serviceId);
		return result;
	}
	
	public String  insertOrModifiedNameVarifiedData(NameVarified c,String input) throws Exception{
		String result = nameVarifiedDao.insertOrModifiedNameVarifiedData(c);
		return result;
	}
	
	public String  cancelNameVarifiedDataSendDate(String serviceid) throws Exception{
		String result = nameVarifiedDao.cancelNameVarifiedDataSendDate(serviceid);
		return result;
	}
	
	

	public NameVarifiedDao getNameVarifiedDao() {
		return nameVarifiedDao;
	}

	public void setNameVarifiedDao(NameVarifiedDao nameVarifiedDao) {
		this.nameVarifiedDao = nameVarifiedDao;
	}
}
