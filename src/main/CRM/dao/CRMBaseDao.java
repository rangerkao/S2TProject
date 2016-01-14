package main.CRM.dao;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import main.CRM.bean.Subscriber;
import main.common.dao.BaseDao;
@Repository
public class CRMBaseDao extends BaseDao{

	public CRMBaseDao() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Subscriber queryServiceIdbyS2tMsisdn(String s2tMsisdn) throws Exception{
		
		Statement st2 = null;
		ResultSet rs = null ;
		String sql = null;
		Subscriber s = new Subscriber();
		try {
			st2 = getConn2().createStatement();
			sql = "SELECT A.PRICEPLANID,B.IMSI,A.SERVICECODE,A.PRICEPLANID,A.SUBSIDIARYID,A.SERVICEID,C.FOLLOWMENUMBER MSISDN "
					+ "FROM SERVICE A,IMSI B,FOLLOWMEDATA C "
					+ "WHERE A.SERVICEID=B.SERVICEID AND A.SERVICECODE IS NOT NULL "
					+ "AND B.SERVICEID=C.SERVICEID(+) AND C.FOLLOWMENUMBER LIKE '886%' "
					+ "AND A.SERVICECODE = '"+s2tMsisdn+"'";
			System.out.println("sql:"+sql);
			rs = st2.executeQuery(sql);
			
			while(rs.next()){
				s.setS2tIMSI(rs.getString("IMSI"));
				s.setServiceId(rs.getString("SERVICEID"));
				s.setS2tMsisdn(rs.getString("SERVICECODE"));
				s.setChtMsisdn(rs.getString("MSISDN"));
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
	
	public Subscriber queryServiceIdbyS2tImsi(String s2tImsi) throws Exception{
		
		Statement st = null;
		Statement st2 = null;
		ResultSet rs = null ;
		String sql = null;
		String serviceId = null;
		
		Subscriber s = new Subscriber();

		try {
			st2 = getConn2().createStatement();
			
			sql = "SELECT A.PRICEPLANID,B.IMSI,A.SERVICECODE,A.PRICEPLANID,A.SUBSIDIARYID,A.SERVICEID,C.FOLLOWMENUMBER MSISDN "
					+ "FROM SERVICE A,IMSI B,FOLLOWMEDATA C "
					+ "WHERE A.SERVICEID=B.SERVICEID AND A.SERVICECODE IS NOT NULL "
					+ "AND B.SERVICEID=C.SERVICEID(+) AND C.FOLLOWMENUMBER LIKE '886%' "
					+ "AND B.IMSI = '"+s2tImsi+"'";
			System.out.println("sql:"+sql);
			rs = st2.executeQuery(sql);
			
			while(rs.next()){
				s.setS2tIMSI(rs.getString("IMSI"));
				s.setServiceId(rs.getString("SERVICEID"));
				s.setS2tMsisdn(rs.getString("SERVICECODE"));
				s.setChtMsisdn(rs.getString("MSISDN"));
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
	
	public Subscriber queryServiceIdbyChtMsisdn(String chtMsisdn) throws Exception{
		
		Statement st = null;
		Statement st2 = null;
		ResultSet rs = null ;
		String sql = null;
		Subscriber s = new Subscriber();
		try {
			
			st2 = getConn2().createStatement();

			sql = "SELECT A.PRICEPLANID,B.IMSI,A.SERVICECODE,A.PRICEPLANID,A.SUBSIDIARYID,A.SERVICEID,C.FOLLOWMENUMBER MSISDN "
					+ "FROM SERVICE A,IMSI B,FOLLOWMEDATA C "
					+ "WHERE A.SERVICEID=B.SERVICEID AND A.SERVICECODE IS NOT NULL "
					+ "AND B.SERVICEID=C.SERVICEID(+) AND C.FOLLOWMENUMBER LIKE '886%' "
					+ "AND C.FOLLOWMENUMBER = '"+chtMsisdn+"'";
			System.out.println("sql:"+sql);
			rs = st2.executeQuery(sql);
			
			while(rs.next()){
				s.setS2tIMSI(rs.getString("IMSI"));
				s.setServiceId(rs.getString("SERVICEID"));
				s.setS2tMsisdn(rs.getString("SERVICECODE"));
				s.setChtMsisdn(rs.getString("MSISDN"));
				s.setPrivePlanId(rs.getString("PRICEPLANID"));
			}
		}
		catch(Exception e){
			e.printStackTrace();
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
	
	public String queryServiceIdbyChtImsi(String chtImsi) throws Exception{
		
		Statement st = null;
		Statement st2 = null;
		ResultSet rs = null ;
		String sql = null;
		String serviceId = null;

		try {
			st = getConn1().createStatement();
			st2 = getConn2().createStatement();
			
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
			if(sCharSet==null || "".equals(sCharSet))
				data = new String(data.getBytes(),dCharSet);
			else
				data = new String(data.getBytes(sCharSet),dCharSet);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return data;
	}

}
