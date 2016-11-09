package main.CRM.dao;

import java.sql.Connection;
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
		String serviceId = queryServiceIdbyS2tMsisdn(s2tMsisdn);
		result = queryApplication(serviceId);
		return result;
	}
	
	public List<ApplicationData> queryByChtMsisdn(String chtMsisdn) throws Exception{
		List<ApplicationData> result = new ArrayList<ApplicationData>();
		String serviceId = queryServiceIdbyChtMsisdn(chtMsisdn);
		result = queryApplication(serviceId);
		return result;
	}
	
	public List<ApplicationData> queryByServiceId(String serviceId) throws Exception{
		List<ApplicationData> result = new ArrayList<ApplicationData>();
		result = queryApplication(serviceId);
		return result;
	}
	public List<ApplicationData> queryApplication(String serviceId) throws Exception{
		List<ApplicationData> result = new ArrayList<ApplicationData>();
		String sql = "SELECT A.SERVICEID,A.TYPE,DATE_FORMAT(A.VERIFIED_DATE,'%Y/%m/%d %H:%m:%s') VERIFIED_DATE "
				+ "FROM CRM_APPLICATION A WHERE A.SERVICEID = '"+serviceId+"' ";
		Statement st = null;
		ResultSet rs = null;
		Connection conn = getConn3();
		try{
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			//System.out.println("sql:"+sql);
			while(rs.next()){
				ApplicationData a = new ApplicationData();
				a.setServiceid(rs.getString("SERVICEID"));
				a.setApplicationDate(rs.getString("VERIFIED_DATE"));
				a.setType(rs.getString("TYPE"));
				result.add(a);
			}
		}finally{
			if(st!=null) st.close();
			if(rs!=null) rs.close();
			closeConn3(conn);
		}
		
		return result;
	}
	
	public List<ApplicationData> queryByDate(String date) throws Exception{
		List<ApplicationData> result = new ArrayList<ApplicationData>();
		String sql = "SELECT A.SERVICEID,A.TYPE,DATE_FORMAT(A.VERIFIED_DATE,'%Y/%m/%d %H:%m:%s') VERIFIED_DATE "
				+ "FROM CRM_APPLICATION A WHERE DATE_FORMAT(A.VERIFIED_DATE,'%Y%m%d') = '"+date+"' ";
		Statement st = null;
		ResultSet rs = null;
		Connection conn = getConn3();
		try{
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			//System.out.println("sql:"+sql);
			while(rs.next()){
				ApplicationData a = new ApplicationData();
				a.setServiceid(rs.getString("SERVICEID"));
				a.setApplicationDate(rs.getString("VERIFIED_DATE"));
				a.setType(rs.getString("TYPE"));
				result.add(a);
			}
		}finally{
			if(st!=null) st.close();
			if(rs!=null) rs.close();
			closeConn3(conn);
		}
		
		return result;
	}
	
	public boolean insertNew(String type,String serviceid,String verifiedDate) throws Exception{
		boolean result = false;
		String sql = "INSERT INTO CRM_APPLICATION(SERVICEID,VERIFIED_DATE,CREATETIME,TYPE) "
				+ "VALUES("+serviceid+",now(),now(),'"+type+"')";
		Statement st = null;
		Connection conn = getConn3();
		try{
			st = conn.createStatement();
			int r = st.executeUpdate(sql);
			if(r==1){
				result = true;
			}
		}finally{
			if(st!=null) st.close();
			closeConn3(conn);
		}
		
		return result;
	}
}
