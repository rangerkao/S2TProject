package main.CRM.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import main.CRM.bean.NameVarified;
import main.CRM.bean.NameVarifiedSet;
import main.CRM.dao.NameVarifiedDao;
import main.CRM.dao.SubscriberDao;
import main.common.service.BaseService;

@Service
public class NameVarifiedService extends BaseService{
	
	@Resource
	private NameVarifiedDao nameVarifiedDao;
	
	
	public List<NameVarified>  queeryNameVarifiedData(NameVarified c,String input) throws Exception{
		//Query China VLN Data
		List<NameVarified>  result = nameVarifiedDao.queeryNameVarifiedData(c.getVln());
		return result;
	}
	public List<NameVarified>  queeryNameVarifiedDataSameMsisdn(String input) throws Exception{
		//Query China VLN Data
		List<NameVarified>  result = nameVarifiedDao.queeryNameVarifiedDataSameMsisdn(input);
		return result;
	}
	public List<NameVarified> queeryNameVarifiedData(String input,String type) throws Exception{
		List<NameVarified> result = nameVarifiedDao.queeryNameVarifiedData(input,type);
		return result;
	}
	
	public String  updateNameVarifiedData(NameVarified c,String input) throws Exception{
		String result = nameVarifiedDao.updateNameVarifiedData(c);
		return result;
	}
	
	public String  addNameVarifiedData(NameVarified c,String input) throws Exception{
		String result = nameVarifiedDao.addNameVarifiedData(c);
		return result;
	}
	public List<String> queryVLN(String serviceId) throws Exception{
		List<String> result = nameVarifiedDao.queryVLN(serviceId);
		return result;
	}
	

	public NameVarifiedDao getNameVarifiedDao() {
		return nameVarifiedDao;
	}

	public void setNameVarifiedDao(NameVarifiedDao nameVarifiedDao) {
		this.nameVarifiedDao = nameVarifiedDao;
	}
}
