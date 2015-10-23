package main.CRM.dao;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.common.dao.BaseDao;

public class CRMBaseDao extends BaseDao{

	public CRMBaseDao() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String queryServiceIdbyS2tMsisdn(String s2tMsisdn) throws SQLException{
		
		Statement st = null;
		Statement st2 = null;
		ResultSet rs = null ;
		String sql = null;
		String serviceId = null;

		try {
			st = conn.createStatement();
			st2 = conn2.createStatement();
			
			sql = "SELECT A.SERVICEID FROM SERVICE A WHERE A.SERVICECODE = '"+s2tMsisdn+"'";
			System.out.println("sql:"+sql);
			rs = st2.executeQuery(sql);
			
			while(rs.next()){
				serviceId = rs.getString("SERVICEID");
			}

		}finally{
			try {
				if(rs!=null)
					rs.close();
				if(st!=null)
					st.close();
				if(st2!=null)
					st2.close();
			} catch (Exception e) {
			}
		}
		
		return serviceId;
	}
	
	public String queryServiceIdbyS2tImsi(String s2tImsi) throws SQLException{
		
		Statement st = null;
		Statement st2 = null;
		ResultSet rs = null ;
		String sql = null;
		String serviceId = null;

		try {
			st = conn.createStatement();
			st2 = conn2.createStatement();
			
			sql = "SELECT A.SERVICEID FROM IMSI A where A.IMSI ='"+s2tImsi+"'";
			System.out.println("sql:"+sql);
			rs = st2.executeQuery(sql);
			
			while(rs.next()){
				serviceId = rs.getString("SERVICEID");
			}

		}finally{
			try {
				if(rs!=null)
					rs.close();
				if(st!=null)
					st.close();
				if(st2!=null)
					st2.close();
			} catch (Exception e) {
			}
		}
		
		return serviceId;
	}
	
	public String queryServiceIdbyChtMsisdn(String chtMsisdn) throws SQLException{
		
		Statement st = null;
		Statement st2 = null;
		ResultSet rs = null ;
		String sql = null;
		String serviceId = null;

		try {
			st = conn.createStatement();
			st2 = conn2.createStatement();
			
			sql = "SELECT A.SERVICEID FROM FOLLOWMEDATA A where A.FOLLOWMENUMBER = '"+chtMsisdn+"' ";
			System.out.println("sql:"+sql);
			rs = st2.executeQuery(sql);
			
			while(rs.next()){
				serviceId = rs.getString("SERVICEID");
			}

		}finally{
			try {
				if(rs!=null)
					rs.close();
				if(st!=null)
					st.close();
				if(st2!=null)
					st2.close();
			} catch (Exception e) {
			}
		}
		
		return serviceId;
	}
	
	public String queryServiceIdbyChtImsi(String chtImsi) throws SQLException{
		
		Statement st = null;
		Statement st2 = null;
		ResultSet rs = null ;
		String sql = null;
		String serviceId = null;

		try {
			st = conn.createStatement();
			st2 = conn2.createStatement();
			
			sql = "SELECT A.SERVICEID FROM IMSI A where A.HOMEIMSI='"+chtImsi+"'";
			System.out.println("sql:"+sql);
			rs = st2.executeQuery(sql);

			while(rs.next()){
				serviceId = rs.getString("SERVICEID");
			}

		}finally{
			try {
				if(rs!=null)
					rs.close();
				if(st!=null)
					st.close();
				if(st2!=null)
					st2.close();
			} catch (Exception e) {
			}
		}
		
		return serviceId;
	}
	
	public String processData(String data){
		return (data==null?" ":data);
	}
	public String processEncodeData(String data,String sCharSet,String dCharSet){
		if(data==null)
			data=" ";
		
		try {
			data = new String(data.getBytes(sCharSet),dCharSet);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return data;
	}

}
