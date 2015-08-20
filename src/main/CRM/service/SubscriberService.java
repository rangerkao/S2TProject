package main.CRM.service;

import java.sql.SQLException;
import java.util.List;

import main.CRM.bean.Subscriber;
import main.CRM.dao.SubscriberDao;
import main.common.service.BaseService;

public class SubscriberService extends BaseService {
	
	
	public SubscriberService() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	SubscriberDao subscriberDao = new SubscriberDao();
	
	public List<Subscriber> queryListById(String id) throws SQLException{
		List<Subscriber> result = null;
		result = subscriberDao.queryListById(id);
		return result;
	}
	
	public List<Subscriber> queryListByName(String name) throws SQLException{
		List<Subscriber> result = null;
		result = subscriberDao.queryListByName(name);
		return result;
	}
	
	public Subscriber queryDataById(String id) throws SQLException{
		Subscriber result = null;
		result = subscriberDao.queryDataById(id);
		return result;
	}
}
