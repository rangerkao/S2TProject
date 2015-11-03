package main.CRM.dao;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import main.CRM.bean.SMS;
import main.CRM.bean.Subscriber;
import main.common.dao.BaseDao;

public class SubscriberDao extends CRMBaseDao{

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
	
	public List<Subscriber> queryListByS2tMisidn(String s2tMsisdn) throws SQLException{
		List<Subscriber> result = new ArrayList<Subscriber>();
		
		Statement st = null;
		Statement st2 = null;
		ResultSet rs = null ;
		String sql = null;
		String serviceId = queryServiceIdbyS2tMsisdn(s2tMsisdn);

		try {
			st = conn.createStatement();
			st2 = conn2.createStatement();
			
			sql = "SELECT A.SUBS_ID_TAXID FROM CRM_SUBSCRIPTION A WHERE A.SERVICEID = '"+serviceId+"'";
			System.out.println("sql:"+sql);
			rs = st.executeQuery(sql);
			
			String id = null;
			
			while(rs.next()){
				id = rs.getString("SUBS_ID_TAXID");
			}
			
			result = queryList("WHERE A.SUBS_ID_TAXID = '"+id+"' ");
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
	
	public List<Subscriber> queryListByChtMsisdn(String chtMsisdn) throws SQLException{
		List<Subscriber> result = new ArrayList<Subscriber>();
		
		Statement st = null;
		Statement st2 = null;
		ResultSet rs = null ;
		String sql = null;
		
		String serviceId = queryServiceIdbyChtMsisdn(chtMsisdn);
		try{
			st = conn.createStatement();
			st2 = conn2.createStatement();
		
			sql = "SELECT A.SUBS_ID_TAXID FROM CRM_SUBSCRIPTION A WHERE A.SERVICEID = '"+serviceId+"'";
			System.out.println("sql:"+sql);
			rs = st.executeQuery(sql);
			
			String id = null;
			while(rs.next()){
				id = rs.getString("SUBS_ID_TAXID");
			}
			result = queryList("WHERE A.SUBS_ID_TAXID = '"+id+"' ");
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
	
	private List<Subscriber> queryList(String condition) throws SQLException{
		List<Subscriber> result = new ArrayList<Subscriber>();
		
		String sql = "SELECT A.SUBS_ID_TAXID ID,A.SUBS_NAME,A.SUBS_PERMANENT_ADDRESS FROM CRM_SUBSCRIBERS A "+condition; 
		
		Statement st = null ;
		ResultSet rs = null ;
		
		try{
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				Subscriber s = new Subscriber();
				s.setIdTaxid(rs.getString("ID"));
				s.setName(processEncodeData(rs.getString("SUBS_NAME"),"ISO-8859-1","BIG5"));
				s.setPermanentAddress(processEncodeData(rs.getString("SUBS_PERMANENT_ADDRESS"),"ISO-8859-1","BIG5"));
				result.add(s);
			}		
		}finally{
			try {
				if(rs!=null)
					rs.close();
				if(st!=null)
					st.close();
			} catch (Exception e) {
			}
		}
		return result;
	}
	
	public List<String> queryServiceIdList(String id) throws SQLException{
		List<String> result = new ArrayList<String>();
		
		String sql = "SELECT A.SERVICEID,A.SUBS_ID_TAXID FROM CRM_SUBSCRIPTION A WHERE A.SUBS_ID_TAXID = '"+id+"'";
		
		Statement st = null ;
		ResultSet rs = null ;
		
		try{
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				String sId = rs.getString("SERVICEID");
				result.add(sId);
			}		
		}finally{
			try {
				if(rs!=null)
					rs.close();
				if(st!=null)
					st.close();
			} catch (Exception e) {
			}
		}
		return result;
	}
	
	//select all data
	public Subscriber queryDataById(String id) throws SQLException{
		Subscriber result = new Subscriber(); 
		
		String sql = "SELECT A.SUBS_ID_TAXID ID,A.SUBS_NAME,A.SUBS_BIRTHDAY,A.SUBS_PHONE,A.SUBS_EMAIL,A.SUBS_PERMANENT_ADDRESS,A.SUBS_BILLING_ADDRESS,A.AGENCY_ID,A.REMARK, "
				+ "to_char(A.CREATETIME,'yyy/MM/dd hh24:mi:ss') CREATETIME,to_char(A.UPDATETIME,'yyy/MM/dd hh24:mi:ss') UPDATETIME,A.SUBS_TYPE "
				+ "FROM CRM_SUBSCRIBERS A WHERE A.SUBS_ID_TAXID='"+id+"' ";
		
		Statement st = null;
		Statement st2 = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.createStatement();
			st2 = conn2.createStatement();
			rs = st.executeQuery(sql);
			
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

			if(serviceId!=null){
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
			}
		} finally{
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
	
	public Subscriber queryDataByServiceId(String id) throws SQLException{
		Subscriber result = new Subscriber(); 
		
		String sql = "SELECT A.SUBS_ID_TAXID ID,A.SUBS_NAME,A.SUBS_BIRTHDAY,A.SUBS_PHONE,A.SUBS_EMAIL,A.SUBS_PERMANENT_ADDRESS,A.SUBS_BILLING_ADDRESS,A.AGENCY_ID,A.REMARK, "
				+ "to_char(A.CREATETIME,'yyy/MM/dd hh24:mi:ss') CREATETIME,to_char(A.UPDATETIME,'yyy/MM/dd hh24:mi:ss') UPDATETIME,A.SUBS_TYPE "
				+ "FROM CRM_SUBSCRIBERS A,CRM_SUBSCRIPTION B WHERE A.SUBS_ID_TAXID = B.SUBS_ID_TAXID AND B.SERVICEID='"+id+"' ";
		
		Statement st = null;
		Statement st2 = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.createStatement();
			st2 = conn2.createStatement();
			rs = st.executeQuery(sql);
			
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
			
			String serviceId = id;
			result.setServiceId(serviceId);

			if(serviceId!=null){
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
			}
		} finally{
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
	//else tool
	
	
}
