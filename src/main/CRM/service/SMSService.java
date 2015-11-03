package main.CRM.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import main.CRM.bean.SMS;
import main.CRM.dao.SMSDao;

public class SMSService {

	public SMSService() throws Exception{
		// TODO Auto-generated constructor stub
	}

	SMSDao sMSDao = new SMSDao();
	
	public List<SMS> querySMS(String s2tMsisdn ,String chtMsisdn,String startDate,String endDate) throws SQLException, ParseException {
		
		List<SMS> result = null;
		result = sMSDao.querySMS(s2tMsisdn, chtMsisdn, startDate, endDate);
		return result;
	}
}
