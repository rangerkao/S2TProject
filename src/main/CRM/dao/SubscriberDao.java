package main.CRM.dao;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.CRM.bean.SMS;
import main.CRM.bean.Subscriber;
import main.common.dao.BaseDao;

public class SubscriberDao extends BaseDao{

	public SubscriberDao() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	//select ID and data
	public List<Subscriber> queryListById(String id) throws SQLException{
		List<Subscriber> result = new ArrayList<Subscriber>();
		result = queryList("WHERE A.SUBS_ID_TAXID='"+id+"' ");
		closeConnection();
		return result;
	}
	
	public List<Subscriber> queryListByName(String name) throws SQLException{
		List<Subscriber> result = new ArrayList<Subscriber>();
		result = queryList("WHERE A.SUBS_NAME Like '%"+processEncodeData(name,"BIG5","ISO-8859-1")+"%' ");
		closeConnection();
		return result;
	}
	
	private List<Subscriber> queryList(String condition) throws SQLException{
		List<Subscriber> result = new ArrayList<Subscriber>();
		
		String sql = "SELECT A.SUBS_ID_TAXID ID,A.SUBS_NAME,A.SUBS_PERMANENT_ADDRESS FROM CRM_SUBSCRIBERS A "+condition; 
		
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next()){
			Subscriber s = new Subscriber();
			s.setIdTaxid(rs.getString("ID"));
			s.setName(processEncodeData(rs.getString("SUBS_NAME"),"ISO-8859-1","BIG5"));
			s.setPermanentAddress(processEncodeData(rs.getString("SUBS_PERMANENT_ADDRESS"),"ISO-8859-1","BIG5"));
			result.add(s);
		}		
		rs.close();
		st.close();
		return result;
	}
	
	//select all data
	public Subscriber queryDataById(String id) throws SQLException{
		Subscriber result = new Subscriber(); 
		
		String sql = "SELECT A.SUBS_ID_TAXID ID,A.SUBS_NAME,A.SUBS_BIRTHDAY,A.SUBS_PHONE,A.SUBS_EMAIL,A.SUBS_PERMANENT_ADDRESS,A.SUBS_BILLING_ADDRESS,A.AGENCY_ID,A.REMARK, "
				+ "to_char(A.CREATETIME,'yyy/MM/dd hh24:mi:ss') CREATETIME,to_char(A.UPDATETIME,'yyy/MM/dd hh24:mi:ss') UPDATETIME,A.SUBS_TYPE "
				+ "FROM CRM_SUBSCRIBERS A WHERE A.SUBS_ID_TAXID='"+id+"' ";
		
		Statement st = conn.createStatement();
		Statement st2 = conn2.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next()){

			result.setName(processEncodeData(rs.getString("SUBS_NAME"),"ISO-8859-1","BIG5"));
			result.setIdTaxid(processData(rs.getString("ID")));
			result.setBirthday(processData(rs.getString("SUBS_BIRTHDAY")));
			result.setPhone(processData(rs.getString("SUBS_PHONE")));
			result.setEmail(processData(rs.getString("SUBS_EMAIL")));
			result.setPermanentAddress(processEncodeData(rs.getString("SUBS_PERMANENT_ADDRESS"),"ISO-8859-1","BIG5"));
			result.setBillingAddress(processEncodeData(rs.getString("SUBS_BILLING_ADDRESS"),"ISO-8859-1","BIG5"));
			result.setAgency(processEncodeData(rs.getString("AGENCY_ID"),"ISO-8859-1","BIG5"));
			result.setRemark(processEncodeData(rs.getString("REMARK"),"ISO-8859-1","BIG5"));
			result.setCreatetime(processData(rs.getString("CREATETIME")));
			result.setUpdatetime(processData(rs.getString("UPDATETIME")));
			result.setType(processData(rs.getString("SUBS_TYPE")));

		}
		
		rs.close();
		rs = null ;
		
		String serviceId = null;
		
		sql = "SELECT A.SERVICEID FROM CRM_SUBSCRIPTION A WHERE A.SUBS_ID_TAXID = '"+id+"'";
		rs = st.executeQuery(sql);
		
		while(rs.next()){
			serviceId = rs.getString("SERVICEID");
			result.setServiceId(serviceId);
		}
		
		rs.close();
		rs = null ;
		
		sql = "SELECT A.FOLLOWMENUMBER FROM FOLLOWMEDATA A where A.SERVICEID = '"+serviceId+"' ";
		rs = st2.executeQuery(sql);
		
		while(rs.next()){
			result.setChtMsisdn(rs.getString("FOLLOWMENUMBER"));
		}
		
		rs.close();
		rs = null ;
		
		sql = "SELECT A.SERVICECODE FROM SERVICE A WHERE A.SERVICEID = '"+serviceId+"'";
		rs = st2.executeQuery(sql);
		
		while(rs.next()){
			result.setS2tMsisdn(rs.getString("SERVICECODE"));
		}
		
		
		rs.close();
		st.close();
		closeConnection();
		return result;
	}
	
	public List<SMS> querySMS(String S2TMSISDN,String CHTMSISDN,String startDate,String endDate) throws SQLException{
		
		
		
		String dateCondiyion = "";
		if(startDate!=null && endDate !=null && !"".equals(startDate) && !"".equals(endDate) && !"NULL".equalsIgnoreCase(startDate) && !"NULL".equalsIgnoreCase(endDate)){
			dateCondiyion = "WHERE SENDTIME >= to_date("+startDate+",'yyyyMMddhh24miss') "
					+ "AND SENDTIME <= to_date("+endDate+",'yyyyMMddhh24miss')";
		}
		
		List<SMS> result = new ArrayList<SMS>();
		
		Statement st = conn.createStatement();
		ResultSet rs = null;

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
		st.close();
		closeConnection();
		return result;
		
	}
	
	
	
	
	
	
	//else tool
	
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
