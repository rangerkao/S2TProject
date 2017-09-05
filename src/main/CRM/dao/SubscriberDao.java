package main.CRM.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import main.CRM.bean.AddonService;
import main.CRM.bean.Subscriber;
import main.CRM.bean.USPacket;

@Repository
public class SubscriberDao extends CRMBaseDao{

	public SubscriberDao() throws Exception {
		super();
	}

	/**
	 * 以CRM ID查詢客戶清單
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<Subscriber> queryListById(String id) throws Exception{
		List<Subscriber> result = new ArrayList<Subscriber>();
		Set<String> serviceids = new HashSet<String>();
		//CRM DB
		String sql1 ="SELECT  A.SUBS_ID_TAXID ID,A.SUBS_NAME,A.SUBS_PERMANENT_ADDRESS,B.SERVICEID "
				+ "FROM  CRM_DB.CRM_SUBSCRIBERS A LEFT JOIN CRM_DB.CRM_SUBSCRIPTION B ON A.SEQ =B.SEQ "
				+ "WHERE A.SUBS_ID_TAXID = '"+id+"' ";
		
		Statement st = null ;
		ResultSet rs = null ;
		Connection conn = getConn3();
		try{
			st = conn.createStatement();
			//System.out.println("query serviceids:"+sql1);
			rs = st.executeQuery(sql1);
			
			while(rs.next()){
				serviceids.add(rs.getString("SERVICEID"));
			}		
		}finally{
			try {
				if(rs!=null) rs.close();
				if(st!=null) st.close();
			} catch (Exception e) {
			}
			closeConn3(conn);
		}
		
		for(String serviceid:serviceids){
			if(!"".equals(serviceid))
				result.add(queryDataByServiceId(serviceid));
		}
	
		return result;
	}
	
	/**
	 * 以護照ID查詢客戶清單
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<Subscriber> queryListByPassPortId(String id) throws Exception{
		List<Subscriber> result = new ArrayList<Subscriber>();
		Set<String> serviceids = new HashSet<String>();
		//CRM DB
		String sql1 ="SELECT  A.SUBS_ID_TAXID ID,A.SUBS_NAME,A.SUBS_PERMANENT_ADDRESS,B.SERVICEID "
				+ "FROM  CRM_DB.CRM_SUBSCRIBERS A LEFT JOIN CRM_DB.CRM_SUBSCRIPTION B ON A.SEQ =B.SEQ "
				+ "WHERE B.PASSPORT_ID = '"+id+"' ";
		
		Statement st = null ;
		ResultSet rs = null ;
		Connection conn = getConn3();
		try{
			st = conn.createStatement();
			//System.out.println("query serviceids:"+sql1);
			rs = st.executeQuery(sql1);
			
			while(rs.next()){
				serviceids.add(rs.getString("SERVICEID"));
			}		
		}finally{
			try {
				if(rs!=null) rs.close();
				if(st!=null) st.close();
			} catch (Exception e) {
			}
			closeConn3(conn);
		}
		
		for(String serviceid:serviceids){
			if(!"".equals(serviceid))
				result.add(queryDataByServiceId(serviceid));
		}
	
		return result;
	}
	
	/**
	 * 以名稱查詢客戶清單
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public List<Subscriber> queryListByName(String name) throws Exception{
		List<Subscriber> result = new ArrayList<Subscriber>();
		Set<String> serviceids = new HashSet<String>();
		//CRM DB
		String sql1 ="SELECT  A.SUBS_ID_TAXID ID,A.SUBS_NAME,A.SUBS_PERMANENT_ADDRESS,B.SERVICEID "
				+ "FROM  CRM_DB.CRM_SUBSCRIBERS A LEFT JOIN CRM_DB.CRM_SUBSCRIPTION B ON A.SEQ =B.SEQ "
				+ "WHERE A.SUBS_NAME LIKE '%"+name+"%' ";
		
		Statement st = null ;
		ResultSet rs = null ;
		Connection conn = getConn3();
		try{
			st = conn.createStatement();
			//System.out.println("query serviceids:"+sql1);
			rs = st.executeQuery(sql1);
			
			while(rs.next()){
				serviceids.add(rs.getString("SERVICEID"));
			}		
		}finally{
			try {
				if(rs!=null) rs.close();
				if(st!=null) st.close();
				
			} catch (Exception e) {
			}closeConn3(conn);
		}
		
		for(String serviceid:serviceids){
			if(!"".equals(serviceid))
				result.add(queryDataByServiceId(serviceid));
		}
	
		return result;
	}
	/**
	 * 以VLN查詢客戶清單
	 * @param VLN
	 * @return
	 * @throws Exception
	 */
	public List<Subscriber> queryListByVLN(String VLN) throws Exception{
		List<Subscriber> result = new ArrayList<Subscriber>();
		
		ResultSet rs = null;
		Statement st = null;
		Connection conn = getConn1();
		String sql = "SELECT A.SERVICEID, A.VLN "
				+ "FROM VLNNUMBER A "
				+ "WHERE A.vlnid <> A.serviceid AND A.VLN = '"+VLN+"' ";
		String serviceId = null;

		try{

			st = conn.createStatement();
			//System.out.println("query List:"+sql);
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				serviceId = rs.getString("SERVICEID");
			}
			
			if(serviceId != null &&!"".equals(serviceId))
				result.add(queryDataByServiceId(serviceId));

		}finally{
			try {
				if(st!=null) st.close();
				if(rs!=null) rs.close();
			} catch (Exception e) { }
			closeConn1(conn);
			//closeConnection();
		}
		return result;
	}
	
	/**
	 * 以香港號查詢客戶清單
	 * @param s2tMsisdn
	 * @return
	 * @throws Exception
	 */
	public List<Subscriber> queryListByS2tMisidn(String s2tMsisdn) throws Exception{
		List<Subscriber> result = new ArrayList<Subscriber>();

		try{
			String serviceid = queryServiceIdbyS2tMsisdn(s2tMsisdn);
			if(serviceid!= null &&!"".equals(serviceid))
				result.add(queryDataByServiceId(serviceid));
			
			/*if(result.size()==0){
				result.add(s);
			}*/
		}finally{
			//closeConnection();
		}
		return result;
	}
	
	/**
	 * 以香港IMSI查詢客戶清單
	 * @param s2tIMSI
	 * @return
	 * @throws Exception
	 */
	public List<Subscriber> queryListByS2tIMSI(String s2tIMSI) throws Exception{
		List<Subscriber> result = new ArrayList<Subscriber>();

		try{
			String serviceid = queryServiceIdbyS2tImsi(s2tIMSI);
			if(serviceid!= null &&!"".equals(serviceid))
				result.add(queryDataByServiceId(serviceid));
			
			/*if(result.size()==0){
				result.add(s);
			}*/
		}finally{
			//closeConnection();
		}
		return result;
	}
	
	/**
	 * 以中華號查詢客戶清單
	 * @param chtMsisdn
	 * @return
	 * @throws Exception
	 */
	public List<Subscriber> queryListByChtMsisdn(String chtMsisdn) throws Exception{
		List<Subscriber> result = new ArrayList<Subscriber>();
		
		try{
			String serviceid = queryServiceIdbyChtMsisdn(chtMsisdn);
			if(serviceid!= null &&!"".equals(serviceid))
				result.add(queryDataByServiceId(serviceid));
			
			/*if(result.size()==0){
				result.add(s);
			}*/
		}finally{
			//closeConnection();
		}
		return result;
	}
	
	/**
	 * 以主號查詢客戶清單
	 * @param mainMsisdn
	 * @return
	 * @throws Exception
	 */
	public List<Subscriber> queryListByMainMsisdn(String mainMsisdn) throws Exception{
		List<Subscriber> result = new ArrayList<Subscriber>();
		
		ResultSet rs = null;
		Statement st = null;
		Connection conn = getConn1();
		String sql = "SELECT A.SERVICEID,A.VALUE "
				+ "FROM PARAMETERVALUE A "
				+ "WHERE A.VALUE = '"+mainMsisdn+"' ";
		String serviceId = null;

		try{

			st = conn.createStatement();
			//System.out.println("query List:"+sql);
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				serviceId = rs.getString("SERVICEID");
			}
			
			if(serviceId != null &&!"".equals(serviceId))
				result.add(queryDataByServiceId(serviceId));

		}finally{
			try {
				if(st!=null) st.close();
				if(rs!=null) rs.close();
				
			} catch (Exception e) { }
			closeConn1(conn);//closeConnection();
		}
		
		
		return result;
	}
	
	
	/*private Subscriber queryList(String serviceid) throws Exception{
		Subscriber result = new Subscriber();
		result.setServiceId(serviceid);
		String sql = "SELECT A.SUBS_ID_TAXID ID,A.SUBS_NAME,A.SUBS_PERMANENT_ADDRESS, "
				+ "(CASE C.STATUS WHEN '1' THEN 'normal'  WHEN '3' THEN 'suspended' WHEN '4' THEN 'terminated' WHEN '10' THEN 'waiting' ELSE 'else' END) STAUS, "
				+ "B. SERVICEID,C. SERVICECODE S2TMSISDN,C.PRICEPLANID,D.FOLLOWMENUMBER CHTMSISDN,C.DATEACTIVATED,C.DATECANCELED  "
				+ "FROM CRM_SUBSCRIBERS A,CRM_SUBSCRIPTION B ,SERVICE C,FOLLOWMEDATA D "
				+ "WHERE A.SEQ = B.SEQ(+) "
				+ "AND B.SERVICEID= C.SERVICEID(+) "
				+ "AND B.SERVICEID = D.SERVICEID(+) "
				+ "AND D.FOLLOWMENUMBER(+)  LIKE '886%' "
				+ " "+condition+" " ;
		
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
				+ "AND D.FOLLOWMENUMBER(+)  LIKE '886%' AND D. FOLLOWMENUMBER is not null "
				+ "AND C.SERVICEID = "+serviceid+" ";
				
				
		String sql3 = "select C.SERVICEID, "
				+ "			(CASE C.STATUS WHEN '1' THEN 'normal'  "
				+ "							WHEN '3' THEN 'suspended' "
				+ "							WHEN '4' THEN 'terminated' "
				+ "							WHEN '10' THEN 'waiting' "
				+ "							ELSE 'else' END) STAUS, "
				+ "			C. SERVICECODE S2TMSISDN,C.PRICEPLANID,E.FORWARD_TO_HOME_NO CHTMSISDN,C.DATEACTIVATED,C.DATECANCELED "
				+ "from service C,S2T_TB_TYPB_WO_SYNC_FILE_DTL E ,FOLLOWMEDATA D "
				+ "where C.SERVICECODE LIKE E.S2T_MSISDN AND C.SERVICEID = D.SERVICEID(+) AND D. FOLLOWMENUMBER is null";
		
		Statement st = null ;
		ResultSet rs = null ;
		
		try{
			st = conn.createStatement();
			//System.out.println("query List:"+sql1);
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
			st = conn.createStatement();
			//System.out.println("query List:"+sql2);
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
			
			
			if(result.getChtMsisdn() == null){
				rs.close();
				
				//System.out.println("query List:"+sql3);
				rs = st.executeQuery(sql3);
				
				while(rs.next()){
					result.setServiceId(rs.getString("SERVICEID"));
					result.setS2tMsisdn(rs.getString("S2TMSISDN"));
					result.setChtMsisdn(rs.getString("CHTMSISDN"));
					result.setPrivePlanId(queryPricePlanId(rs.getString("PRICEPLANID")));
					result.setStatus(rs.getString("STAUS"));
					
					result.setActivatedDate(rs.getString("DATEACTIVATED"));
					result.setCanceledDate(rs.getString("DATECANCELED"));
				}	
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
			//closeConnection();
		}
		return result;
	}*/
	
	/**
	 * 以ID查詢客戶資料，供資料帶入使用
	 * @param id
	 * @return
	 * @throws Exception
	 */
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
				+ "A.SUBS_TYPE,B. SERVICEID,E.CHAIRMAN,E.CHAIRMAN_ID, B.PASSPORT_ID,B.PASSPORT_NAME "
				+ "FROM CRM_DB.CRM_SUBSCRIBERS A left join CRM_DB.CRM_SUBSCRIPTION B on A.seq =B.seq left join CRM_DB.CRM_CHAIRMAN E on A.seq = E.seq "
				+ "WHERE A.SUBS_ID_TAXID = '"+id+"' ";
		
		Statement st = null;
		ResultSet rs = null;
		Connection conn = getConn3();
		try {
			
			st = conn.createStatement();
			//System.out.println("query data by id:"+sql1);
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
				result.setChairId(rs.getString("CHAIRMAN_ID"));
				
				result.setSeq(rs.getString("SEQ"));
				result.setServiceId(rs.getString("SERVICEID"));
				
				//20170317 add
				result.setPassportId(rs.getString("PASSPORT_ID"));
				result.setPassportName(rs.getString("PASSPORT_NAME"));
			}
		} finally{
			try {
				if(rs!=null) rs.close();
				if(st!=null) st.close();
				
			} catch (Exception e) {
			}closeConn3(conn);
			//closeConnection();
		}
		
		return result;
	}
	/**
	 * 以ServiceID查詢客戶資料，主ServiceID
	 * @param id
	 * @return
	 * @throws Exception
	 */
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
				+ "A.SUBS_TYPE,B. SERVICEID,E.CHAIRMAN,E.CHAIRMAN_ID,B.PASSPORT_ID,B.PASSPORT_NAME "
				+ "FROM CRM_DB.CRM_SUBSCRIBERS A left join CRM_DB.CRM_SUBSCRIPTION B on A.seq =B.seq left join CRM_DB.CRM_CHAIRMAN E on A.seq = E.seq "
				+ "WHERE B.SERVICEID = "+id+" ";
		
/*		String sql2="SELECT C. SERVICEID, "
				+ "			(CASE C.STATUS "
				+ "					WHEN '1' THEN 'normal' "
				+ "					WHEN '3' THEN 'suspended' "
				+ "					WHEN '4' THEN 'terminated' "
				+ "					WHEN '10' THEN 'waiting' "
				+ "					ELSE 'else' END) STAUS, "
				+ "			C. SERVICECODE S2TMSISDN,C.PRICEPLANID,D.FOLLOWMENUMBER CHTMSISDN,C.DATEACTIVATED,C.DATECANCELED,F.HOMEIMSI,F.IMSI "
				+ "FROM SERVICE C,FOLLOWMEDATA  D,IMSI F "
				+ "WHERE C.SERVICEID= D.SERVICEID(+) "
				+ "AND C.SERVICEID = F.SERVICEID(+) "
				+ "AND D.FOLLOWMENUMBER(+) LIKE '886%' "
				+ "AND C.SERVICEID = "+id+" ";
		*/
		//S2T DB
		String sql2 = ""
				+ "SELECT C.SERVICEID, "
				+ " 				(CASE C.STATUS WHEN '1' THEN 'normal'  "
				+ "					WHEN '3' THEN 'suspended' "
				+ "					WHEN '4' THEN 'terminated' "
				+ "					WHEN '10' THEN 'waiting' "
				+ "					ELSE 'else' END) STAUS, "
				+ "					C. SERVICECODE S2TMSISDN,C.PRICEPLANID, "
				+ "					to_char(C.DATEACTIVATED,'yyyy/MM/dd hh24:mi:ss') DATEACTIVATED,nvl(to_char(C.DATECANCELED,'yyyy/MM/dd hh24:mi:ss'),'') DATECANCELED,F.HOMEIMSI,F.IMSI "
				+ "FROM SERVICE C,IMSI F "
				+ "WHERE C.SERVICEID = F.SERVICEID(+) AND C.SERVICEID = "+id+" ";
		
		
		
		
		
		
		/*String sql2 ="SELECT C.SERVICEID, "
				+ "			(CASE C.STATUS WHEN '1' THEN 'normal'  "
				+ "							WHEN '3' THEN 'suspended' "
				+ "							WHEN '4' THEN 'terminated' "
				+ "							WHEN '10' THEN 'waiting' "
				+ "							ELSE 'else' END) STAUS, "
				+ "			C. SERVICECODE S2TMSISDN,C.PRICEPLANID,D.FOLLOWMENUMBER CHTMSISDN,"
				+ "			to_char(C.DATEACTIVATED,'yyyy/MM/dd hh24:mi:ss') DATEACTIVATED,nvl(to_char(C.DATECANCELED,'yyyy/MM/dd hh24:mi:ss'),'') DATECANCELED,F.HOMEIMSI,F.IMSI "
				+ "FROM SERVICE C,FOLLOWMEDATA D,IMSI F "
				+ "WHERE  C.SERVICEID = D.SERVICEID(+) "
				+ "AND C.SERVICEID = F.SERVICEID(+) "
				+ "AND D.FOLLOWMENUMBER(+)  LIKE '886%' "
				+ "AND C.status!=4 "
				+ "AND C.SERVICEID = "+id+" ";
				+ "UNION "			
				+ "select C.SERVICEID, "
				+ "			(CASE C.STATUS WHEN '1' THEN 'normal'  "
				+ "							WHEN '3' THEN 'suspended' "
				+ "							WHEN '4' THEN 'terminated' "
				+ "							WHEN '10' THEN 'waiting' "
				+ "							ELSE 'else' END) STAUS, "
				+ "			C. SERVICECODE S2TMSISDN,C.PRICEPLANID,E.FORWARD_TO_HOME_NO CHTMSISDN,"
				+ "			to_char(C.DATEACTIVATED,'yyyy/MM/dd hh24:mi:ss') DATEACTIVATED,nvl(to_char(C.DATECANCELED,'yyyy/MM/dd hh24:mi:ss'),'') DATECANCELED,E.ORIGINAL_CMCC_IMSI HOMEIMSI,E.S2T_IMSI IMSI "
				+ "from service C,S2T_TB_TYPB_WO_SYNC_FILE_DTL E  "
				+ "where C.SERVICECODE = E.S2T_MSISDN(+) "
				+ "AND C.status=4 "
				+ "AND E.SEND_TIME(+) > C.DATEACTIVATED AND E.SEND_TIME(+) <C.DATECANCELED "
				+ "AND C.SERVICEID = "+id+" ";*/
				
		//MBOSS
		String sql3 = "SELECT A.SERVICEID, A.SERVICECODE, A.STATUS, "
				+ "to_char(A.DATEACTIVATED,'yyyy/MM/dd hh24:mi:ss') DATEACTIVATED,nvl(to_char(B.COMPLETEDATE,'yyyy/MM/dd hh24:mi:ss'),'') DATECANCELED "
				+ "FROM SERVICE A, TERMINATIONORDER B "
				+ "WHERE A.SERVICEID=B.TERMOBJID and serviceid = "+id+" ";
		
		
		
		Statement st = null;
		ResultSet rs = null;
		Connection conn = getConn3();
		try {
			
			st = conn.createStatement();
			//System.out.println("query data by service id:"+sql1);
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
				result.setChairId(rs.getString("CHAIRMAN_ID"));
				
				//20170317 add
				result.setPassportId(rs.getString("PASSPORT_ID"));
				result.setPassportName(rs.getString("PASSPORT_NAME"));
	
				result.setSeq(rs.getString("SEQ"));
			}
		} finally{
			try {
				if(rs!=null) rs.close();
				if(st!=null) st.close();
				
			} catch (Exception e) {
			}closeConn3(conn);
			//closeConnection();
		}

		conn = null;
		conn = getConn1();
		try {
			
			st = conn.createStatement();
			//System.out.println("query data by id:"+sql2);
			rs = st.executeQuery(sql2);
			System.out.println("Execute SQL:"+sql2);
			while(rs.next()){

				result.setServiceId(rs.getString("SERVICEID"));
				result.setS2tMsisdn(rs.getString("S2TMSISDN"));
				result.setS2tIMSI(rs.getString("IMSI"));
				//result.setChtMsisdn(rs.getString("CHTMSISDN"));
				//princePlanId= rs.getString("PRICEPLANID");
				result.setPrivePlanId(queryPricePlanId(rs.getString("PRICEPLANID")));
				
				result.setStatus(rs.getString("STAUS"));
				
				result.setActivatedDate(rs.getString("DATEACTIVATED"));
				//result.setCanceledDate(rs.getString("DATECANCELED"));
				
				result.setHomeIMSI(rs.getString("HOMEIMSI"));
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
		
		//20170110
		conn = null;
		conn = getConn2();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql3);
			System.out.println("Execute SQL:"+sql3);
			if(rs.next()){
				//只有已退租才會記錄
				//result.setActivatedDate(rs.getString("DATEACTIVATED"));
				result.setCanceledDate(rs.getString("DATECANCELED"));
			}
		} finally{
			try {
				if(rs!=null) rs.close();
				if(st!=null) st.close();
				closeConn2(conn);
			} catch (Exception e) {
			}
			//closeConnection();
		}
		
		//20170310
		//20170626 mod
		conn = null;
		conn = getConn1();
		String chtMsisdn = null;
		try {
			
			//20170310 add 查詢CHTMSISDN
			//20170510 mod 以環球卡Table查詢
			
			String sql4 = "select Followmenumber from FOLLOWMEDATA A where serviceid = "+id+" AND Followmenumber like '886%' ";
			st = conn.createStatement();
			System.out.println("Execute SQL:"+sql4);
			rs = st.executeQuery(sql4);
			
			if(rs.next()){
				chtMsisdn = rs.getString("Followmenumber");
			}
			
			if(chtMsisdn == null || "".equals(chtMsisdn)){
				sql4 = "select Replace(nvl(A.FORWARD_TO_HOME_NO,A.FORWARD_TO_S2T_NO_1),'+','') MSISDN "
						+ "from S2T_TB_TYPB_WO_SYNC_FILE_DTL A "
						+ "where A.S2T_MSISDN = '"+result.getS2tMsisdn()+"' "
						+ "and A.S2T_OPERATIONDATE > to_date('"+result.getActivatedDate()+"','yyyy/MM/dd hh24:mi:ss') "
						+ (result.getCanceledDate() == null || "".equals(result.getCanceledDate() ) ? "" : "AND A.S2T_OPERATIONDATE < to_date('"+result.getCanceledDate()+"','yyyy/MM/dd hh24:mi:ss') " )
						+ "order by A.S2T_OPERATIONDATE DESC";
				
				rs = null;
				System.out.println("Execute SQL:"+sql4);
				rs = st.executeQuery(sql4);
				if(rs.next()){
					chtMsisdn = rs.getString("MSISDN");
				}
			}
			
			
	/*		String sql4 = ""
					+ "       select * from (	"
					+ "								select replace(A.FORWARD_TO_HOME_NO,'+','') CHTMSISDN "
					+ "								from S2T_TB_TYPB_WO_SYNC_FILE_DTL A,SERVICE B "
					+ "								where A.S2T_MSISDN = B.SERVICECODE "
					+ "								AND A.SEND_TIME > B.DATEACTIVATED AND  (B.DATECANCELED is null  OR A.SEND_TIME <= B.DATECANCELED) "
					+ ("139".equals(result.getPrivePlanId().getId())?" AND replace(A.FORWARD_TO_HOME_NO,'+','') like '886%' ":"")
					+ "								AND B.SERVICEID ="+id+"  ORDER BY A.SEND_TIME DESC"
					+ "						) WHERE rownum = 1 "
					+ "		union all "
					+ "		 select A.PARTNERMSISDN CHTMSISDN "
					+ "		from AVAILABLEMSISDN A ,service B "
					+ "		where B.serviceid = "+id+" and A.S2TMSISDN = B.servicecode "
					+ "		union all "
					+ "		 select A.FOLLOWMENUMBER CHTMSISDN "
					+ "		from FOLLOWMEDATA A"
					+ "    	where A.serviceid = "+id+" AND A.FOLLOWMENUMBER LIKE '%886%'"
					+ " ";
			
			st = conn.createStatement();
			rs = st.executeQuery(sql4);
			System.out.println("Execute SQL:"+sql4);
			String chtMsisdn = null;
			while(rs.next()){				
				chtMsisdn = rs.getString("CHTMSISDN");
				if(chtMsisdn!=null )
					break;
			}*/
			
			
			result.setChtMsisdn(chtMsisdn);
			
		} finally{
			try {
				if(rs!=null) rs.close();
				if(st!=null) st.close();
				closeConn1(conn);
			} catch (Exception e) {
			}
			//closeConnection();
		}
		
		result.setNowS2tActivated(queryS2TMsisdnNowStatus(result.getS2tMsisdn()));
		
		return result;
	}
	
	/**
	 * 更新客戶資料
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public boolean updateSubscriber(Subscriber s) throws Exception{
		boolean result = false;
		String sql = "UPDATE CRM_DB.CRM_SUBSCRIBERS A "
				+ "SET A.SUBS_ID_TAXID='"+s.getIdTaxid()+"',A.SUBS_NAME='"+s.getName()+"',A.SUBS_BIRTHDAY='"+s.getBirthday()+"'  "
				+ ",A.SUBS_PHONE='"+s.getPhone()+"',A.SUBS_EMAIL='"+s.getEmail()+"'  "
				+ ",A.SUBS_PERMANENT_ADDRESS='"+s.getPermanentAddress()+"',A.SUBS_BILLING_ADDRESS='"+s.getBillingAddress()+"'  "
				+ ",A.AGENCY_ID='"+s.getAgency()+"',A.REMARK='"+s.getRemark()+"',A.UPDATETIME=now() ,A.SUBS_TYPE = '"+s.getType()+"'  "
				//+ ",A.PASSPORT_ID = '"+s.getPassportId()+"',A.PASSPORT_NAME = '"+s.getPassportName()+"'  "
				+ "WHERE A.SEQ ='"+s.getSeq()+"'";
	
		Statement st = null;
		Connection conn = getConn3();
		conn.setAutoCommit(false);
		
		if(s.getSeq()== null ||"".equals(s.getSeq())){
			//如果為新資料，進行插入動作
			result = insertSubscriber(s);
		}else{
			try {
				st = conn.createStatement();
				//System.out.println("sql:"+sql);
				int eRow = st.executeUpdate(sql);
				
				if(eRow>1)
					throw new Exception("Update data fail! updated Subscriber "+eRow);
				if(eRow == 0)
					result = insertSubscriber(s);
				result = true;
			}finally{
				try {
					if(st!=null) st.close();
					
				} catch (Exception e) {
				}closeConn3(conn);
				//closeConnection();
			}	
		}
		updateSubscription(s);
		conn.commit();
		
		
		
		return result;
	}
	
	public boolean insertSubscriber(Subscriber s) throws Exception{
		boolean result = false;
		
		Statement st = null;
		Connection conn = getConn3();
		try {

			st = conn.createStatement();
			
			String sql3 = "SELECT MAX(A.seq)+1 SEQ "
					+ "FROM CRM_DB.CRM_SUBSCRIBERS A ";
			//System.out.println("sql3:"+sql3);
			ResultSet rs = st.executeQuery(sql3);
			
			while(rs.next()){
				s.setSeq(rs.getString("SEQ"));
			}

			String sql = "INSERT INTO "
					+ "CRM_DB.CRM_SUBSCRIBERS("
					+ "SUBS_NAME, SUBS_BIRTHDAY, SUBS_ID_TAXID, SUBS_PHONE, SUBS_EMAIL,"
					+ "SUBS_PERMANENT_ADDRESS,SUBS_BILLING_ADDRESS,AGENCY_ID,REMARK,"
					+ "CREATETIME,SUBS_TYPE,SEQ"
					//+ ",PASSPORT_ID,PASSPORT_NAME"
					+ ") "
					+ "VALUES("
					+ "'"+s.getName()+"','"+s.getBirthday()+"','"+s.getIdTaxid()+"','"+s.getPhone()+"','"+s.getEmail()+"',"
					+ "'"+s.getPermanentAddress()+"','"+s.getBillingAddress()+"','"+s.getAgency()+"','"+s.getRemark()+"',"
					+ "now(),'"+s.getType()+"','"+s.getSeq()+"' "
					//+ ",'"+s.getPassportId()+"','"+s.getPassportName()+"' "
					+ ") ";
			//System.out.println("sql:"+sql);
			int eRow = st.executeUpdate(sql);

			if(eRow!=1)
				throw new Exception("Update data fail! InsertSubscriber "+eRow);
			//插入後更新對應資料
			updateSubscription(s);
			result = true;
		}finally{
			try {
				if(st!=null) st.close();
				
			} catch (Exception e) {
			}closeConn3(conn);
		}	
		return result;
	}
	
	public boolean insertSubscription(Subscriber s) throws Exception{
		
		Statement st = null;
		Connection conn = getConn3();
		boolean result = false;
		try {

			st = conn.createStatement();
			
			String sql = "INSERT INTO "
					+ "CRM_DB.CRM_SUBSCRIPTION("
					+ "SERVICEID,SEQ,CREATETIME"
					+ ",PASSPORT_ID,PASSPORT_NAME"
					+ ") "
					+ "VALUES("
					+ " '"+s.getServiceId()+"','"+s.getSeq()+"',now()"
					+",'"+s.getPassportId()+"','"+s.getPassportName()+"' "
					+ ") ";
			
			//System.out.println("sql:"+sql);
			int eRow = st.executeUpdate(sql);
			
			if(eRow!=1)
				throw new Exception("Update data fail! inserted Subscription "+eRow);
			
			result = true;
		}finally{
			try {
				if(st!=null) st.close();
				
			} catch (Exception e) {
			}closeConn3(conn);
		}
		return result;	
		
		
	}
	
	public boolean updateSubscription(Subscriber s) throws Exception{
		
		
		boolean result = false;
		Connection conn = getConn3();
		Statement st = null;
		try {

			st = conn.createStatement();

			String sql = "UPDATE CRM_DB.CRM_SUBSCRIPTION A "
					+ "SET A.SEQ = '"+s.getSeq()+"',A.UPDATETIME = now() "
					+ ",A.PASSPORT_ID = '"+s.getPassportId()+"',A.PASSPORT_NAME = '"+s.getPassportName()+"'  "
					+ "WHERE A.SERVICEID = '"+s.getServiceId()+"'";
			
			if(s.getServiceId()==null || "".equals(s.getServiceId())){
				throw new Exception("Invalid serviceid!");
			}else{
				//System.out.println("sql:"+sql);
				int eRow = st.executeUpdate(sql);
				
				if(eRow>1)
					throw new Exception("Update data fail! updated Subscription "+eRow);
				else if(eRow==0)
					//如果為新資料，進行插入
					insertSubscription(s);
			}

			//為公司戶嘗試更新負責人資訊，否則嘗試刪除不正確的負責人資料
			if("E".equalsIgnoreCase(s.getType())){
				updateChairMain(s);
			}else if("P".equalsIgnoreCase(s.getType())){
				deleteChairMain(s);
			}
			
			
			result = true;
		}finally{
			try {
				if(st!=null) st.close();
			} catch (Exception e) {
			}
			closeConn3(conn);
		}
		return result;	
		
	}
	
	public int insertChairMain(Subscriber s) throws Exception{
		int result = 0;
		
		
		String sql = "INSERT INTO CRM_DB.CRM_CHAIRMAN(SEQ,CHAIRMAN,CHAIRMAN_ID,CREATETIME) "
				+ "VALUES('"+s.getSeq()+"','"+s.getChair()+"','"+s.getChairId()+"',now())";
		
		
		Statement st = null;
		Connection conn = getConn3();
		try {
			st = conn.createStatement();
			//System.out.println("sql:"+sql);
			result = st.executeUpdate(sql);

		}finally{
			try {
				if(st!=null) st.close();
			} catch (Exception e) {
			}
			closeConn3(conn);
		}	
		return result;
	}
	
	public boolean updateChairMain(Subscriber s) throws Exception{
		boolean result = false;
		
		
		String sql = "UPDATE CRM_DB.CRM_CHAIRMAN A "
				+ "SET A.CHAIRMAN = '"+s.getChair()+"', A.CHAIRMAN_ID = '"+s.getChairId()+"' "
				+ "WHERE A.SEQ = '"+s.getSeq()+"'";
		
		
		Statement st = null;
		Connection conn = getConn3();
		try {
			st = conn.createStatement();
			//System.out.println("sql:"+sql);
			int eRow = st.executeUpdate(sql);
			if(eRow>1)
				throw new Exception("Update data fail! updated ChairMain "+eRow);
			else if(eRow==0)
				insertChairMain(s);
			
			result = true;
		}finally{
			try {
				if(st!=null) st.close();
			} catch (Exception e) {
			}
			closeConn3(conn);
		}	
		return result;
	}
	
	public boolean deleteChairMain(Subscriber s) throws Exception{
		boolean result = false;
		
		
		String sql = "DELETE FROM CRM_DB.CRM_CHAIRMAN "
				+ "WHERE SEQ = '"+s.getSeq()+"' ";
		
		
		Statement st = null;
		Connection conn = getConn3();
		try {
			st = conn.createStatement();
			//System.out.println("sql:"+sql);
			st.executeUpdate(sql);
			result = true;

		}finally{
			try {
				if(st!=null) st.close();
				
			} catch (Exception e) {
			}
			closeConn3(conn);
		}	
		return result;
	}
	
	private String queryS2TMsisdnNowStatus(String S2TMsisdn) throws Exception{
		String result = "0"; 
		
		Statement st = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			conn =getConn1();
			st = conn.createStatement();
			
			String sql = "select count(1) CD from service where servicecode='"+S2TMsisdn+"' and datecanceled is null ";
			System.out.println("sql : "+sql);
			rs =  st.executeQuery(sql);
			if(rs.next()){
				result = rs.getString("CD");
			}

		}finally{
			try {
				if(st!=null) st.close();
				if(rs!=null) rs.close();
			} catch (Exception e) { }
			closeConn3(conn);//closeConnection();
		}
		
		return result;
	}	
	
}
