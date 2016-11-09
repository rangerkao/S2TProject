package main.CRM.dao;
 
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import main.CRM.bean.SMS;
import main.common.dao.BaseDao;
@Repository
public class SmsDao extends CRMBaseDao{

	public SmsDao() throws Exception {
		// TODO Auto-generated constructor stub
	}
	
	public List<SMS> querySMS(String S2TMSISDN,String CHTMSISDN,String startDate,String endDate,String activatedDate,String canceledDate) throws Exception, ParseException{
		
		String dateCondiyion = "WHERE SENDTIME >  to_date('"+activatedDate+"','yyyy/MM/dd hh24:mi:ss') "
				+ ("".equals(canceledDate) ?"": "AND SENDTIME < to_date('"+canceledDate+"','yyyy/MM/dd hh24:mi:ss') ");
		
		if(startDate!=null && endDate !=null && !"".equals(startDate) && !"".equals(endDate) && !"NULL".equalsIgnoreCase(startDate) && !"NULL".equalsIgnoreCase(endDate)){
			dateCondiyion += " SENDTIME >= to_date("+startDate+",'yyyyMMddhh24miss') "
					+ "AND SENDTIME <= to_date("+endDate+",'yyyyMMddhh24miss')";
		}
		
		List<SMS> result = new ArrayList<SMS>();
		
		Statement st = null;
		Statement st2 = null;
		ResultSet rs = null;
		Connection conn1 = getConn1();
		Connection conn2 = getConn2();
		try {
			st = conn1.createStatement();
			st2 = conn2.createStatement();
			
			String sql = "SELECT SCLASS,SEND_NUMBER,MSG,TO_CHAR(SENDTIME,'yyyyMMdd hh24:mi:ss') SENDTIME "
					+ "FROM( "
					+ "		SELECT 'DVRS' SCLASS ,A.SEND_NUMBER,A.MSG,A.SEND_DATE  SENDTIME "
					+ "		FROM HUR_SMS_LOG A "
					+ "		WHERE A.SEND_NUMBER = '"+S2TMSISDN+"'"
					+ "		UNION "
					+ "		SELECT 'DVRS' SCLASS ,B.SEND_NUMBER,B.MSG,B.SEND_DATE  SENDTIME "
					+ "		FROM HUR_SMS_LOG B "
					+ "		WHERE B.SEND_NUMBER = '"+CHTMSISDN+"'"
					+ "		UNION "
					+ "		SELECT 'TWNLD' SCLASS,C.PHONENUMBER SEND_NUMBER,C.CONTENT MSG,C.CREATETIME SENDTIME "
					+ "		FROM S2T_BL_SMS_LOG C "
					+ "		WHERE C.PHONENUMBER = '"+CHTMSISDN+"' "
					+ "		UNION "
					+ "		SELECT 'Landing' SCLASS,D.PHONENUMBER SEND_NUMBER,D.CONTENT MSG,D.CREATETIME SENDTIME "
					+ "		FROM S2T_BL_SMS_LOG D "
					+ "		WHERE D.PHONENUMBER = '"+S2TMSISDN+"' "
					+ ") "
					+dateCondiyion
					+ " ORDER BY SENDTIME DESC ";

			System.out.println(sql);
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				SMS r = new SMS();
				r.setSmsclass(rs.getString("SCLASS"));
				r.setPhoneno(rs.getString("SEND_NUMBER"));
				r.setContent(processEncodeData(rs.getString("MSG"),"ISO-8859-1","BIG5"));
				r.setSendTime(rs.getString("SENDTIME"));
				result.add(r);
			}
			
			rs.close();
			rs = null;
			
			sql = "SELECT SCLASS,SEND_NUMBER,MSG,TO_CHAR(SENDTIME,'yyyyMMdd hh24:mi:ss') SENDTIME "
					+ "FROM( "
					+ "		SELECT 'LocalNumber' SCLASS,A.SERVICECODE SEND_NUMBER,A.MSGCONTENT MSG,A.LASTSUCCESSTIME SENDTIME "
					+ "		FROM MSSENDINGTASK A "
					+ "		WHERE A.SERVICECODE = '"+S2TMSISDN+"' "
					+ "		UNION "
					+ "		SELECT 'LocalNumber' SCLASS,B.SERVICECODE SEND_NUMBER,B.MSGCONTENT MSG,B.LASTSUCCESSTIME SENDTIME "
					+ "		FROM MSSENDINGTASK B "
					+ "		WHERE B.SERVICECODE = '"+CHTMSISDN+"' "
					+ "		UNION "
					+ "		SELECT 'LocalNumber' SCLASS,C.SERVICECODE SEND_NUMBER,C.MSGCONTENT MSG,C.LASTSUCCESSTIME SENDTIME "
					+ "		FROM MESSAGETASK C "
					+ "		WHERE C.SERVICECODE = '"+S2TMSISDN+"' "
					+ "		UNION "
					+ "		SELECT 'LocalNumber' SCLASS,D.SERVICECODE SEND_NUMBER,D.MSGCONTENT MSG,D.LASTSUCCESSTIME SENDTIME "
					+ "		FROM MESSAGETASK D "
					+ "		WHERE D.SERVICECODE = '"+CHTMSISDN+"' "
					+ ") "
					+dateCondiyion
					+ " ORDER BY SENDTIME DESC ";

			System.out.println(sql);
			rs = st2.executeQuery(sql);
			
			while(rs.next()){
				SMS r = new SMS();
				r.setSmsclass(rs.getString("SCLASS"));
				r.setPhoneno(rs.getString("SEND_NUMBER"));
				r.setContent(processEncodeData(rs.getString("MSG"),"ISO-8859-1","BIG5"));
				r.setSendTime(rs.getString("SENDTIME"));
				result.add(r);
			}
			
			
			sort(result);
			
		}finally{
			try {
				if(rs!=null) rs.close();
				if(st!=null) st.close();
				if(st2!=null) st2.close();
				closeConn1(conn1);
				closeConn2(conn2);
			} catch (Exception e) {
			}
		}

		//closeConnection();
		return result;
		
		
	}


}
