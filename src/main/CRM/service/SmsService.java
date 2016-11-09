package main.CRM.service;
 
import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import main.CRM.bean.SMS;
import main.CRM.dao.SmsDao;

@Service
public class SmsService {

	public SmsService() throws Exception{
		// TODO Auto-generated constructor stub
	}
	@Resource
	SmsDao smsDao;
	
	public List<SMS> querySMS(String s2tMsisdn ,String chtMsisdn,String startDate,String endDate,String activatedDate,String canceledDate) throws Exception, ParseException {
		
		List<SMS> result = null;
		result = smsDao.querySMS(s2tMsisdn, chtMsisdn, startDate, endDate,activatedDate,canceledDate);
		return result;
	}

	public SmsDao getSmsDao() {
		return smsDao;
	}

	public void setSmsDao(SmsDao smsDao) {
		this.smsDao = smsDao;
	}
	
	
}
