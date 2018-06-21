package main.CRM.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import main.CRM.bean.AddonService;
import main.CRM.bean.USPacket;

@Repository
public class ElseDao extends CRMBaseDao{

	public ElseDao() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 取的華人上網包資料
	 * @param serviceId
	 * @return
	 * @throws Exception
	 */
	public List<USPacket> queryUSPacket(String serviceId) throws Exception{
		List<USPacket> result = new ArrayList<USPacket>(); 

		String sql = "select A.SERVICEID,A.START_DATE,A.END_DATE,"
				+ "to_char(A.CREATE_TIME,'yyyy/MM/dd hh24:mi:ss') CREATE_TIME,"
				+ "to_char(A.CANCEL_TIME,'yyyy/MM/dd hh24:mi:ss') CANCEL_TIME,A.ALERTED||'%' ALERTED "
				+ "from HUR_VOLUME_POCKET A "
				+ "where A.TYPE = 0 AND A.SERVICEID = '"+serviceId+"' "
				+ "ORDER by A.START_DATE DESC ";
		
				
		Statement st = null;
		ResultSet rs = null;
		Connection conn = getConn1();
		try {
			
			st = conn.createStatement();
			//System.out.println("sql:"+sql);
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				USPacket a = new USPacket();
				a.setServiceid(rs.getString("SERVICEID"));
				a.setStartDate(rs.getString("START_DATE"));
				a.setEndDate(rs.getString("END_DATE"));
				a.setCreateTime(rs.getString("CREATE_TIME"));
				a.setCancelTime(rs.getString("CANCEL_TIME"));
				a.setAlerted(rs.getString("ALERTED"));
			
				result.add(a);
			}
		} finally{
			try {
				if(rs!=null) rs.close();
				if(st!=null) st.close();
			} catch (Exception e) {
			}
			//closeConnection();
			closeConn1(conn);
		}
		return result;
	}
	
	/**
	 * 取得數據狀態
	 * @param msisdn
	 * @return
	 * @throws Exception
	 */
	public String getGPRSStatus(String serviceid) throws Exception{
		String result = null;
		String sql = "SELECT nvl(PDPSUBSID,0) as status "
				+ "FROM BASICPROFILE "
				+ "WHERE serviceid = '"+serviceid+"'";
				
		Statement st = null;
		ResultSet rs = null;
		Connection conn = getConn1();
		try {
			
			st = conn.createStatement();
			//System.out.println("sql:"+sql);
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				result = rs.getString("status");
			}
		} finally{
			try {
				if(rs!=null) rs.close();
				if(st!=null) st.close();
			} catch (Exception e) {
			}
			closeConn1(conn);
			//closeConnection();
		}
		return result;
	}
	
	/**
	 * 查詢華人上網包資料
	 * @param serviceId
	 * @return
	 * @throws Exception
	 */
	public List<AddonService> queryAddonService(String serviceId) throws Exception{
		List<AddonService> result = new ArrayList<AddonService>(); 

		String sql = "SELECT A.SERVICEID,A.SERVICECODE,A.STATUS,A.STARTDATE,A.ENDDATE "
				+ "FROM ADDONSERVICE_N A "
				+ "WHERE A.SERVICEID = '"+serviceId+"' "
				+ "ORDER BY A.STARTDATE DESC";
				
		Statement st = null;
		ResultSet rs = null;
		Connection conn = getConn1();
		try {
			
			st = conn.createStatement();
			//System.out.println("sql:"+sql);
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
				if(rs!=null) rs.close();
				if(st!=null) st.close();
			} catch (Exception e) {
			}
			closeConn1(conn);
			//closeConnection();
		}
		return result;
	}
	
	/**
	 * 查詢VLN
	 * @param serviceId
	 * @return
	 * @throws Exception
	 */
	public List<String> queryVLN(String serviceId) throws Exception{
		List<String> result = new ArrayList<String>(); 

		String sql = "SELECT A.VLN||'('||(case A.vlntype when '1' then 'static' when '0' then 'dynamic' else '' end)||')' VLN "
				+ "FROM VLNNUMBER A "
				+ "WHERE A.status = 1 AND A.SERVICEID = '"+serviceId+"' ";
				
		//String s2tIMSI = null,chtMsisdn = null;
		Statement st = null;
		ResultSet rs = null;
		Connection conn = getConn1();
		try {
			
			st = conn.createStatement();
			//System.out.println("sql:"+sql);
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				result.add(rs.getString("VLN"));
			}
			
			/*sql = "select a.imsi,c.PARTNERMSISDN "
					+ "from IMSI A,service B,AVAILABLEMSISDN c "
					+ "where A.serviceid = B.serviceid and B.servicecode = c.s2tMsisdn and a.serviceid = "+serviceId+" ";
			
			rs = st.executeQuery(sql);
			while(rs.next()){
				s2tIMSI = rs.getString("IMSI");
				chtMsisdn = rs.getString("PARTNERMSISDN");
			}*/
			
			
			/*sql = "select instr(A.content,'CHNA') CD "
					+ "from PROVLOG A "
					+ "where A.CONTENT like '%S2T_IMSI="+s2tIMSI+"%' AND A.CONTENT like '%Req_Status=07%' "
					+ "order by A.REQTIME desc ";*/
			
			/*sql = "select instr(A.content,'CHNA') CD "
					+ "from PROVLOG A "
					+ "where (A.CONTENT like '%TWNLD_MSISDN="+chtMsisdn+"%' or A.CONTENT like '%S2T_IMSI="+s2tIMSI+"%'  )"
					+ "AND (A.CONTENT like '%Req_Status=07%' or A.CONTENT like '%Req_Status=99%') "
					+ "order by A.REQTIME desc ";
			rs = st.executeQuery(sql);
			
			//只取最後一筆
			if(rs.next()){
				int cd = rs.getInt("CD");
				if(cd!=0)
					result.add("＊已申請中國固定號");
			}*/

			
		} finally{
			try {
				if(rs!=null) rs.close();
				if(st!=null) st.close();
			} catch (Exception e) {
			}
			closeConn1(conn);
			//closeConnection();
		}
		return result;
	}
	
	public List<String> queryWhetherAppliedCHNA(String serviceId) throws Exception{
		List<String> result = new ArrayList<String>(); 
		String sql = "";
				
		String s2tIMSI = null,chtMsisdn = null;
		Statement st = null;
		ResultSet rs = null;
		Connection conn = getConn1();
		try {
				
			sql = "select a.imsi,c.PARTNERMSISDN "
					+ "from IMSI A,service B,AVAILABLEMSISDN c "
					+ "where A.serviceid = B.serviceid and B.servicecode = c.s2tMsisdn and a.serviceid = "+serviceId+" ";
			st = conn.createStatement();		
			rs = st.executeQuery(sql);
			while(rs.next()){
				s2tIMSI = rs.getString("IMSI");
				chtMsisdn = rs.getString("PARTNERMSISDN");
			}
			/*sql = "select instr(A.content,'CHNA') CD "
					+ "from PROVLOG A "
					+ "where A.CONTENT like '%S2T_IMSI="+s2tIMSI+"%' AND A.CONTENT like '%Req_Status=07%' "
					+ "order by A.REQTIME desc ";*/
			
			sql = "select instr(A.content,'CHNA') CD,to_char(A.REQTIME,'MM/dd') EF "
					+ "from PROVLOG A "
					+ "where (A.CONTENT like '%TWNLD_MSISDN="+chtMsisdn+"%' or A.CONTENT like '%S2T_IMSI="+s2tIMSI+"%'  )"
					+ "AND (A.CONTENT like '%CHNA%' or A.CONTENT like '%CHND%') "
					+ "order by A.REQTIME desc ";
			rs = st.executeQuery(sql);
			
			//只取最後一筆
			if(rs.next()){
				int cd = rs.getInt("CD");
				String ef = rs.getString("EF");
				if(cd!=0)
					result.add("＊"+ef+"已申請中國固定號");
			}
		} finally{
			try {
				if(rs!=null) rs.close();
				if(st!=null) st.close();
			} catch (Exception e) {
			}
			closeConn1(conn);
			//closeConnection();
		}
		return result;
	}
	
}
