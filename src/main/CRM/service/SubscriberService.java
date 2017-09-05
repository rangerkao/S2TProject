package main.CRM.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import main.CRM.bean.AddonService;
import main.CRM.bean.Subscriber;
import main.CRM.bean.USPacket;
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
		List<Subscriber> result = subscriberDao.queryListById(id);
		return result;
	}
	
	public List<Subscriber> queryListByPassPortId(String id) throws Exception{
		List<Subscriber> result = subscriberDao.queryListByPassPortId(id);
		return result;
	}
	
	public List<Subscriber> queryListByName(String name) throws Exception{
		List<Subscriber> result =  subscriberDao.queryListByName(name);
		return result;
	}
	
	public List<Subscriber> queryListByVLN(String VLN) throws Exception{
		List<Subscriber> result = subscriberDao.queryListByVLN(VLN);
		return result;
	}
	
	public List<Subscriber> queryListByS2tMisidn(String s2tMsisdn) throws Exception{
		List<Subscriber> result = subscriberDao.queryListByS2tMisidn(s2tMsisdn);
		return result;
	}
	
	public List<Subscriber> queryListByS2tIMSI(String s2tIMSI) throws Exception{
		List<Subscriber> result = subscriberDao.queryListByS2tIMSI(s2tIMSI);
		return result;
	}
	
	public List<Subscriber> queryListByChtMsisdn(String chtMsisdn) throws Exception{
		List<Subscriber> result = subscriberDao.queryListByChtMsisdn(chtMsisdn);
		return result;
	}
	
	public List<Subscriber> queryListByMainMsisdn(String chtMsisdn) throws Exception{
		List<Subscriber> result =  subscriberDao.queryListByChtMsisdn(chtMsisdn);
		return result;
	}
	
	public Subscriber queryDataById(String id) throws Exception{
		Subscriber result = subscriberDao.queryDataById(id);
		return result;
	}
	
	/*public List<String> queryServiceIdList(String id) throws Exception{
		return subscriberDao.queryServiceIdList(id);
	}*/
	
	public Subscriber queryDataByServiceId(String id) throws Exception{
		Subscriber result = subscriberDao.queryDataByServiceId(id);
		return result;
	}
	
	public boolean updateSubscriber(Subscriber s,String arg) throws Exception{
		boolean result = subscriberDao.updateSubscriber(s);
		return result;
	}
	
	
	
	
	protected SubscriberDao getSubscriberDao() {
		return subscriberDao;
	}
	protected void setSubscriberDao(SubscriberDao subscriberDao) {
		this.subscriberDao = subscriberDao;
	}	
	
	
	
}
