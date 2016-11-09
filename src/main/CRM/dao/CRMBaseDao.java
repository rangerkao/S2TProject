package main.CRM.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import main.CRM.bean.PricePlanID;
import main.CRM.bean.Subscriber;
import main.common.dao.BaseDao;
@Repository
public class CRMBaseDao extends BaseDao{

	public CRMBaseDao() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String queryServiceIdbyS2tMsisdn(String s2tMsisdn) throws Exception{
		
		String serviceid = null;
		
		Statement st1 = null;
		ResultSet rs = null ;
		String sql = "SELECT MAX(A.SERVICEID) SERVICEID "
					+ "FROM SERVICE A "
					+ "where A.SERVICECODE = '"+s2tMsisdn+"' ";
		
		Connection conn = getConn1();
		try {
			st1 = conn.createStatement();
			
			System.out.println("sql:"+sql);
			rs = st1.executeQuery(sql);
			
			while(rs.next()){
				serviceid = rs.getString("SERVICEID");
			}

		}finally{
			try {
				if(rs!=null)	rs.close();
				if(st1!=null) st1.close();
				closeConn1(conn);
			} catch (Exception e) {
			}
		}
		
		return serviceid;
	}
	
	public String queryServiceIdbyChtMsisdn(String chtMsisdn) throws Exception{
		String serviceid = null;
		
		Statement st = null;
		ResultSet rs = null ;
		String sql = "select A.SERVICEID "
					+ "from FOLLOWMEDATA A "
					+ "where A.FOLLOWMENUMBER = '"+chtMsisdn+"'";
		Connection conn = getConn1();
		try {
			st = conn.createStatement();
			
			System.out.println("sql:"+sql);
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				serviceid = rs.getString("SERVICEID");
			}
			
			if(serviceid == null){
				
				rs.close();
				String time = null;
				
				sql = "Select  A.S2T_MSISDN,A.S2T_IMSI,to_char(A.CMCC_OPERATIONDATE,'yyyyMMddhh24miss') TIME "
						+ "from S2T_TB_TYPB_WO_SYNC_FILE_DTL A "
						+ "where (A.FORWARD_TO_HOME_NO like '%"+chtMsisdn+"%' or A.FORWARD_TO_S2T_NO_1 like '%"+chtMsisdn+"%' ) "
						+ "AND trim(A.S2T_MSISDN) is not null "
						+ "order by A.WORK_ORDER_NBR ";
				
				System.out.println("sql:"+sql);
				rs = st.executeQuery(sql);
				
				String s2tMsisdn = null;
				while(rs.next()){
					time = rs.getString("TIME");
					s2tMsisdn = rs.getString("S2T_MSISDN");
				}
				
				rs = null;
				
				sql = "select A.SERVICEID "
						+ "from service A "
						+ "where A.servicecode = '"+s2tMsisdn+"' "
						+ "and A.DATEACTIVATED<to_date('"+time+"','yyyyMMddhh24miss') and (A.DATECANCELED is null or A.DATECANCELED>to_date('"+time+"','yyyyMMddhh24miss')) ";
				
				System.out.println(sql);
				rs = st.executeQuery(sql);
				
				while(rs.next()){
					serviceid = rs.getString("SERVICEID");
				}
				
				//serviceid = queryServiceIdbyS2tMsisdn(s2tMsisdn);
			}

		}finally{
			try {
				if(rs!=null) rs.close();
				if(st!=null) st.close();
				closeConn1(conn);
			} catch (Exception e) {
			}
		}
		return serviceid;
	}
	
	public String queryServiceIdbyS2tImsi(String s2tImsi) throws Exception{
String serviceid = null;
		
		Statement st1 = null;
		ResultSet rs = null ;
		String sql = null;
		Connection conn = getConn1();
		try {
			st1 = conn.createStatement();
			sql = "select MAX(A.SERVICEID)  SERVICEID "
					+ "from imsi A "
					+ "where imsi = '"+s2tImsi+"' ";
;
			System.out.println("sql:"+sql);
			rs = st1.executeQuery(sql);
			
			while(rs.next()){
				serviceid = rs.getString("SERVICEID");
			}
			
			if(serviceid == null){
				
				rs.close();
				
				sql = "Select  A.S2T_MSISDN,A.S2T_IMSI "
						+ "from S2T_TB_TYPB_WO_SYNC_FILE_DTL A "
						+ "where A.S2T_IMSI like '%"+s2tImsi+"%' "
						+ "AND trim(A.S2T_MSISDN) is not null "
						+ "order by A.WORK_ORDER_NBR ";
				
				System.out.println("sql:"+sql);
				rs = st1.executeQuery(sql);
				
				String s2tMsisdn = null;
				while(rs.next()){
					s2tMsisdn = rs.getString("S2T_MSISDN");
				}
				
				serviceid = queryServiceIdbyS2tMsisdn(s2tMsisdn);
			}

		}finally{
			try {
				if(rs!=null) rs.close();
				if(st1!=null) st1.close();
				closeConn1(conn);
			} catch (Exception e) {
			}
		}
		
		
		
		
		return serviceid;
	}
	
	/*public Subscriber queryServiceIdbyS2tMsisdn(String s2tMsisdn) throws Exception{
		
		Statement st2 = null;
		ResultSet rs = null ;
		String sql = null;
		Subscriber s = new Subscriber();
		try {
			st2 = conn.createStatement();
			sql = "SELECT A.PRICEPLANID,B.IMSI,A.SERVICECODE,A.PRICEPLANID,A.SUBSIDIARYID,A.SERVICEID,C.FOLLOWMENUMBER MSISDN "
					+ "FROM SERVICE A,IMSI B,FOLLOWMEDATA C "
					+ "WHERE A.SERVICEID=B.SERVICEID AND A.SERVICECODE IS NOT NULL "
					+ "AND B.SERVICEID=C.SERVICEID(+) AND C.FOLLOWMENUMBER LIKE '886%' "
					+ "AND A.SERVICECODE = '"+s2tMsisdn+"' "
					+ "order by A.DATEACTIVATED ";
			System.out.println("sql:"+sql);
			rs = st2.executeQuery(sql);
			
			while(rs.next()){
				s.setS2tIMSI(rs.getString("IMSI"));
				s.setServiceId(rs.getString("SERVICEID"));
				s.setS2tMsisdn(rs.getString("SERVICECODE"));
				s.setChtMsisdn(rs.getString("MSISDN"));
				s.setPrivePlanId(queryPricePlanId(rs.getString("PRICEPLANID")));
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
	}*/
	
	/*public Subscriber queryServiceIdbyS2tImsi(String s2tImsi) throws Exception{
		
		Statement st = null;
		Statement st2 = null;
		ResultSet rs = null ;
		String sql = null;
		String serviceId = null;
		
		Subscriber s = new Subscriber();

		try {
			st2 = conn.createStatement();
			
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
				s.setPrivePlanId(queryPricePlanId(rs.getString("PRICEPLANID")));
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
	}*/
	
	/*public Subscriber queryServiceIdbyChtMsisdn(String chtMsisdn) throws Exception{
		
		Statement st = null;
		Statement st2 = null;
		ResultSet rs = null ;
		String sql = null;
		Subscriber s = new Subscriber();
		try {
			
			st2 = conn.createStatement();

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
				s.setPrivePlanId(queryPricePlanId(rs.getString("PRICEPLANID")));
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
	}*/
	
	public String queryServiceIdbyChtImsi(String chtImsi) throws Exception{
		
		Statement st = null;
		ResultSet rs = null ;
		String sql = null;
		String serviceId = null;
		Connection conn = getConn2();
		try {
			st = conn.createStatement();
			
			sql = "SELECT A.SERVICEID FROM IMSI A where A.HOMEIMSI='"+chtImsi+"'";
			System.out.println("sql:"+sql);
			rs = st.executeQuery(sql);

			while(rs.next()){
				serviceId = rs.getString("SERVICEID");
			}

		}finally{
			try {
				if(rs!=null) rs.close();
				if(st!=null) st.close();
				closeConn2(conn);
			} catch (Exception e) {
			}
		}
		
		return serviceId;
	}
	
	
	
	public PricePlanID queryPricePlanId(String id) throws SQLException, Exception{
		PricePlanID p = new PricePlanID();
		
		Statement st = null;
		ResultSet rs = null;
		Connection conn = getConn1();
		String sql = "SELECT A.EFFECTIVITY,  A.PRICEPLANID,  A.NAME,  A.ALIASES,  A.PRODUCTION,  A.DESCRIPTION "
				+ "FROM PRICEPLAN_DETAIL A "
				+ "WHERE A.PRICEPLANID = "+id+" ";
		try {
						
			st = conn.createStatement();
			System.out.println("query serviceId detail:"+sql);
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				p.setEffectivity(rs.getString("EFFECTIVITY"));
				p.setAliases(processEncodeData(rs.getString("ALIASES"),"ISO-8859-1","BIG5"));
				p.setDesc(processEncodeData(rs.getString("DESCRIPTION"),"ISO-8859-1","BIG5"));
				p.setId(rs.getString("PRICEPLANID"));
				p.setName(processEncodeData(rs.getString("NAME"),"ISO-8859-1","BIG5"));
				p.setProduction(processEncodeData(rs.getString("PRODUCTION"),"ISO-8859-1","BIG5"));
			}
		} finally{
			try {
				if(rs!=null) rs.close();
				if(st!=null) st.close();
				closeConn1(conn);
			} catch (Exception e) {
			}
			//closeConnection();
		}
		
		return p;
	}

}
