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
	
	public String queryServiceIdbyS2tMsisdn(String s2tMsisdn,boolean isTeminated) throws Exception{
		
		String serviceid = null;
		
		Statement st1 = null;
		ResultSet rs = null ;
		
		String sql1 = " SELECT MAX(SERVICEID) SERVICEID from service where SERVICECODE = '"+s2tMsisdn+"' "
				+ (isTeminated?"and DateCanceled is not null":"and DateCanceled is null ");
		
		
		String sql2 = "SELECT MAX(A.SERVICEID) SERVICEID "
					+ "FROM SERVICE A "
					+ "where A.SERVICECODE = '"+s2tMsisdn+"' ";
		
		Connection conn = getConn1();

		try {
			st1 = conn.createStatement();
			
			System.out.println("sql:"+sql1);
			rs = st1.executeQuery(sql1);
			while(rs.next()){
				serviceid = rs.getString("SERVICEID");
			}
			
			if(serviceid==null||"".equals(serviceid)){
				rs.close();
				
				System.out.println("sql:"+sql2);
				rs = st1.executeQuery(sql2);
				
				while(rs.next()){
					serviceid = rs.getString("SERVICEID");
				}
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
	
	//public String queryServiceIdbyChtMsisdn(String chtMsisdn) throws Exception{
	public Subscriber queryServiceIdbyChtMsisdn(String chtMsisdn) throws Exception{
		Subscriber s = new Subscriber();
		s.setChtMsisdn(chtMsisdn); 
		
		String serviceid = null;
		
		Statement st = null;
		ResultSet rs = null ;
		//查詢現行資料
		String sql = "select A.SERVICEID "
					+ "from FOLLOWMEDATA A "
					+ "where A.FOLLOWMENUMBER = '"+chtMsisdn+"' ";
		Connection conn = getConn1();
		try {
			st = conn.createStatement();
			
			System.out.println("sql:"+sql);
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				serviceid = rs.getString("SERVICEID");
				s.setServiceId(serviceid);
			}
			
			if(serviceid == null){
				
				rs.close();
				String time = null;
				String workType = null;
				
				
				
				//20180118 add
				sql = "select to_char(REQTIME,'yyyyMMddhh24miss') TIME,"
						+ "SUBSTR(A.content,instr(content,'S2T_IMSI=')+length('S2T_IMSI='),15) S2T_IMSI,"
						+ "SUBSTR(A.content,instr(content,'TWNLD_IMSI=')+length('TWNLD_IMSI='),15) TWNLD_IMSI,"
						+ "SUBSTR(A.content,instr(content,'Req_Status=')+length('Req_Status='),2) WORK_TYPE " 
						+ "from provlog A "
						+ "where content like '%"+chtMsisdn+"%' "
						+ "order by REQTIME DESC";
				String s2tMsisdn = null;
				String s2tIMSI = null;
				String homeIMSI = null;
				
				System.out.println("sql:"+sql);
				rs = st.executeQuery(sql);
				
				//PROVLOG 查的到資料，環球卡用戶
				if(rs.next()){
					System.out.println("IS CHT USER.");
					time = rs.getString("TIME");
					workType = rs.getString("WORK_TYPE");
					s2tIMSI = rs.getString("S2T_IMSI");
					homeIMSI = rs.getString("TWNLD_IMSI");
					
					System.out.println("workType:"+workType);
					//20170921 確認是否換過號
					int count = 0;
					while(!"99".equals(workType)&&!"0".equals(workType)&&count<=10) {
						count++; //防無限迴圈
						rs = null;
						workType = "0";
						//查詢是否同IMSI之後是否有05換中華號
						//20180118 add
						sql = "select to_char(REQTIME,'yyyyMMddhh24miss') TIME,"
								+ "SUBSTR(A.content,instr(content,'S2T_IMSI=')+length('S2T_IMSI='),15) S2T_IMSI,"
								+ "SUBSTR(A.content,instr(content,'Req_Status=')+length('Req_Status='),2) WORK_TYPE, " 
								+ "SUBSTR(A.content,instr(content,'TWNLD_MSISDN=')+length('TWNLD_MSISDN='),12) TWNLD_MSISDN "
								+ "from provlog A "
								+ "where content like '%"+s2tIMSI+"%' "
								+ "and A.REQTIME > to_date('"+time+"','yyyyMMddhh24miss')" 
								+ "order by REQTIME ";
						
						System.out.println("sql:"+sql);
						rs = st.executeQuery(sql);

						if(rs.next()) {
							time = rs.getString("TIME");
							workType = rs.getString("WORK_TYPE");
							chtMsisdn = rs.getString("TWNLD_MSISDN");
						}
						System.out.println("workType:"+workType);
						//如果同imsi之後的第一筆為05，表示有換號，重新查詢是否退租
						if("05".equals(workType)) {
							rs = null;
							workType = "0";
							//20180118 add
							sql = "select to_char(REQTIME,'yyyyMMddhh24miss') TIME,"
									+ "SUBSTR(A.content,instr(content,'S2T_IMSI=')+length('S2T_IMSI='),15) S2T_IMSI,"
									+ "SUBSTR(A.content,instr(content,'Req_Status=')+length('Req_Status='),2) WORK_TYPE " 
									+ "from provlog A "
									+ "where content like '%"+chtMsisdn+"%' "
									+ "and A.REQTIME > to_date('"+time+"','yyyyMMddhh24miss')" 
									+ "order by REQTIME DESC";
							
							System.out.println("sql:"+sql);
							rs = st.executeQuery(sql);
							
							if(rs.next()){
								time = rs.getString("TIME");
								workType = rs.getString("WORK_TYPE");
								s2tIMSI = rs.getString("S2T_IMSI");
							}
						}
					}
					//查詢在最後一張工單之後所獲得的S2TMSISDN
					rs = null;
					
					//20180118 add 查詢Service
					String sql2 = "Select  A.S2T_MSISDN,A.S2T_IMSI,to_char(A.CMCC_OPERATIONDATE,'yyyyMMddhh24miss') TIME,A.WORK_TYPE,A.ORIGINAL_CMCC_MSISDN " + 
							"from S2T_TB_TYPB_WO_SYNC_FILE_DTL A " + 
							"where A.S2T_IMSI = '"+s2tIMSI+"' " + 
							"and A.CMCC_OPERATIONDATE <= to_date('"+time+"','yyyyMMddhh24miss')" + 
							"order by A.WORK_ORDER_NBR DESC";
					
					System.out.println("sql:"+sql2);
					rs = st.executeQuery(sql2);
					
					if(rs.next()) {
						s2tMsisdn = rs.getString("S2T_MSISDN");
					}
				//非環球卡用戶	
				}else {
					System.out.println("NOT CHT USER.");
					rs = null;
					//查詢最後一張工單
					sql = "Select  A.S2T_MSISDN,A.S2T_IMSI,to_char(A.CMCC_OPERATIONDATE,'yyyyMMddhh24miss') TIME,A.WORK_TYPE "
					+ "from S2T_TB_TYPB_WO_SYNC_FILE_DTL A "
					+ "where A.ORIGINAL_CMCC_MSISDN like '%"+chtMsisdn+"%' "
					+ "AND trim(A.S2T_MSISDN) is not null "
					+ "order by A.WORK_ORDER_NBR DESC ";
			
					System.out.println("sql:"+sql);
					rs = st.executeQuery(sql);
					if(rs.next()){
						time = rs.getString("TIME");
						s2tMsisdn = rs.getString("S2T_MSISDN");
						workType = rs.getString("WORK_TYPE");
						s2tIMSI = rs.getString("S2T_IMSI");
						
						System.out.println("workType:"+workType);
						//20170921 確認是否換過號
						int count = 1;
						while(!"99".equals(workType)&&!"0".equals(workType)&&count<=10) {
							count++;
							rs = null;
							workType = "0";
							String sql2 = "Select  A.S2T_MSISDN,A.S2T_IMSI,to_char(A.CMCC_OPERATIONDATE,'yyyyMMddhh24miss') TIME,A.WORK_TYPE,A.ORIGINAL_CMCC_MSISDN " + 
									"from S2T_TB_TYPB_WO_SYNC_FILE_DTL A " + 
									"where A.S2T_IMSI = '"+s2tIMSI+"' " + 
									"and A.CMCC_OPERATIONDATE > to_date('"+time+"','yyyyMMddhh24miss')" + 
									"order by A.WORK_ORDER_NBR ";
							
							System.out.println("sql:"+sql2);
							rs = st.executeQuery(sql2);

							if(rs.next()) {
								time = rs.getString("TIME");
								workType = rs.getString("WORK_TYPE");
								chtMsisdn = rs.getString("ORIGINAL_CMCC_MSISDN");
							}
							System.out.println("workType:"+workType);
							//如果同imsi之後的第一筆為05，表示有換號，重新查詢最後一筆的紀錄
							if("05".equals(workType)) {
								rs = null;
								workType = "0";
								sql = "Select  A.S2T_MSISDN,A.S2T_IMSI,to_char(A.CMCC_OPERATIONDATE,'yyyyMMddhh24miss') TIME,A.WORK_TYPE "
										+ "from S2T_TB_TYPB_WO_SYNC_FILE_DTL A "
										+ "where A.ORIGINAL_CMCC_MSISDN like '%"+chtMsisdn+"%' "
										+ "AND trim(A.S2T_MSISDN) is not null "
										+ "and A.CMCC_OPERATIONDATE > to_date('"+time+"','yyyyMMddhh24miss')"
										+ "order by A.WORK_ORDER_NBR DESC ";
								
								System.out.println("sql:"+sql);
								rs = st.executeQuery(sql);
								
								if(rs.next()){
									time = rs.getString("TIME");
									workType = rs.getString("WORK_TYPE");
									s2tIMSI = rs.getString("S2T_IMSI");
								}	
							}
						}
					}	
				}
				//從service查詢 對應的Serviceid
				if(s2tMsisdn!=null) {
					rs = null;
					
					sql = "select A.SERVICEID "
							+ "from service A "
							+ "where A.servicecode = '"+s2tMsisdn+"' "
							+ "and A.DATEACTIVATED<=to_date('"+time+"','yyyyMMddhh24miss') "
							+ "and "+("99".equals(workType)?"A.DATECANCELED>=to_date('"+time+"','yyyyMMddhh24miss')" : "A.DATECANCELED is null");				
					System.out.println(sql);
					rs = st.executeQuery(sql);
					
					while(rs.next()){
						serviceid = rs.getString("SERVICEID");
						s.setServiceId(serviceid);
					}
				}
				
				//serviceid = queryServiceIdbyS2tMsisdn(s2tMsisdn);
				s.setS2tIMSI(s2tIMSI);
				s.setS2tMsisdn(s2tMsisdn);
				s.setHomeIMSI(homeIMSI);
			}

		}finally{
			try {
				if(rs!=null) rs.close();
				if(st!=null) st.close();
				closeConn1(conn);
			} catch (Exception e) {
			}
		}
		return s;
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
				
				serviceid = queryServiceIdbyS2tMsisdn(s2tMsisdn,true);
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
