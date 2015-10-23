package main.CRM.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.CRM.bean.ApplicationData;

public class ApplicationDao extends CRMBaseDao {

	public ApplicationDao() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<ApplicationData> queryByS2tIMSI(String imsi){
		String serviceId = null;
		
		return queryApplication(serviceId);
	}
	
	public List<ApplicationData> queryByS2tMsisdn(String s2tMsisdn) throws SQLException{
		List<ApplicationData> result = new ArrayList<ApplicationData>();
		String serviceId = queryServiceIdbyS2tMsisdn(s2tMsisdn);
		result = queryApplication(serviceId);
		closeConnection();
		return result;
	}
	
	public List<ApplicationData> queryByChtMsisdn(String chtMsisdn) throws SQLException{
		List<ApplicationData> result = new ArrayList<ApplicationData>();
		String serviceId = queryServiceIdbyChtMsisdn(chtMsisdn);
		result = queryApplication(serviceId);
		closeConnection();
		return result;
	}
	
	public List<ApplicationData> queryApplication(String serviceId){
		List<ApplicationData> result = new ArrayList<ApplicationData>();
		
		
		return result;
	}
}
