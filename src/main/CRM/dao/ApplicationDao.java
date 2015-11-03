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

	public List<ApplicationData> queryByS2tIMSI(String imsi) throws SQLException{
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
	
	public List<ApplicationData> queryByServiceId(String serviceId) throws SQLException{
		List<ApplicationData> result = new ArrayList<ApplicationData>();
		result = queryApplication(serviceId);
		closeConnection();
		return result;
	}
	public List<ApplicationData> queryApplication(String serviceId) throws SQLException{
		List<ApplicationData> result = new ArrayList<ApplicationData>();
		String sql = "SELECT A.TYPE,to_char(A.VERIFIED_DATE,'yyyy/MM/dd hh24:mi:ss') VERIFIED_DATE "
				+ "FROM CRM_APPLICATION A WHERE A.SERVICEID = '"+serviceId+"' ";
		Statement st = null;
		ResultSet rs = null;
		
		try{
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			System.out.println("sql:"+sql);
			while(rs.next()){
				ApplicationData a = new ApplicationData();
				a.setApplicationDate(rs.getString("VERIFIED_DATE"));
				a.setType(rs.getString("TYPE"));
				result.add(a);
			}
		}finally{
			
		}
		
		return result;
	}
	
	public boolean insertNew(String type,String serviceid,String verifiedDate) throws SQLException{
		boolean result = false;
		String sql = "INSERT INTO CRM_APPLICATION(SERVICEID,VERIFIED_DATE,CREATETIME,TYPE) "
				+ "VALUES("+serviceid+",sysdate,sysdate,"+type+")";
		Statement st = null;
		
		try{
			st = conn.createStatement();
			int r = st.executeUpdate(sql);
			if(r==1){
				result = true;
			}
		}finally{
			
		}
		
		return result;
	}
}
