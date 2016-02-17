package main.CRM.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import main.CRM.bean.AddonService;
import main.CRM.bean.Subscriber;
import main.CRM.dao.SubscriberDao;
import main.common.service.BaseService;
@Service
public class SubscriberService extends BaseService{
	
	
	public SubscriberService() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Resource
	SubscriberDao subscriberDao;
	
	
	public List<Subscriber> queryListById(String id) throws Exception{
		List<Subscriber> result = null;
		result = subscriberDao.queryListById(id);
		return result;
	}
	
	public List<Subscriber> queryListByName(String name) throws Exception{
		List<Subscriber> result = null;
		result = subscriberDao.queryListByName(name);
		return result;
	}
	
	public List<Subscriber> queryListByVLN(String VLN) throws Exception{
		List<Subscriber> result = null;
		result = subscriberDao.queryListByVLN(VLN);
		return result;
	}
	
	public List<Subscriber> queryListByS2tMisidn(String s2tMsisdn) throws Exception{
		List<Subscriber> result = null;
		result = subscriberDao.queryListByS2tMisidn(s2tMsisdn);
		return result;
	}
	
	public List<Subscriber> queryListByChtMsisdn(String chtMsisdn) throws Exception{
		List<Subscriber> result = null;
		result = subscriberDao.queryListByChtMsisdn(chtMsisdn);
		return result;
	}
	
	public List<Subscriber> queryListByMainMsisdn(String chtMsisdn) throws Exception{
		List<Subscriber> result = null;
		result = subscriberDao.queryListByChtMsisdn(chtMsisdn);
		return result;
	}
	
	public Subscriber queryDataById(String id) throws Exception{
		Subscriber result = null;
		result = subscriberDao.queryDataById(id);
		return result;
	}
	
	public List<String> queryServiceIdList(String id) throws Exception{
		return subscriberDao.queryServiceIdList(id);
	}
	
	public Subscriber queryDataByServiceId(String id) throws Exception{
		return subscriberDao.queryDataByServiceId(id);
	}
	
	public boolean updateSubscriber(Subscriber s,String arg) throws Exception{
		return subscriberDao.updateSubscriber(s);
	}
	
	public List<String> queryVLN(String serviceId) throws Exception{
		return subscriberDao.queryVLN(serviceId);
	}
	
	public List<AddonService> queryAddonService(String serviceId) throws Exception{
		return subscriberDao.queryAddonService(serviceId);
	}
	
	public String getGPRSStatus(String msisdn) throws Exception{
		return subscriberDao.getGPRSStatus(msisdn);
	}

	
	
	protected SubscriberDao getSubscriberDao() {
		return subscriberDao;
	}
	protected void setSubscriberDao(SubscriberDao subscriberDao) {
		this.subscriberDao = subscriberDao;
	}	
	
}
