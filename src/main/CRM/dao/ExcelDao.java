package main.CRM.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.CRM.bean.SubscriberExcel;
import main.common.dao.BaseDao;

public class ExcelDao extends CRMBaseDao{

	public ExcelDao() throws Exception {
		super();
	}

	//查詢列表
	public List<Map<String,Object>>  querySubscribers() throws SQLException{
		
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		String sql=
				"SELECT to_char(D.VERIFIED_DATE,'yyyy/MM/dd') VERIFIED_DATE,F.PARTNERMSISDN,E.SERVICECODE,B.SERVICEID,E.DATEACTIVATED,E.DATECANCELED, "
				+ "A.SUBS_NAME,A.SUBS_ID_TAXID,C.CHAIRMAN,C.CHAIRMAN_ID,A.SUBS_PHONE,A.SUBS_BIRTHDAY, "
				+ "A.SUBS_PERMANENT_ADDRESS,A.SUBS_BILLING_ADDRESS,A.SUBS_EMAIL,A.AGENCY_ID,A.REMARK "
				+ "FROM CRM_SUBSCRIBERS A,CRM_SUBSCRIPTION B,CRM_CHAIRMAN C,"
				+ "		(	SELECT MAX(VERIFIED_DATE) VERIFIED_DATE,SERVICEID "
				+ "			FROM CRM_APPLICATION"
				+ "			GROUP BY SERVICEID) D,"
				+ "		(	SELECT A.SERVICEID,A.SERVICECODE ||(CASE WHEN A.STATUS!=1 THEN '  (TERMINATED)' END) SERVICECODE, "
				+ "					A.DATEACTIVATED,A.DATECANCELED,A.STATUS s "
				+ "			FROM SERVICE A ,"
				+ "				(	SELECT B.SERVICEID,MAX(B.DATEACTIVATED) DATEACTIVATED "
				+ "					FROM SERVICE B GROUP BY SERVICEID) B "
				+ "					WHERE A.SERVICEID = B.SERVICEID AND A.DATEACTIVATED = B.DATEACTIVATED) E, "
				+ "		AVAILABLEMSISDN F "
				+ "WHERE A.SEQ = B.SEQ "
				+ "AND A.SEQ = C.SEQ(+) "
				+ "AND B.SERVICEID = D.SERVICEID(+) "
				+ "AND B.SERVICEID =  to_char(E.SERVICEID) "
				+ "AND E.SERVICECODE = F.S2TMSISDN(+) ";
		
		Statement st = null;
		ResultSet rs = null;
		
		SubscriberExcel se = new SubscriberExcel();
		se.getClass().getFields();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			System.out.println("Execute SQL :"+sql);
			while(rs.next()){
				Map<String, Object> m = new HashMap<String,Object>();
				m.put("VERIFIED_DATE", rs.getString("VERIFIED_DATE"));
				m.put("PARTNERMSISDN", rs.getString("PARTNERMSISDN"));
				m.put("SERVICECODE", rs.getString("SERVICECODE"));
				m.put("SERVICEID", rs.getString("SERVICEID"));
				m.put("DATEACTIVATED", rs.getString("DATEACTIVATED"));
				m.put("DATECANCELED", rs.getString("DATECANCELED"));
				m.put("SUBS_NAME", processEncodeData(rs.getString("SUBS_NAME"),"ISO-8859-1","BIG5"));
				m.put("SUBS_ID_TAXID", rs.getString("SUBS_ID_TAXID"));
				m.put("CHAIRMAN", processEncodeData(rs.getString("CHAIRMAN"),"ISO-8859-1","BIG5"));
				m.put("CHAIRMAN_ID", rs.getString("CHAIRMAN_ID"));
				m.put("SUBS_PHONE", rs.getString("SUBS_PHONE"));
				m.put("SUBS_BIRTHDAY", rs.getString("SUBS_BIRTHDAY"));
				m.put("SUBS_PERMANENT_ADDRESS", processEncodeData(rs.getString("SUBS_PERMANENT_ADDRESS"),"ISO-8859-1","BIG5"));
				m.put("SUBS_BILLING_ADDRESS", processEncodeData(rs.getString("SUBS_BILLING_ADDRESS"),"ISO-8859-1","BIG5"));
				m.put("SUBS_EMAIL", rs.getString("SUBS_EMAIL"));
				m.put("AGENCY_ID", processEncodeData(rs.getString("AGENCY_ID"),"ISO-8859-1","BIG5"));
				m.put("REMARK", processEncodeData(rs.getString("REMARK"),"ISO-8859-1","BIG5"));
				result.add(m);
			}
		} finally{
			try {
				if(st!=null)
					st.close();
				if(rs!=null)
					rs.close();
			} catch (Exception e) {
			}
		}
		closeConnection();
			
		return result;
		
	}
	
	
}
