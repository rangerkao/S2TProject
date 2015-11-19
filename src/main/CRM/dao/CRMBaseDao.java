package main.CRM.dao;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.CRM.bean.Subscriber;
import main.common.dao.BaseDao;

public class CRMBaseDao extends BaseDao{

	public CRMBaseDao() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Subscriber queryServiceIdbyS2tMsisdn(String s2tMsisdn) throws SQLException{
		
		Statement st2 = null;
		ResultSet rs = null ;
		String sql = null;
		Subscriber s = new Subscriber();
		try {
			st2 = conn2.createStatement();
			/*
			sql = "SELECT A.SERVICEID,A.SERVICECODE S2TMSISDN,B.FOLLOWMENUMBER CHTMSISDN "
				+ "FROM SERVICE A,FOLLOWMEDATA B "
				+ "WHERE  A.SERVICEID= B.SERVICEID AND A.SERVICECODE = '"+s2tMsisdn+"'";

			System.out.println("sql:"+sql);
			rs = st2.executeQuery(sql);
			
			while(rs.next()){
				s.setServiceId(rs.getString("SERVICEID"));
				s.setS2tMsisdn(rs.getString("S2TMSISDN"));
				s.setChtMsisdn(rs.getString("CHTMSISDN"));
			}*/
			
			sql = "SELECT A.PRICEPLANID,B.IMSI,A.SERVICECODE,A.PRICEPLANID,A.SUBSIDIARYID,A.SERVICEID,"
					+ "(CASE A. STATUS 	WHEN '1' then to_char(C.value) "
					+ "					when '3' then to_char( C.value) "
					+ "					when '10' then to_char(C.value) else null end) NCODE "
					+ "FROM SERVICE A,IMSI B,PARAMETERVALUE C "
					+ "WHERE A.SERVICEID=B.SERVICEID AND A.SERVICECODE IS NOT NULL "
					+ "AND B.SERVICEID=C.SERVICEID(+) AND C.PARAMETERVALUEID(+)=3748 "
					+ "AND A.SERVICECODE = '"+s2tMsisdn+"'";
			System.out.println("sql:"+sql);
			rs = st2.executeQuery(sql);
			
			while(rs.next()){
				s.setS2tIMSI(rs.getString("IMSI"));
				s.setServiceId(rs.getString("SERVICEID"));
				s.setS2tMsisdn(rs.getString("SERVICECODE"));
				s.setChtMsisdn(rs.getString("NCODE"));
				s.setPrivePlanId(rs.getString("PRICEPLANID"));
			}

		}finally{
			try {
				if(rs!=null)
					rs.close();
				if(st2!=null)
					st2.close();
			} catch (Exception e) {
			}
		}
		
		return s;
	}
	
	public Subscriber queryServiceIdbyS2tImsi(String s2tImsi) throws SQLException{
		
		Statement st = null;
		Statement st2 = null;
		ResultSet rs = null ;
		String sql = null;
		String serviceId = null;
		
		Subscriber s = new Subscriber();

		try {
			st2 = conn2.createStatement();
			
			sql = "SELECT A.PRICEPLANID,B.IMSI,A.SERVICECODE,A.PRICEPLANID,A.SUBSIDIARYID,A.SERVICEID,"
					+ "(CASE A. STATUS 	WHEN '1' then to_char(C.value) "
					+ "					when '3' then to_char( C.value) "
					+ "					when '10' then to_char(C.value) else null end) NCODE "
					+ "FROM SERVICE A,IMSI B,PARAMETERVALUE C "
					+ "WHERE A.SERVICEID=B.SERVICEID AND A.SERVICECODE IS NOT NULL "
					+ "AND B.SERVICEID=C.SERVICEID(+) AND C.PARAMETERVALUEID(+)=3748 "
					+ "AND B.IMSI = '"+s2tImsi+"'";
			System.out.println("sql:"+sql);
			rs = st2.executeQuery(sql);
			
			while(rs.next()){
				s.setS2tIMSI(rs.getString("IMSI"));
				s.setServiceId(rs.getString("SERVICEID"));
				s.setS2tMsisdn(rs.getString("SERVICECODE"));
				s.setChtMsisdn(rs.getString("NCODE"));
				s.setPrivePlanId(rs.getString("PRICEPLANID"));
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
		
		return s;
	}
	
	public Subscriber queryServiceIdbyChtMsisdn(String chtMsisdn) throws SQLException{
		
		Statement st = null;
		Statement st2 = null;
		ResultSet rs = null ;
		String sql = null;
		Subscriber s = new Subscriber();
		try {
			
			st2 = conn2.createStatement();


			/*sql = "SELECT A.SERVICEID,A.SERVICECODE S2TMSISDN,B.FOLLOWMENUMBER CHTMSISDN "
				+ "FROM SERVICE A,FOLLOWMEDATA B "
				+ "WHERE  A.SERVICEID= B.SERVICEID AND A.FOLLOWMENUMBER = '"+chtMsisdn+"'";

			System.out.println("sql:"+sql);
			rs = st2.executeQuery(sql);
			
			while(rs.next()){
				s.setServiceId(rs.getString("SERVICEID"));
				s.setS2tMsisdn(rs.getString("S2TMSISDN"));
				s.setChtMsisdn(rs.getString("CHTMSISDN"));
			}*/
			
			sql = "SELECT A.PRICEPLANID,B.IMSI,A.SERVICECODE,A.PRICEPLANID,A.SUBSIDIARYID,A.SERVICEID,"
					+ "(CASE A. STATUS 	WHEN '1' then to_char(C.value) "
					+ "					when '3' then to_char( C.value) "
					+ "					when '10' then to_char(C.value) else null end) NCODE "
					+ "FROM SERVICE A,IMSI B,PARAMETERVALUE C "
					+ "WHERE A.SERVICEID=B.SERVICEID AND A.SERVICECODE IS NOT NULL "
					+ "AND B.SERVICEID=C.SERVICEID(+) AND C.PARAMETERVALUEID(+)=3748 "
					+ "AND C.value = '"+chtMsisdn+"'";
			System.out.println("sql:"+sql);
			rs = st2.executeQuery(sql);
			
			while(rs.next()){
				s.setS2tIMSI(rs.getString("IMSI"));
				s.setServiceId(rs.getString("SERVICEID"));
				s.setS2tMsisdn(rs.getString("SERVICECODE"));
				s.setChtMsisdn(rs.getString("NCODE"));
				s.setPrivePlanId(rs.getString("PRICEPLANID"));
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
		
		return s;
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
			return " ";
		
		try {
			data = new String(data.getBytes(sCharSet),dCharSet);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return data;
	}

}
