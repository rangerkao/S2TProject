package main.CRM.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import main.CRM.bean.NameVarified;
import main.CRM.dao.NameVarifiedDao;
import main.common.service.BaseService;

@Service
public class NameVarifiedService extends BaseService{
	
	@Resource
	private NameVarifiedDao nameVarifiedDao;
	
	public NameVarified queeryNameVarifiedData(String serviceId) throws Exception{
		NameVarified result = nameVarifiedDao.queeryNameVarifiedData(serviceId);
		return result;
	}
	
	public String  insertOrModifiedNameVarifiedData(NameVarified c) throws Exception{
		String result = nameVarifiedDao.insertOrModifiedNameVarifiedData(c);
		return result;
	}

	public NameVarifiedDao getNameVarifiedDao() {
		return nameVarifiedDao;
	}

	public void setNameVarifiedDao(NameVarifiedDao nameVarifiedDao) {
		this.nameVarifiedDao = nameVarifiedDao;
	}

}
