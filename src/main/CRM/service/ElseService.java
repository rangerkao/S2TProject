package main.CRM.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import main.CRM.bean.AddonService;
import main.CRM.bean.USPacket;
import main.CRM.dao.ElseDao;
import main.CRM.dao.SubscriberDao;
import main.common.service.BaseService;

@Service
public class ElseService extends BaseService{
	
	@Resource
	ElseDao elseDao;
	
	public List<String> queryVLN(String serviceId) throws Exception{
		List<String> result =  elseDao.queryVLN(serviceId);
		result.addAll(elseDao.queryWhetherAppliedCHNA(serviceId));
		return result;
	}
	
	public List<AddonService> queryAddonService(String serviceId) throws Exception{
		List<AddonService> result = elseDao.queryAddonService(serviceId);
		return result;
	}
	
	public String getGPRSStatus(String serviceid) throws Exception{
		String result = elseDao.getGPRSStatus(serviceid);
		return result;
	}
	
	public List<USPacket> queryUSPacket(String serviceId) throws Exception{
		List<USPacket> result = elseDao.queryUSPacket(serviceId);
		return result;
	}

	public ElseDao getElseDao() {
		return elseDao;
	}

	public void setElseDao(ElseDao elseDao) {
		this.elseDao = elseDao;
	}
	
	

}
