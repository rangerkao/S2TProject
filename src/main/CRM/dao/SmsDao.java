package main.CRM.dao;
 
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
	
	
	public List<SMS> querySMS(String S2TMSISDN,String CHTMSISDN,String startDate,String endDate) throws Exception, ParseException{
		
		
		
		String dateCondiyion = "";
		if(startDate!=null && endDate !=null && !"".equals(startDate) && !"".equals(endDate) && !"NULL".equalsIgnoreCase(startDate) && !"NULL".equalsIgnoreCase(endDate)){
			dateCondiyion = "WHERE SENDTIME >= to_date("+startDate+",'yyyyMMddhh24miss') "
					+ "AND SENDTIME <= to_date("+endDate+",'yyyyMMddhh24miss')";
		}
		
		List<SMS> result = new ArrayList<SMS>();
		
		Statement st = null;
		Statement st2 = null;
		ResultSet rs = null;

		try {
			st = getConn1().createStatement();
			st2 = getConn2().createStatement();
			
			String sql = "SELECT SCLASS,SEND_NUMBER,MSG,TO_CHAR(SENDTIME,'yyyyMMdd hh24:mi:ss') SENDTIME "
					+ "FROM( "
					+ "		SELECT 'DVRS' SCLASS ,A.SEND_NUMBER,A.MSG,A.SEND_DATE  SENDTIME "
					+ "		FROM HUR_SMS_LOG A "
					+ "		WHERE A.SEND_NUMBER = '"+S2TMSISDN+"'"
					+ "		UNION "
					+ "		SELECT 'DVRS' SCLASS ,A.SEND_NUMBER,A.MSG,A.SEND_DATE  SENDTIME "
					+ "		FROM HUR_SMS_LOG A "
					+ "		WHERE A.SEND_NUMBER = '"+CHTMSISDN+"'"
					+ "		UNION "
					+ "		SELECT 'TWNLD' SCLASS,A.PHONENUMBER SEND_NUMBER,A.CONTENT MSG,A.CREATETIME SENDTIME "
					+ "		FROM S2T_BL_SMS_LOG A "
					+ "		WHERE A.PHONENUMBER = '"+CHTMSISDN+"' "
					+ "		UNION "
					+ "		SELECT 'Landing' SCLASS,A.PHONENUMBER SEND_NUMBER,A.CONTENT MSG,A.CREATETIME SENDTIME "
					+ "		FROM S2T_BL_SMS_LOG A "
					+ "		WHERE A.PHONENUMBER = '"+S2TMSISDN+"' "
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
					+ "		SELECT 'LocalNumber' SCLASS,A.SERVICECODE SEND_NUMBER,A.MSGCONTENT MSG,A.LASTSUCCESSTIME SENDTIME "
					+ "		FROM MSSENDINGTASK A "
					+ "		WHERE A.SERVICECODE = '"+CHTMSISDN+"' "
					+ "		UNION "
					+ "		SELECT 'LocalNumber' SCLASS,A.SERVICECODE SEND_NUMBER,A.MSGCONTENT MSG,A.LASTSUCCESSTIME SENDTIME "
					+ "		FROM MESSAGETASK A "
					+ "		WHERE A.SERVICECODE = '"+S2TMSISDN+"' "
					+ "		UNION "
					+ "		SELECT 'LocalNumber' SCLASS,A.SERVICECODE SEND_NUMBER,A.MSGCONTENT MSG,A.LASTSUCCESSTIME SENDTIME "
					+ "		FROM MESSAGETASK A "
					+ "		WHERE A.SERVICECODE = '"+CHTMSISDN+"' "
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
				if(rs!=null)
					rs.close();
				if(st!=null)
					st.close();
				if(st2!=null)
					st2.close();
			} catch (Exception e) {
			}
		}

		//closeConnection();
		return result;
		
	}

}
