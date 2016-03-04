package main.CRM.dao;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import main.CRM.bean.AddonService;
import main.CRM.bean.PricePlanID;
import main.CRM.bean.Subscriber;

@Repository
public class SubscriberDao extends CRMBaseDao{

	public SubscriberDao() throws Exception {
		super();
	}

	//select ID and data
	public List<Subscriber> queryListById(String id) throws Exception{
		List<Subscriber> result = new ArrayList<Subscriber>();
		Set<String> serviceids = new HashSet<String>();
		//CRM DB
		String sql1 ="SELECT  A.SUBS_ID_TAXID ID,A.SUBS_NAME,A.SUBS_PERMANENT_ADDRESS,B.SERVICEID "
				+ "FROM  CRM_DB.CRM_SUBSCRIBERS A LEFT JOIN CRM_DB.CRM_SUBSCRIPTION B ON A.SEQ =B.SEQ "
				+ "WHERE A.SUBS_ID_TAXID = '"+id+"' ";
		
		Statement st = null ;
		ResultSet rs = null ;
		
		try{
			st = getConn3().createStatement();
			System.out.println("query serviceids:"+sql1);
			rs = st.executeQuery(sql1);
			
			while(rs.next()){
				serviceids.add(rs.getString("SERVICEID"));
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
		
		for(String serviceid:serviceids){
			if(!"".equals(serviceid))
				result.add(queryList(serviceid));
		}
	
		return result;
	}
	
	public List<Subscriber> queryListByName(String name) throws Exception{
		List<Subscriber> result = new ArrayList<Subscriber>();
		Set<String> serviceids = new HashSet<String>();
		//CRM DB
		String sql1 ="SELECT  A.SUBS_ID_TAXID ID,A.SUBS_NAME,A.SUBS_PERMANENT_ADDRESS,B.SERVICEID "
				+ "FROM  CRM_DB.CRM_SUBSCRIBERS A LEFT JOIN CRM_DB.CRM_SUBSCRIPTION B ON A.SEQ =B.SEQ "
				+ "WHERE A.SUBS_NAME LIKE '%"+name+"%' ";
		
		Statement st = null ;
		ResultSet rs = null ;
		
		try{
			st = getConn3().createStatement();
			System.out.println("query serviceids:"+sql1);
			rs = st.executeQuery(sql1);
			
			while(rs.next()){
				serviceids.add(rs.getString("SERVICEID"));
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
		
		for(String serviceid:serviceids){
			if(!"".equals(serviceid))
				result.add(queryList(serviceid));
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
			
			if(serviceId != null &&!"".equals(serviceId))
				result.add(queryList(serviceId));

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
			if(s.getServiceId() != null &&!"".equals(s.getServiceId()))
				result.add(queryList(s.getServiceId()));
			
			/*if(result.size()==0){
				result.add(s);
			}*/
		}finally{
			//closeConnection();
		}
		return result;
	}
	
	public List<Subscriber> queryListByS2tIMSI(String s2tIMSI) throws Exception{
		List<Subscriber> result = new ArrayList<Subscriber>();

		try{
			Subscriber s = queryServiceIdbyS2tImsi(s2tIMSI);
			if(s.getServiceId() != null &&!"".equals(s.getServiceId()))
				result.add(queryList(s.getServiceId()));
			
			/*if(result.size()==0){
				result.add(s);
			}*/
		}finally{
			//closeConnection();
		}
		return result;
	}
	
	public List<Subscriber> queryListByChtMsisdn(String chtMsisdn) throws Exception{
		List<Subscriber> result = new ArrayList<Subscriber>();
		
		try{
			Subscriber s = queryServiceIdbyChtMsisdn(chtMsisdn);
			if(s.getServiceId() != null &&!"".equals(s.getServiceId()))
				result.add(queryList(s.getServiceId()));
			
			/*if(result.size()==0){
				result.add(s);
			}*/
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
			
			if(serviceId != null &&!"".equals(serviceId))
				result.add(queryList(serviceId));

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
	
	
	private Subscriber queryList(String serviceid) throws Exception{
		Subscriber result = new Subscriber();
		result.setServiceId(serviceid);
		/*String sql = "SELECT A.SUBS_ID_TAXID ID,A.SUBS_NAME,A.SUBS_PERMANENT_ADDRESS, "
				+ "(CASE C.STATUS WHEN '1' THEN 'normal'  WHEN '3' THEN 'suspended' WHEN '4' THEN 'terminated' WHEN '10' THEN 'waiting' ELSE 'else' END) STAUS, "
				+ "B. SERVICEID,C. SERVICECODE S2TMSISDN,C.PRICEPLANID,D.FOLLOWMENUMBER CHTMSISDN,C.DATEACTIVATED,C.DATECANCELED  "
				+ "FROM CRM_SUBSCRIBERS A,CRM_SUBSCRIPTION B ,SERVICE C,FOLLOWMEDATA D "
				+ "WHERE A.SEQ = B.SEQ(+) "
				+ "AND B.SERVICEID= C.SERVICEID(+) "
				+ "AND B.SERVICEID = D.SERVICEID(+) "
				+ "AND D.FOLLOWMENUMBER(+)  LIKE '886%' "
				+ " "+condition+" " ;*/
		
		//CRM DB
		String sql1 ="SELECT  A.SUBS_ID_TAXID ID,A.SUBS_NAME,A.SUBS_PERMANENT_ADDRESS,B.SERVICEID "
				+ "FROM  CRM_DB.CRM_SUBSCRIBERS A LEFT JOIN CRM_DB.CRM_SUBSCRIPTION B ON A.SEQ =B.SEQ "
				+ "WHERE B.SERVICEID = "+serviceid+" ";
		//S2T DB
		String sql2 ="SELECT C.SERVICEID, "
				+ "			(CASE C.STATUS WHEN '1' THEN 'normal'  "
				+ "							WHEN '3' THEN 'suspended' "
				+ "							WHEN '4' THEN 'terminated' "
				+ "							WHEN '10' THEN 'waiting' "
				+ "							ELSE 'else' END) STAUS, "
				+ "			C. SERVICECODE S2TMSISDN,C.PRICEPLANID,D.FOLLOWMENUMBER CHTMSISDN,C.DATEACTIVATED,C.DATECANCELED "
				+ "FROM SERVICE C,FOLLOWMEDATA D "
				+ "WHERE  C.SERVICEID = D.SERVICEID(+) "
				+ "AND D.FOLLOWMENUMBER(+)  LIKE '886%' "
				+ "AND C.SERVICEID = "+serviceid+" ";;
		
		Statement st = null ;
		ResultSet rs = null ;
		
		try{
			st = getConn3().createStatement();
			System.out.println("query List:"+sql1);
			rs = st.executeQuery(sql1);
			
			while(rs.next()){

				result.setIdTaxid(rs.getString("ID"));
				result.setName(rs.getString("SUBS_NAME"));
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
		
		try{
			st = getConn1().createStatement();
			System.out.println("query List:"+sql2);
			rs = st.executeQuery(sql2);
			
			while(rs.next()){
				result.setServiceId(rs.getString("SERVICEID"));
				result.setS2tMsisdn(rs.getString("S2TMSISDN"));
				result.setChtMsisdn(rs.getString("CHTMSISDN"));
				result.setPrivePlanId(queryPricePlanId(rs.getString("PRICEPLANID")));
				result.setStatus(rs.getString("STAUS"));
				
				result.setActivatedDate(rs.getString("DATEACTIVATED"));
				result.setCanceledDate(rs.getString("DATECANCELED"));
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
	
	//get customer data
	public Subscriber queryDataById(String id) throws Exception{
		Subscriber result = new Subscriber(); 
		//Main by customer data
		/*String sql = "SELECT A.SUBS_ID_TAXID ID,A.SUBS_NAME,A.SUBS_BIRTHDAY,A.SUBS_PHONE,A.SUBS_EMAIL,A.SEQ,F.HOMEIMSI, "
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
				+ "AND A.SUBS_ID_TAXID='"+id+"' ";*/
		
		
		String sql1="SELECT A.SUBS_ID_TAXID ID,A.SUBS_NAME,A.SUBS_BIRTHDAY,A.SUBS_PHONE,A.SUBS_EMAIL,A.SEQ, "
				+ "A.SUBS_PERMANENT_ADDRESS,A.SUBS_BILLING_ADDRESS,A.AGENCY_ID,A.REMARK, "
				+ "DATE_FORMAT(A.CREATETIME,'%Y/%m/%d %H:%m:%s') CREATETIME,DATE_FORMAT(A.UPDATETIME,'%Y/%m/%d %H:%m:%s') UPDATETIME, "
				+ "A.SUBS_TYPE,B. SERVICEID,E.CHAIRMAN,E.CHAIRMAN_ID "
				+ "FROM CRM_DB.CRM_SUBSCRIBERS A left join CRM_DB.CRM_SUBSCRIPTION B on A.seq =B.seq left join CRM_DB.CRM_CHAIRMAN E on A.seq = E.seq "
				+ "WHERE A.SUBS_ID_TAXID = '"+id+"' ";
		
		Statement st = null;
		ResultSet rs = null;
		
		try {
			
			st = getConn3().createStatement();
			System.out.println("query data by id:"+sql1);
			rs = st.executeQuery(sql1);
			
			while(rs.next()){

				result.setName(rs.getString("SUBS_NAME"));
				result.setIdTaxid(processData(rs.getString("ID")));
				result.setBirthday(processData(rs.getString("SUBS_BIRTHDAY")));
				result.setPhone(processData(rs.getString("SUBS_PHONE")));
				result.setEmail(processData(rs.getString("SUBS_EMAIL")));
				result.setPermanentAddress(rs.getString("SUBS_PERMANENT_ADDRESS"));
				result.setBillingAddress(rs.getString("SUBS_BILLING_ADDRESS"));
				result.setAgency(rs.getString("AGENCY_ID"));
				result.setRemark(rs.getString("REMARK"));
				result.setCreatetime(processData(rs.getString("CREATETIME")));
				result.setUpdatetime(processData(rs.getString("UPDATETIME")));
				result.setType(processData(rs.getString("SUBS_TYPE")));
				
				result.setChair(rs.getString("CHAIRMAN"));
				result.setChairID(rs.getString("CHAIRMAN_ID"));
				
				result.setSeq(rs.getString("SEQ"));
				result.setServiceId(rs.getString("SERVICEID"));
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
	
	public Subscriber queryDataByServiceId(String id) throws Exception{
		Subscriber result = new Subscriber(); 
		result.setServiceId(id);
		//main by serviceid s2tnumber followNumber
		/*String sql = "SELECT A.SUBS_ID_TAXID ID,A.SUBS_NAME,A.SUBS_BIRTHDAY,A.SUBS_PHONE,A.SUBS_EMAIL,A.SEQ,F.HOMEIMSI, "
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
				+ "AND C.SERVICEID='"+id+"' "; */

		String sql1="SELECT A.SUBS_ID_TAXID ID,A.SUBS_NAME,A.SUBS_BIRTHDAY,A.SUBS_PHONE,A.SUBS_EMAIL,A.SEQ, "
				+ "A.SUBS_PERMANENT_ADDRESS,A.SUBS_BILLING_ADDRESS,A.AGENCY_ID,A.REMARK, "
				+ "DATE_FORMAT(A.CREATETIME,'%Y/%m/%d %H:%m:%s') CREATETIME,DATE_FORMAT(A.UPDATETIME,'%Y/%m/%d %H:%m:%s') UPDATETIME, "
				+ "A.SUBS_TYPE,B. SERVICEID,E.CHAIRMAN,E.CHAIRMAN_ID "
				+ "FROM CRM_DB.CRM_SUBSCRIBERS A left join CRM_DB.CRM_SUBSCRIPTION B on A.seq =B.seq left join CRM_DB.CRM_CHAIRMAN E on A.seq = E.seq "
				+ "WHERE B.SERVICEID = "+id+" ";
		
		String sql2="SELECT F.HOMEIMSI,C. SERVICEID, "
				+ "			(CASE C.STATUS "
				+ "					WHEN '1' THEN 'normal' "
				+ "					WHEN '3' THEN 'suspended' "
				+ "					WHEN '4' THEN 'terminated' "
				+ "					WHEN '10' THEN 'waiting' "
				+ "					ELSE 'else' END) STAUS, "
				+ "			C.DATEACTIVATED,C.DATECANCELED, "
				+ "			C. SERVICECODE S2TMSISDN,C.PRICEPLANID,F.IMSI,D.FOLLOWMENUMBER CHTMSISDN "
				+ "FROM SERVICE C,FOLLOWMEDATA  D,IMSI F "
				+ "WHERE C.SERVICEID= D.SERVICEID(+) "
				+ "AND C.SERVICEID = F.SERVICEID(+) "
				+ "AND D.FOLLOWMENUMBER(+) LIKE '886%' "
				+ "AND C.SERVICEID = "+id+" ";;
				
		Statement st = null;
		ResultSet rs = null;
		
		try {
			
			st = getConn3().createStatement();
			System.out.println("query data by service id:"+sql1);
			rs = st.executeQuery(sql1);
			
			while(rs.next()){

				result.setName(rs.getString("SUBS_NAME"));
				result.setIdTaxid(processData(rs.getString("ID")));
				result.setBirthday(processData(rs.getString("SUBS_BIRTHDAY")));
				result.setPhone(processData(rs.getString("SUBS_PHONE")));
				result.setEmail(processData(rs.getString("SUBS_EMAIL")));
				result.setPermanentAddress(rs.getString("SUBS_PERMANENT_ADDRESS"));
				result.setBillingAddress(rs.getString("SUBS_BILLING_ADDRESS"));
				result.setAgency(rs.getString("AGENCY_ID"));
				result.setRemark(rs.getString("REMARK"));
				result.setCreatetime(processData(rs.getString("CREATETIME")));
				result.setUpdatetime(processData(rs.getString("UPDATETIME")));
				result.setType(processData(rs.getString("SUBS_TYPE")));
	
				result.setChair(rs.getString("CHAIRMAN"));
				result.setChairID(rs.getString("CHAIRMAN_ID"));
	
				result.setSeq(rs.getString("SEQ"));

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
		
		
		try {
			
			st = getConn1().createStatement();
			System.out.println("query data by id:"+sql2);
			rs = st.executeQuery(sql2);
			
			while(rs.next()){

				result.setServiceId(rs.getString("SERVICEID"));
				result.setS2tMsisdn(rs.getString("S2TMSISDN"));
				result.setS2tIMSI(rs.getString("IMSI"));
				result.setChtMsisdn(rs.getString("CHTMSISDN"));
				result.setPrivePlanId(queryPricePlanId(rs.getString("PRICEPLANID")));
		
				result.setStatus(rs.getString("STAUS"));
				
				result.setActivatedDate(rs.getString("DATEACTIVATED"));
				result.setCanceledDate(rs.getString("DATECANCELED"));
				
				result.setHomeIMSI(rs.getString("HOMEIMSI"));
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
	
	
	
	public boolean insertSubscriber(Subscriber s) throws Exception{
		boolean result = false;
		
		Statement st = null;
		
		try {

			st = getConn3().createStatement();
			
			String sql3 = "SELECT MAX(A.seq)+1 SEQ "
					+ "FROM CRM_DB.CRM_SUBSCRIBERS A ";
			System.out.println("sql3:"+sql3);
			ResultSet rs = st.executeQuery(sql3);
			
			while(rs.next()){
				s.setSeq(rs.getString("SEQ"));
			}

			String sql = "INSERT INTO "
					+ "CRM_DB.CRM_SUBSCRIBERS("
					+ "SUBS_NAME, SUBS_BIRTHDAY, SUBS_ID_TAXID, SUBS_PHONE, SUBS_EMAIL,"
					+ "SUBS_PERMANENT_ADDRESS,SUBS_BILLING_ADDRESS,AGENCY_ID,REMARK,"
					+ "CREATETIME,SUBS_TYPE,SEQ) "
					+ "VALUES("
					+ "'"+s.getName()+"','"+s.getBirthday()+"','"+s.getIdTaxid()+"','"+s.getPhone()+"','"+s.getEmail()+"',"
					+ "'"+s.getPermanentAddress()+"','"+s.getBillingAddress()+"','"+s.getAgency()+"','"+s.getRemark()+"',"
					+ "now(),'"+s.getType()+"','"+s.getSeq()+"') ";
			System.out.println("sql:"+sql);
			int eRow = st.executeUpdate(sql);

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

			st = getConn3().createStatement();
			
			String sql = "INSERT INTO "
					+ "CRM_DB.CRM_SUBSCRIPTION("
					+ "SERVICEID,SEQ,CREATETIME) "
					+ "VALUES("
					+ "'"+s.getServiceId()+"','"+s.getSeq()+"',now()) ";
			
			System.out.println("sql:"+sql);
			int eRow = st.executeUpdate(sql);
			
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

			st = getConn3().createStatement();

			String sql = "UPDATE CRM_DB.CRM_SUBSCRIPTION A "
					+ "SET A.SEQ = '"+s.getSeq()+"',A.UPDATETIME = now() "
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
		
		
		String sql = "INSERT INTO CRM_DB.CRM_CHAIRMAN(SEQ,CHAIRMAN,CHAIRMAN_ID,CREATETIME) "
				+ "VALUES('"+s.getSeq()+"','"+s.getChair()+"','"+s.getChairID()+"',now())";
		
		
		Statement st = null;
		
		try {
			st = getConn3().createStatement();
			System.out.println("sql:"+sql);
			result = st.executeUpdate(sql);

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
		
		
		String sql = "UPDATE CRM_DB.CRM_CHAIRMAN A "
				+ "SET A.CHAIRMAN = '"+s.getChair()+"', A.CHAIRMAN_ID = '"+s.getChairID()+"' "
				+ "WHERE A.SEQ = '"+s.getSeq()+"'";
		
		
		Statement st = null;
		
		try {
			st = getConn3().createStatement();
			System.out.println("sql:"+sql);
			int eRow = st.executeUpdate(sql);
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
		
		
		String sql = "DELETE FROM CRM_DB.CRM_CHAIRMAN "
				+ "WHERE SEQ = '"+s.getSeq()+"' ";
		
		
		Statement st = null;
		
		try {
			st = getConn3().createStatement();
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
		String sql = "UPDATE CRM_DB.CRM_SUBSCRIBERS A "
				+ "SET A.SUBS_ID_TAXID='"+s.getIdTaxid()+"',A.SUBS_NAME='"+s.getName()+"',A.SUBS_BIRTHDAY='"+s.getBirthday()+"', "
				+ "A.SUBS_PHONE='"+s.getPhone()+"',A.SUBS_EMAIL='"+s.getEmail()+"', "
				+ "A.SUBS_PERMANENT_ADDRESS='"+s.getPermanentAddress()+"',A.SUBS_BILLING_ADDRESS='"+s.getBillingAddress()+"', "
				+ "A.AGENCY_ID='"+s.getAgency()+"',A.REMARK='"+s.getRemark()+"',A.UPDATETIME=now() ,A.SUBS_TYPE = '"+s.getType()+"' "
				+ "WHERE A.SEQ ='"+s.getSeq()+"'";
	
		Statement st = null;
		
		try {
			getConn3().setAutoCommit(false);
			
			st = getConn3().createStatement();
			System.out.println("sql:"+sql);
			int eRow = st.executeUpdate(sql);
			
			if(eRow>1)
				throw new Exception("Update data fail! updated Subscriber "+eRow);
			if(eRow == 0)
				result = insertSubscriber(s);
			
			
			updateSubscription(s);
			
			result = true;
			
			getConn3().commit();
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
	//TODO
	public List<String> queryVLN(String serviceId) throws Exception{
		List<String> result = new ArrayList<String>(); 

		String sql = "SELECT A.VLN "
				+ "FROM VLNNUMBER A "
				+ "WHERE A.SERVICEID = '"+serviceId+"'";
				
		Statement st = null;
		ResultSet rs = null;
		
		try {
			
			st = getConn1().createStatement();
			System.out.println("sql:"+sql);
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				result.add(rs.getString("VLN"));
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
