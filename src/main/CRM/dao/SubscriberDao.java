package main.CRM.dao;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import main.CRM.bean.AddonService;
import main.CRM.bean.Subscriber;

@Repository
public class SubscriberDao extends CRMBaseDao{

	public SubscriberDao() throws Exception {
		super();
	}

	//select ID and data
	public List<Subscriber> queryListById(String id) throws Exception{
		List<Subscriber> result = new ArrayList<Subscriber>();
		try{
			result = queryList("AND A.SUBS_ID_TAXID='"+id+"' ");
		}finally{
			//closeConnection();
		}
		return result;
	}
	
	public List<Subscriber> queryListByName(String name) throws Exception{
		List<Subscriber> result = new ArrayList<Subscriber>();
		try {
			System.out.println(name+";"+new String(name.getBytes("BIG5"),"ISO-8859-1")+";"+new String("迷稱".getBytes("BIG5"),"ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			result = queryList("AND A.SUBS_NAME Like '%"+processEncodeData(name,"BIG5","ISO-8859-1")+"%' ");
		}finally{
			//closeConnection();
		}
		return result;
	}
	
	public List<Subscriber> queryListByVLN(String VLN) throws Exception{
		List<Subscriber> result = new ArrayList<Subscriber>();
		
		ResultSet rs = null;
		Statement st = null;
		
		String sql = "SELECT A.SERVICEID, A.VLN "
				+ "FROM VLNNUMBER A "
				+ "WHERE A.VLN = '"+VLN+"' ";
		String serviceId = null;

		try{

			st = getConn1().createStatement();
			System.out.println("query List:"+sql);
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				serviceId = rs.getString("SERVICEID");
			}
			
			if(serviceId != null)
				result = queryList("AND B.SERVICEID = '"+serviceId+"' ");

		}finally{
			try {
				if(st!=null)
					st.close();
				if(rs!=null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			//closeConnection();
		}
		
		
		return result;
	}
	
	public List<Subscriber> queryListByS2tMisidn(String s2tMsisdn) throws Exception{
		List<Subscriber> result = new ArrayList<Subscriber>();

		try{
			Subscriber s = queryServiceIdbyS2tMsisdn(s2tMsisdn);
			result = queryList("AND B.SERVICEID = '"+s.getServiceId()+"' ");
			
			if(result.size()==0){
				result.add(s);
			}
		}finally{
			//closeConnection();
		}
		return result;
	}
	
	public List<Subscriber> queryListByChtMsisdn(String chtMsisdn) throws Exception{
		List<Subscriber> result = new ArrayList<Subscriber>();
		
		try{
			Subscriber s = queryServiceIdbyChtMsisdn(chtMsisdn);
			result = queryList("AND B.SERVICEID = '"+s.getServiceId()+"' ");
			
			if(result.size()==0){
				result.add(s);
			}
		}finally{
			//closeConnection();
		}
		return result;
	}
	
	public List<Subscriber> queryListByMainMsisdn(String mainMsisdn) throws Exception{
		List<Subscriber> result = new ArrayList<Subscriber>();
		
		ResultSet rs = null;
		Statement st = null;
		
		String sql = "SELECT A.SERVICEID,A.VALUE "
				+ "FROM PARAMETERVALUE A "
				+ "WHERE A.VALUE = '"+mainMsisdn+"' ";
		String serviceId = null;

		try{

			st = getConn1().createStatement();
			System.out.println("query List:"+sql);
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				serviceId = rs.getString("SERVICEID");
			}
			
			if(serviceId != null)
				result = queryList("AND B.SERVICEID = '"+serviceId+"' ");

		}finally{
			try {
				if(st!=null)
					st.close();
				if(rs!=null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			//closeConnection();
		}
		
		
		return result;
	}
	
	
	
	private List<Subscriber> queryList(String condition) throws Exception{
		List<Subscriber> result = new ArrayList<Subscriber>();

		String sql = "SELECT A.SUBS_ID_TAXID ID,A.SUBS_NAME,A.SUBS_PERMANENT_ADDRESS, "
				+ "(CASE C.STATUS WHEN '1' THEN 'normal'  WHEN '3' THEN 'suspended' WHEN '4' THEN 'terminated' WHEN '10' THEN 'waiting' ELSE 'else' END) STAUS, "
				+ "B. SERVICEID,C. SERVICECODE S2TMSISDN,C.PRICEPLANID,D.FOLLOWMENUMBER CHTMSISDN,C.DATEACTIVATED,C.DATECANCELED  "
				+ "FROM CRM_SUBSCRIBERS A,CRM_SUBSCRIPTION B ,SERVICE C,FOLLOWMEDATA D "
				+ "WHERE A.SEQ = B.SEQ(+) "
				+ "AND B.SERVICEID= C.SERVICEID(+) "
				+ "AND B.SERVICEID = D.SERVICEID(+) "
				+ "AND D.FOLLOWMENUMBER(+)  LIKE '886%' "
				+ " "+condition+" " ;
		
		Statement st = null ;
		ResultSet rs = null ;
		
		try{
			st = getConn1().createStatement();
			System.out.println("query List:"+sql);
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				Subscriber s = new Subscriber();
				s.setIdTaxid(rs.getString("ID"));
				s.setName(processEncodeData(rs.getString("SUBS_NAME"),"ISO-8859-1","BIG5"));
				s.setServiceId(rs.getString("SERVICEID"));
				s.setS2tMsisdn(rs.getString("S2TMSISDN"));
				s.setChtMsisdn(rs.getString("CHTMSISDN"));
				s.setPrivePlanId(rs.getString("PRICEPLANID"));
				s.setStatus(rs.getString("STAUS"));
				
				s.setActivatedDate(rs.getString("DATEACTIVATED"));
				s.setCanceledDate(rs.getString("DATECANCELED"));
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
	
	public List<String> queryServiceIdList(String id) throws Exception{
		List<String> result = new ArrayList<String>();
		
		String sql = "SELECT A.SERVICEID,A.SEQ FROM CRM_SUBSCRIPTION A WHERE A.SEQ = '"+id+"'";
		
		Statement st = null ;
		ResultSet rs = null ;
		
		try{
			st = getConn1().createStatement();
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
			//closeConnection();
		}
		return result;
	}
	
	//select all data
	public Subscriber queryDataById(String id) throws Exception{
		Subscriber result = new Subscriber(); 
		//Main by customer data
		String sql = "SELECT A.SUBS_ID_TAXID ID,A.SUBS_NAME,A.SUBS_BIRTHDAY,A.SUBS_PHONE,A.SUBS_EMAIL,A.SEQ,F.HOMEIMSI, "
				+ "(CASE C.STATUS WHEN '1' THEN 'normal'  WHEN '3' THEN 'suspended' WHEN '4' THEN 'terminated' WHEN '10' THEN 'waiting' ELSE 'else' END) STAUS, "
				+ "A.SUBS_PERMANENT_ADDRESS,A.SUBS_BILLING_ADDRESS,A.AGENCY_ID,A.REMARK,C.DATEACTIVATED,C.DATECANCELED, "
				+ "to_char(A.CREATETIME,'yyy/MM/dd hh24:mi:ss') CREATETIME,to_char(A.UPDATETIME,'yyy/MM/dd hh24:mi:ss') UPDATETIME, "
				+ "A.SUBS_TYPE,B. SERVICEID,C. SERVICECODE S2TMSISDN,C.PRICEPLANID,F.IMSI,D.FOLLOWMENUMBER CHTMSISDN,E.CHAIRMAN,E.CHAIRMAN_ID "
				+ "FROM CRM_SUBSCRIBERS A,CRM_SUBSCRIPTION B ,SERVICE C,FOLLOWMEDATA  D,CRM_CHAIRMAN E,IMSI F "
				+ "WHERE A.SEQ = B.SEQ "
				+ "AND B.SERVICEID = C.SERVICEID "
				+ "AND C.SERVICEID= D.SERVICEID(+) "
				+ "AND A.SEQ = E.SEQ(+) AND C.SERVICEID = F.SERVICEID(+) "
				+ "AND D.FOLLOWMENUMBER LIKE '886%' "
				+ "AND A.SUBS_ID_TAXID='"+id+"' ";
		
		Statement st = null;
		Statement st2 = null;
		ResultSet rs = null;
		
		try {
			
			st = getConn1().createStatement();
			st2 = getConn2().createStatement();
			System.out.println("query data by id:"+sql);
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
				
				result.setServiceId(rs.getString("SERVICEID"));
				result.setS2tMsisdn(rs.getString("S2TMSISDN"));
				result.setS2tIMSI(rs.getString("IMSI"));
				result.setChtMsisdn(rs.getString("CHTMSISDN"));
				result.setPrivePlanId(rs.getString("PRICEPLANID"));
				
				result.setChair(processEncodeData(rs.getString("CHAIRMAN"),"ISO-8859-1","BIG5"));
				result.setChairID(rs.getString("CHAIRMAN_ID"));
				result.setStatus(rs.getString("STAUS"));
				
				result.setActivatedDate(rs.getString("DATEACTIVATED"));
				result.setCanceledDate(rs.getString("DATECANCELED"));
				result.setSeq(rs.getString("SEQ"));
				
				result.setHomeIMSI(rs.getString("HOMEIMSI"));
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
			//closeConnection();
		}
		return result;
	}
	
	public Subscriber queryDataByServiceId(String id) throws Exception{
		Subscriber result = new Subscriber(); 

		//main by serviceid s2tnumber followNumber
		String sql = "SELECT A.SUBS_ID_TAXID ID,A.SUBS_NAME,A.SUBS_BIRTHDAY,A.SUBS_PHONE,A.SUBS_EMAIL,A.SEQ,F.HOMEIMSI, "
				+ "(CASE C.STATUS WHEN '1' THEN 'normal'  WHEN '3' THEN 'suspended' WHEN '4' THEN 'terminated' WHEN '10' THEN 'waiting' ELSE 'else' END) STAUS, "
				+ "A.SUBS_PERMANENT_ADDRESS,A.SUBS_BILLING_ADDRESS,A.AGENCY_ID,A.REMARK,C.DATEACTIVATED,C.DATECANCELED, "
				+ "to_char(A.CREATETIME,'yyy/MM/dd hh24:mi:ss') CREATETIME,to_char(A.UPDATETIME,'yyy/MM/dd hh24:mi:ss') UPDATETIME, "
				+ "A.SUBS_TYPE,C. SERVICEID,C. SERVICECODE S2TMSISDN,C.PRICEPLANID,F.IMSI,D.FOLLOWMENUMBER CHTMSISDN,E.CHAIRMAN,E.CHAIRMAN_ID "
				+ "FROM CRM_SUBSCRIBERS A,CRM_SUBSCRIPTION B ,SERVICE C,FOLLOWMEDATA  D,CRM_CHAIRMAN E,IMSI F "
				+ "WHERE A.SEQ(+) = B.SEQ "
				+ "AND B.SERVICEID(+) = C.SERVICEID "
				+ "AND A.SEQ = E.SEQ(+) "
				+ "AND C.SERVICEID= D.SERVICEID(+) "
				+ "AND D.FOLLOWMENUMBER(+) LIKE '886%'  "
				+ "AND C.SERVICEID = F.SERVICEID(+) "
				+ "AND C.SERVICEID='"+id+"' "; 

				
		Statement st = null;
		Statement st2 = null;
		ResultSet rs = null;
		
		try {
			
			st = getConn1().createStatement();
			st2 = getConn2().createStatement();
			System.out.println("query data by service id:"+sql);
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
				
				result.setServiceId(rs.getString("SERVICEID"));
				result.setS2tMsisdn(rs.getString("S2TMSISDN"));
				result.setS2tIMSI(rs.getString("IMSI"));
				result.setChtMsisdn(rs.getString("CHTMSISDN"));
				result.setPrivePlanId(rs.getString("PRICEPLANID"));
				
				result.setChair(processEncodeData(rs.getString("CHAIRMAN"),"ISO-8859-1","BIG5"));
				result.setChairID(rs.getString("CHAIRMAN_ID"));
				result.setStatus(rs.getString("STAUS"));
				
				result.setActivatedDate(rs.getString("DATEACTIVATED"));
				result.setCanceledDate(rs.getString("DATECANCELED"));
				result.setSeq(rs.getString("SEQ"));
				
				result.setHomeIMSI(rs.getString("HOMEIMSI"));
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
			//closeConnection();
		}
		return result;
	}
	
	public boolean insertSubscriber(Subscriber s) throws Exception{
		boolean result = false;
		
		Statement st = null;
		
		try {

			st = getConn1().createStatement();
			
			String sql3 = "SELECT CRM_SEQ.NEXTVAL SEQ "
					+ "FROM DUAL ";
			System.out.println("sql3:"+sql3);
			ResultSet rs = st.executeQuery(sql3);
			
			while(rs.next()){
				s.setSeq(rs.getString("SEQ"));
			}

			String sql = "INSERT INTO "
					+ "CRM_SUBSCRIBERS("
					+ "SUBS_NAME, SUBS_BIRTHDAY, SUBS_ID_TAXID, SUBS_PHONE, SUBS_EMAIL,"
					+ "SUBS_PERMANENT_ADDRESS,SUBS_BILLING_ADDRESS,AGENCY_ID,REMARK,"
					+ "CREATETIME,SUBS_TYPE,SEQ) "
					+ "VALUES("
					+ "'"+s.getName()+"','"+s.getBirthday()+"','"+s.getIdTaxid()+"','"+s.getPhone()+"','"+s.getEmail()+"',"
					+ "'"+s.getPermanentAddress()+"','"+s.getBillingAddress()+"','"+s.getAgency()+"','"+s.getRemark()+"',"
					+ "sysdate,'"+s.getType()+"','"+s.getSeq()+"') ";
			System.out.println("sql:"+sql);
			int eRow = st.executeUpdate(new String(sql.getBytes("BIG5"),"ISO-8859-1"));

			if(eRow!=1)
				throw new Exception("Update data fail! InsertSubscriber "+eRow);
			updateSubscription(s);
			result = true;
		}finally{
			try {
				if(st!=null)
					st.close();
			} catch (Exception e) {
			}
		}	
		return result;
	}
	
	public boolean insertSubscription(Subscriber s) throws Exception{
		
		Statement st = null;
		boolean result = false;
		try {

			st = getConn1().createStatement();
			
			String sql = "INSERT INTO "
					+ "CRM_SUBSCRIPTION("
					+ "SERVICEID,SEQ,CREATETIME) "
					+ "VALUES("
					+ "'"+s.getServiceId()+"','"+s.getSeq()+"',sysdate) ";
			
			System.out.println("sql:"+sql);
			int eRow = st.executeUpdate(new String(sql.getBytes("BIG5"),"ISO-8859-1"));
			
			if(eRow!=1)
				throw new Exception("Update data fail! inserted Subscription "+eRow);
			
			result = true;
		}finally{
			try {
				if(st!=null)
					st.close();
			} catch (Exception e) {
			}
		}
		return result;	
		
		
	}
	
	public boolean updateSubscription(Subscriber s) throws Exception{
		
		
		boolean result = false;
		
		Statement st = null;
		try {

			st = getConn1().createStatement();

			String sql = "UPDATE CRM_SUBSCRIPTION A "
					+ "SET A.SEQ = '"+s.getSeq()+"',A.UPDATETIME = SYSDATE "
					+ "WHERE A.SERVICEID = '"+s.getServiceId()+"'";
			
			System.out.println("sql:"+sql);
			int eRow = st.executeUpdate(sql);
			
			if(eRow>1)
				throw new Exception("Update data fail! updated Subscription "+eRow);
			else if(eRow==0)
				insertSubscription(s);
			
			if("E".equalsIgnoreCase(s.getType())){
				updateChairMain(s);
			}else if("P".equalsIgnoreCase(s.getType())){
				deleteChairMain(s);
			}
			
			
			result = true;
		}finally{
			try {
				if(st!=null)
					st.close();
			} catch (Exception e) {
			}
		}
		return result;	
		
	}
	
	public int insertChairMain(Subscriber s) throws Exception{
		int result = 0;
		
		
		String sql = "INSERT INTO CRM_CHAIRMAN(SEQ,CHAIRMAN,CHAIRMAN_ID,CREATETIME) "
				+ "VALUES('"+s.getSeq()+"','"+s.getChair()+"','"+s.getChairID()+"',sysdate)";
		
		
		Statement st = null;
		
		try {
			st = getConn1().createStatement();
			System.out.println("sql:"+sql);
			result = st.executeUpdate(new String(sql.getBytes("BIG5"),"ISO-8859-1"));

		}finally{
			try {
				if(st!=null)
					st.close();
			} catch (Exception e) {
			}
		}	
		return result;
	}
	
	public boolean updateChairMain(Subscriber s) throws Exception{
		boolean result = false;
		
		
		String sql = "UPDATE CRM_CHAIRMAN A "
				+ "SET A.CHAIRMAN = '"+s.getChair()+"', A.CHAIRMAN_ID = '"+s.getChairID()+"' "
				+ "WHERE A.SEQ = '"+s.getSeq()+"'";
		
		
		Statement st = null;
		
		try {
			st = getConn1().createStatement();
			System.out.println("sql:"+sql);
			int eRow = st.executeUpdate(new String(sql.getBytes("BIG5"),"ISO-8859-1"));
			if(eRow>1)
				throw new Exception("Update data fail! updated ChairMain "+eRow);
			else if(eRow==0)
				insertChairMain(s);
			
			result = true;
		}finally{
			try {
				if(st!=null)
					st.close();
			} catch (Exception e) {
			}
		}	
		return result;
	}
	
	public boolean deleteChairMain(Subscriber s) throws Exception{
		boolean result = false;
		
		
		String sql = "DELETE CRM_CHAIRMAN A "
				+ "WHERE A.SEQ = '"+s.getSeq()+"' ";
		
		
		Statement st = null;
		
		try {
			st = getConn1().createStatement();
			System.out.println("sql:"+sql);
			int eRow = st.executeUpdate(sql);
			result = true;

		}finally{
			try {
				if(st!=null)
					st.close();
			} catch (Exception e) {
			}
		}	
		return result;
	}
	
	public boolean updateSubscriber(Subscriber s) throws Exception{
		boolean result = false;
		String sql = "UPDATE CRM_SUBSCRIBERS A "
				+ "SET A.SUBS_NAME='"+s.getName()+"',A.SUBS_BIRTHDAY='"+s.getBirthday()+"', "
				+ "A.SUBS_PHONE='"+s.getPhone()+"',A.SUBS_EMAIL='"+s.getEmail()+"', "
				+ "A.SUBS_PERMANENT_ADDRESS='"+s.getPermanentAddress()+"',A.SUBS_BILLING_ADDRESS='"+s.getBillingAddress()+"', "
				+ "A.AGENCY_ID='"+s.getAgency()+"',A.REMARK='"+s.getRemark()+"',A.UPDATETIME=SYSDATE ,A.SUBS_TYPE = '"+s.getType()+"' "
				+ "WHERE A.SEQ ='"+s.getSeq()+"'";
	
		Statement st = null;
		
		try {
			conn.setAutoCommit(false);
			
			st = getConn1().createStatement();
			System.out.println("sql:"+sql);
			int eRow = st.executeUpdate(new String(sql.getBytes("BIG5"),"ISO-8859-1"));
			
			if(eRow>1)
				throw new Exception("Update data fail! updated Subscriber "+eRow);
			if(eRow == 0)
				result = insertSubscriber(s);
			
			
			updateSubscription(s);
			
			result = true;
			
			conn.commit();
		}finally{
			try {
				if(st!=null)
					st.close();
			} catch (Exception e) {
			}
			//closeConnection();
		}	
		
		return result;
	}
	
	public List<String> queryVLN(String serviceId) throws Exception{
		List<String> result = new ArrayList<String>(); 

		String sql = "SELECT A.FOLLOWMENUMBER "
				+ "FROM FOLLOWMEDATA A "
				+ "WHERE A.SERVICEID = '"+serviceId+"'";
				
		Statement st = null;
		ResultSet rs = null;
		
		try {
			
			st = getConn1().createStatement();
			System.out.println("sql:"+sql);
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				result.add(rs.getString("FOLLOWMENUMBER"));
			}
			
			
		} finally{
			try {
				if(rs!=null)
					rs.close();
				if(st!=null)
					st.close();
			} catch (Exception e) {
			}
			//closeConnection();
		}
		return result;
	}
	
	public List<AddonService> queryAddonService(String serviceId) throws Exception{
		List<AddonService> result = new ArrayList<AddonService>(); 

		String sql = "SELECT A.SERVICEID,A.SERVICECODE,A.STATUS,A.STARTDATE,A.ENDDATE "
				+ "FROM ADDONSERVICE_N A "
				+ "WHERE A.SERVICEID = '"+serviceId+"' "
				+ "ORDER BY A.STARTDATE DESC";
				
		Statement st = null;
		ResultSet rs = null;
		
		try {
			
			st = getConn1().createStatement();
			System.out.println("sql:"+sql);
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				AddonService a = new AddonService();
				a.setServiceId(rs.getString("SERVICEID"));
				a.setServiceCode(rs.getString("SERVICECODE"));
				a.setStatus(rs.getString("STATUS"));
				a.setStartDate(rs.getString("STARTDATE"));
				a.setEndDate(rs.getString("ENDDATE"));
				result.add(a);
			}
		} finally{
			try {
				if(rs!=null)
					rs.close();
				if(st!=null)
					st.close();
			} catch (Exception e) {
			}
			//closeConnection();
		}
		return result;
	}
	
	public String getGPRSStatus(String msisdn) throws Exception{
		String result = null;
		String sql = "SELECT nvl(PDPSUBSID,0) as status "
				+ "FROM BASICPROFILE "
				+ "WHERE MSISDN = '"+msisdn+"'";
				
		Statement st = null;
		ResultSet rs = null;
		
		try {
			
			st = getConn1().createStatement();
			System.out.println("sql:"+sql);
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				result = rs.getString("status");
			}
		} finally{
			try {
				if(rs!=null)
					rs.close();
				if(st!=null)
					st.close();
			} catch (Exception e) {
			}
			//closeConnection();
		}
		return result;
	}
	//else tool
	
	
}
