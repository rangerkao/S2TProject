package main.CRM.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import main.CRM.bean.SMS;
import main.common.dao.BaseDao;

public class SMSDao extends CRMBaseDao{

	public SMSDao() throws Exception {
		// TODO Auto-generated constructor stub
	}
	
	
	public List<SMS> querySMS(String S2TMSISDN,String CHTMSISDN,String startDate,String endDate) throws SQLException, ParseException{
		
		
		
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
			st = conn.createStatement();
			st2 = conn2.createStatement();
			
			String sql = "SELECT SCLASS,SEND_NUMBER,MSG,TO_CHAR(SENDTIME,'yyyyMMddhh24miss') SENDTIME "
					+ "FROM( "
					+ "		SELECT 'DVRS' SCLASS ,A.SEND_NUMBER,A.MSG,A.SEND_DATE  SENDTIME "
					+ "		FROM HUR_SMS_LOG A "
					+ "		WHERE A.SEND_NUMBER = '"+S2TMSISDN+"'"
					+ "		UNION "
					+ "		SELECT 'TWNLD' SCLASS,A.PHONENUMBER SEND_NUMBER,A.CONTENT MSG,A.CREATETIME SENDTIME "
					+ "		FROM S2T_BL_SMS_LOG A "
					+ "		WHERE A.PHONENUMBER = '"+CHTMSISDN+"' "
					+ "		UNION "
					+ "		SELECT 'ARRIVED' SCLASS,A.PHONENUMBER SEND_NUMBER,A.CONTENT MSG,A.CREATETIME SENDTIME "
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
			
			sql = "SELECT SCLASS,SEND_NUMBER,MSG,TO_CHAR(SENDTIME,'yyyyMMddhh24miss') SENDTIME "
					+ "FROM( "
					+ "		SELECT 'MSSENDINGTASK' SCLASS,A.SERVICECODE SEND_NUMBER,A.MSGCONTENT MSG,A.LASTSUCCESSTIME SENDTIME "
					+ "		FROM MSSENDINGTASK A "
					+ "		WHERE A.SERVICECODE = '"+S2TMSISDN+"' "
					+ "		UNION "
					+ "		SELECT 'MSSENDINGTASK' SCLASS,A.SERVICECODE SEND_NUMBER,A.MSGCONTENT MSG,A.LASTSUCCESSTIME SENDTIME "
					+ "		FROM MSSENDINGTASK A "
					+ "		WHERE A.SERVICECODE = '"+CHTMSISDN+"' "
					+ "		UNION "
					+ "		SELECT 'MESSAGETASK' SCLASS,A.SERVICECODE SEND_NUMBER,A.MSGCONTENT MSG,A.LASTSUCCESSTIME SENDTIME "
					+ "		FROM MESSAGETASK A "
					+ "		WHERE A.SERVICECODE = '"+S2TMSISDN+"' "
					+ "		UNION "
					+ "		SELECT 'MESSAGETASK' SCLASS,A.SERVICECODE SEND_NUMBER,A.MSGCONTENT MSG,A.LASTSUCCESSTIME SENDTIME "
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

		closeConnection();
		return result;
		
	}
	
	
	public long getTimeValue(String s) throws ParseException{
		Long value = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		value=sdf.parse(s).getTime();
		return value;
	}
	
	
	public void sort(List<SMS> l) throws ParseException{
		
		int size = l.size();
		
		for(int i = 0 ; i<size;i++){
			for(int j = 1 ;j<size;j++){
				swap(l,j-1,getTimeValue(l.get(j-1).getSendTime()),j,getTimeValue(l.get(j).getSendTime()));
			}
		}
		
	}
	
	
	public void swap(List<SMS> l,int i ,long vi,int j,long vj){
		
		if(vi<vj){
			SMS io = l.get(i);
			SMS jo = l.get(j);
			l.set(i, jo);
			l.set(j, io);
		}
	}

}
