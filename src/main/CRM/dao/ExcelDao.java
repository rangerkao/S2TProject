package main.CRM.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import main.CRM.bean.SubscriberExcel;
import main.common.dao.BaseDao;

public class ExcelDao extends CRMBaseDao{



	public ExcelDao() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	//查詢列表
	public List<Map<String,Object>>  querySubscribers() throws Exception{
		
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		/*String sql=
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
				+ "AND E.SERVICECODE = F.S2TMSISDN(+) ";*/
		
		
		Statement st = null;
		ResultSet rs = null;
		
		SubscriberExcel se = new SubscriberExcel();
		se.getClass().getFields();
		try {
			String sql=
					"SELECT DATE_FORMAT(D.VERIFIED_DATE,'%Y/%m/%d') VERIFIED_DATE,B.SERVICEID, "
					+ "A.SUBS_NAME,A.SUBS_ID_TAXID,C.CHAIRMAN,C.CHAIRMAN_ID,A.SUBS_PHONE,A.SUBS_BIRTHDAY, "
					+ "A.SUBS_PERMANENT_ADDRESS,A.SUBS_BILLING_ADDRESS,A.SUBS_EMAIL,A.AGENCY_ID,A.REMARK "
					+ "FROM CRM_DB.CRM_SUBSCRIBERS A inner join "
					+ "		CRM_DB.CRM_SUBSCRIPTION B on A.SEQ=B.SEQ left join "
					+ "		CRM_DB.CRM_CHAIRMAN C on A.SEQ = C.SEQ left join "
					+ "		(	SELECT MAX(VERIFIED_DATE) VERIFIED_DATE,SERVICEID "
					+ "			FROM CRM_DB.CRM_APPLICATION "
					+ "			GROUP BY SERVICEID) D ON B.SERVICEID = D.SERVICEID ";

			st = getConn3().createStatement();
			System.out.println("Execute SQL :"+sql);
			rs = st.executeQuery(sql);
			while(rs.next()){
				Map<String, Object> m = new HashMap<String,Object>();
				m.put("VERIFIED_DATE", rs.getString("VERIFIED_DATE"));
				//m.put("PARTNERMSISDN", rs.getString("PARTNERMSISDN"));
				//m.put("SERVICECODE", rs.getString("SERVICECODE"));
				m.put("SERVICEID", rs.getString("SERVICEID"));
				//m.put("DATEACTIVATED", rs.getString("DATEACTIVATED"));
				//m.put("DATECANCELED", rs.getString("DATECANCELED"));
				m.put("SUBS_NAME", rs.getString("SUBS_NAME"));
				m.put("SUBS_ID_TAXID", rs.getString("SUBS_ID_TAXID"));
				m.put("CHAIRMAN", rs.getString("CHAIRMAN"));
				m.put("CHAIRMAN_ID", rs.getString("CHAIRMAN_ID"));
				m.put("SUBS_PHONE", rs.getString("SUBS_PHONE"));
				m.put("SUBS_BIRTHDAY", rs.getString("SUBS_BIRTHDAY"));
				m.put("SUBS_PERMANENT_ADDRESS", rs.getString("SUBS_PERMANENT_ADDRESS"));
				m.put("SUBS_BILLING_ADDRESS", rs.getString("SUBS_BILLING_ADDRESS"));
				m.put("SUBS_EMAIL", rs.getString("SUBS_EMAIL"));
				m.put("AGENCY_ID", rs.getString("AGENCY_ID"));
				m.put("REMARK", rs.getString("REMARK"));
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
		
		Map<String, Map<String,String>> m2 = new HashMap<String,Map<String,String>>();
		try{
			System.out.println("query remain data!");
			st = getConn1().createStatement();
			String sql = "SELECT F.PARTNERMSISDN,E.SERVICECODE,E.DATEACTIVATED,E.DATECANCELED,E.SERVICEID "
					+ "FROM AVAILABLEMSISDN F,"
					+ "		(	SELECT A.SERVICEID,A.SERVICECODE ||(CASE WHEN A.STATUS!=1 THEN '  (TERMINATED)' END) SERVICECODE, "
					+ "						A.DATEACTIVATED,A.DATECANCELED,A.STATUS s "
					+ "			FROM SERVICE A ,"
					+ "					(	SELECT B.SERVICEID,MAX(B.DATEACTIVATED) DATEACTIVATED "
					+ "						FROM SERVICE B GROUP BY SERVICEID) B "
					+ "						WHERE A.SERVICEID = B.SERVICEID AND A.DATEACTIVATED = B.DATEACTIVATED) E "
					+ "WHERE E.SERVICECODE = F.S2TMSISDN(+) "; //E.SERVICEID IN ({{SERIVCEIDS}})  AND
			
			 
			String serviceids = "";
			int i = 0;
			Map<String,String> m3 = null;
			
			System.out.println("Execute SQL :"+sql);
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				m3 = new HashMap<String,String>();
				m3.put("PARTNERMSISDN", rs.getString("PARTNERMSISDN"));
				m3.put("SERVICECODE", rs.getString("SERVICECODE"));
				m3.put("DATEACTIVATED", rs.getString("DATEACTIVATED"));
				m3.put("DATECANCELED", rs.getString("DATECANCELED"));
				m2.put(rs.getString("SERVICEID"), m3);
			}
			rs.close();
			
			/*for(Map<String, Object> m : result){
				serviceids += m.get("SERVICEID")+",";
				i++;
				if(i==1000){
					if(!"".equals(serviceids)){
						serviceids = serviceids.substring(0,serviceids.length()-1);
						String exe = sql.replace("{{SERIVCEIDS}}", serviceids);
						System.out.println("Execute SQL :"+exe);
						rs = st.executeQuery(exe);
						
						while(rs.next()){
							m3 = new HashMap<String,String>();
							m3.put("PARTNERMSISDN", rs.getString("PARTNERMSISDN"));
							m3.put("SERVICECODE", rs.getString("SERVICECODE"));
							m3.put("DATEACTIVATED", rs.getString("DATEACTIVATED"));
							m3.put("DATECANCELED", rs.getString("DATECANCELED"));
							m2.put(rs.getString("SERVICEID"), m3);
						}
						rs.close();
					}
					serviceids = "";
					i=0;					
				}
				
			}
			if(i>0){
				if(!"".equals(serviceids)){
					serviceids = serviceids.substring(0,serviceids.length()-1);
					String exe = sql.replace("{{SERIVCEIDS}}", serviceids);
					System.out.println("Execute SQL :"+exe);
					rs = st.executeQuery(exe);
					
					while(rs.next()){
						m3 = new HashMap<String,String>();
						m3.put("PARTNERMSISDN", rs.getString("PARTNERMSISDN"));
						m3.put("SERVICECODE", rs.getString("SERVICECODE"));
						m3.put("DATEACTIVATED", rs.getString("DATEACTIVATED"));
						m3.put("DATECANCELED", rs.getString("DATECANCELED"));
						m2.put(rs.getString("SERVICEID"), m3);
					}
					rs.close();
				}
				serviceids = "";
				i=0;					
			}*/
			
			
		}finally{
			try {
				if(st!=null)
					st.close();
				if(rs!=null)
					rs.close();
			} catch (Exception e) {
			}
		}
		System.out.println("Set remain data!");
		Set<String> serviceids = new HashSet<String>();
		
		for(Map<String, Object> m : result){
			String serviceid = (m.get("SERVICEID")!=null ?m.get("SERVICEID").toString():"");
			
			serviceids.add(serviceid);
			if(m2.get(serviceid)!=null){
				m.put("PARTNERMSISDN",m2.get(m.get("SERVICEID")).get("PARTNERMSISDN"));
				m.put("SERVICECODE",m2.get(m.get("SERVICEID")).get("SERVICECODE"));
				m.put("DATEACTIVATED",m2.get(m.get("SERVICEID")).get("DATEACTIVATED"));
				m.put("DATECANCELED",m2.get(m.get("SERVICEID")).get("DATECANCELED"));
			}
		}
		for(String serviceid : m2.keySet()){
			if(!serviceids.contains(serviceid)){
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("SERVICEID",serviceid);
				m.put("PARTNERMSISDN",m2.get(serviceid).get("PARTNERMSISDN"));
				m.put("SERVICECODE",m2.get(serviceid).get("SERVICECODE"));
				m.put("DATEACTIVATED",m2.get(serviceid).get("DATEACTIVATED"));
				m.put("DATECANCELED",m2.get(serviceid).get("DATECANCELED"));
				result.add(m);
			}
		}

		System.out.println("data query finished");
		//closeConnection();
		return sortByServiceid(result);		
	}
	
	//20160223 add 
	public List<Map<String,Object>> sortByServiceid(List<Map<String,Object>> list){
		
		System.out.println("sort data.");
		List<Map<String,Object>> result = null;
		try {
			result = mergeSortByServiceid(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("sort data end."+list.size()+","+result.size());
		return result;

	}
	
	public List<Map<String,Object>> mergeSortByServiceid(List<Map<String,Object>> list){
		
		int total = list.size();
		int middle = (int) Math.floor(total/2);
		
		if(total <= 1){
			return list;
		}
		
		List<Map<String,Object>> left = mergeSortByServiceid(list.subList(0, middle));
		List<Map<String,Object>> right = mergeSortByServiceid(list.subList(middle,total));
		
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();

		int i = 0,j=0;
		
		while(i<left.size()&&j<right.size()){
			int servicei = Integer.parseInt(left.get(i).get("SERVICEID") == null ? "0"	: (String) left.get(i).get("SERVICEID"));
			int servicej = Integer.parseInt(right.get(j).get("SERVICEID") == null ? "0"	: (String) right.get(j).get("SERVICEID"));
			if(servicei<=servicej){
				result.add(left.get(i));
				i++;
			}else{
				result.add(right.get(j));
				j++;
			}
		}
		
		for(;i<left.size();i++){
			result.add(left.get(i));
		}
		for(;j<right.size();j++){
			result.add(right.get(j));
		}
		
		return result;
	}
	
	public List<Map<String,Object>> bubbleSortByServiceid(List<Map<String,Object>> list){
		
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = 0; j < list.size() - 1; j++) {
				Map<String, Object> m1 = list.get(j);
				Map<String, Object> m2 = list.get(j + 1);
				int servicei = Integer.parseInt(m1.get("SERVICEID") == null ? "0"	: (String) m1.get("SERVICEID"));
				int servicej = Integer.parseInt(m2.get("SERVICEID") == null ? "0"	: (String) m2.get("SERVICEID"));

				if (servicei > servicej) {
					list.set(j + 1, m1);
					list.set(j, m2);
				}
			}

		}
		return list;
	}
	
	
}
