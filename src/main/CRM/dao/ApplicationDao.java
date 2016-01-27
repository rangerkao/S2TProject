package main.CRM.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import main.CRM.bean.ApplicationData;
@Repository
public class ApplicationDao extends CRMBaseDao {

	public ApplicationDao() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<ApplicationData> queryByS2tIMSI(String imsi) throws Exception{
		String serviceId = null;
		
		return queryApplication(serviceId);
	}
	
	public List<ApplicationData> queryByS2tMsisdn(String s2tMsisdn) throws Exception{
		List<ApplicationData> result = new ArrayList<ApplicationData>();
		String serviceId = queryServiceIdbyS2tMsisdn(s2tMsisdn).getServiceId();
		result = queryApplication(serviceId);
		//closeConnection();
		return result;
	}
	
	public List<ApplicationData> queryByChtMsisdn(String chtMsisdn) throws Exception{
		List<ApplicationData> result = new ArrayList<ApplicationData>();
		String serviceId = queryServiceIdbyChtMsisdn(chtMsisdn).getServiceId();
		result = queryApplication(serviceId);
		//closeConnection();
		return result;
	}
	
	public List<ApplicationData> queryByServiceId(String serviceId) throws Exception{
		List<ApplicationData> result = new ArrayList<ApplicationData>();
		result = queryApplication(serviceId);
		//closeConnection();
		return result;
	}
	public List<ApplicationData> queryApplication(String serviceId) throws Exception{
		List<ApplicationData> result = new ArrayList<ApplicationData>();
		String sql = "SELECT A.TYPE,DATE_FORMAT(A.VERIFIED_DATE,'%Y/%m/%d %H:%m:%s') VERIFIED_DATE "
				+ "FROM CRM_APPLICATION A WHERE A.SERVICEID = '"+serviceId+"' ";
		Statement st = null;
		ResultSet rs = null;
		
		try{
			st = getConn3().createStatement();
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
	
	public boolean insertNew(String type,String serviceid,String verifiedDate) throws Exception{
		boolean result = false;
		String sql = "INSERT INTO CRM_APPLICATION(SERVICEID,VERIFIED_DATE,CREATETIME,TYPE) "
				+ "VALUES("+serviceid+",now(),now(),'"+type+"')";
		Statement st = null;
		
		try{
			st = getConn3().createStatement();
			int r = st.executeUpdate(sql);
			if(r==1){
				result = true;
			}
		}finally{
			
		}
		
		return result;
	}
}
