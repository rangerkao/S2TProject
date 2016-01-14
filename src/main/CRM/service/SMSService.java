package main.CRM.service;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import main.CRM.bean.SMS;
import main.CRM.dao.SMSDao;

@Service
public class SMSService {

	public SMSService() throws Exception{
		// TODO Auto-generated constructor stub
	}
	@Resource
	SMSDao sMSDao;
	
	public List<SMS> querySMS(String s2tMsisdn ,String chtMsisdn,String startDate,String endDate) throws Exception, ParseException {
		
		List<SMS> result = null;
		result = sMSDao.querySMS(s2tMsisdn, chtMsisdn, startDate, endDate);
		return result;
	}

	public SMSDao getsMSDao() {
		return sMSDao;
	}

	public void setsMSDao(SMSDao sMSDao) {
		this.sMSDao = sMSDao;
	}
	
}
