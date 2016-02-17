package main.DVRS.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import main.DVRS.bean.CurrentDay;
import main.DVRS.bean.CurrentMonth;
import main.common.action.CacheAction;
import main.common.dao.BaseDao;


@Repository
public class CurrentDao extends BaseDao {

	public CurrentDao() throws Exception {
		super();
	}

	Map<String,String> serviceIDtoIMSI = new HashMap<String,String>();
	Map<String,String> imsitoServiceID = new HashMap<String,String>();
	
	
	public List<CurrentMonth> queryCurrentMonth() throws Exception{
		
		imsitoServiceID = CacheAction.getImsitoServiceID();
		serviceIDtoIMSI = CacheAction.getServiceIDtoIMSI();
		if(imsitoServiceID.size()==0){
			CacheAction.reloadServiceIDwithIMSIMappingCache();
			imsitoServiceID = CacheAction.getImsitoServiceID();
			serviceIDtoIMSI = CacheAction.getServiceIDtoIMSI();
			createConnection();
		}
		
		String sql=
				"SELECT A.MONTH,A.SERVICEID,A.CHARGE,A.VOLUME,A.SMS_TIMES,A.LAST_ALERN_THRESHOLD,A.LAST_ALERN_VOLUME,A.EVER_SUSPEND,A.LAST_FILEID "
				+ ",TO_CHAR(A.LAST_DATA_TIME,'yyyy/MM/dd hh24:mi:ss') LAST_DATA_TIME,TO_CHAR(A.UPDATE_DATE,'yyyy/MM/dd hh24:mi:ss') UPDATE_DATE,TO_CHAR(A.CREATE_DATE,'yyyy/MM/dd hh24:mi:ss') CREATE_DATE "
				+ "FROM HUR_CURRENT A "
				+ "ORDER BY A.LAST_DATA_TIME DESC ";
		
		List<CurrentMonth> list = new ArrayList<CurrentMonth>();
		
		
			Statement st = conn.createStatement();
			
			ResultSet rs=st.executeQuery(sql);
			
			while(rs.next()){
				
				String imsi = serviceIDtoIMSI.get(rs.getString("SERVICEID"));
				if(imsi==null || "".equals(imsi))
					imsi=rs.getString("SERVICEID");
				
				CurrentMonth c = new CurrentMonth();
				c.setMonth(rs.getString("MONTH"));
				c.setImsi(imsi);
				c.setCharge(FormatDouble(rs.getDouble("CHARGE"), "0.0000"));
				c.setVolume(rs.getDouble("VOLUME"));
				c.setSmsTimes(rs.getInt("SMS_TIMES"));
				c.setLastAlertThreshold(rs.getDouble("LAST_ALERN_THRESHOLD"));
				c.setLastAlertVolume(rs.getDouble("LAST_ALERN_VOLUME"));
				c.setEverSuspend(("0".equals(rs.getString("EVER_SUSPEND"))?false:true));
				c.setLastFileId(rs.getInt("LAST_FILEID"));
				c.setLastDataTime(rs.getString("LAST_DATA_TIME"));
				c.setUpdateDate(rs.getString("UPDATE_DATE"));
				c.setCreateDate(rs.getString("CREATE_DATE"));
				
				list.add(c);
			}
			rs.close();
			st.close();
			//closeConnection();
		return list;
		
	}
	
	public List<CurrentMonth> queryCurrentMonth(String imsi,String from,String to,String suspend) throws Exception{
		System.out.println("dao queryCurrentMonth..."+","+new Date());
		
		List<CurrentMonth> list = new ArrayList<CurrentMonth>();
		String sServiceID = null;
		String sql = ""
				+ "SELECT A.SERVICEID,A.IMSI "
				+ "FROM( 	SELECT A.SERVICEID,B.NEWVALUE IMSI,A.COMPLETEDATE "
				+ "			FROM SERVICEORDER A,SERVICEINFOCHANGEORDER B "
				+ "			WHERE A.ORDERID =B.ORDERID AND  B.FIELDID=3713 "
				+ "			UNION "
				+ "			SELECT A.SERVICEID,B.FIELDVALUE IMSI,A.COMPLETEDATE "
				+ "			FROM SERVICEORDER A,NEWSERVICEORDERINFO B "
				+ "			WHERE A.ORDERID =B.ORDERID AND   B.FIELDID=3713 )A, "
				+ "		(	SELECT SERVICEID,MAX(COMPLETEDATE) COMPLETEDATE "
				+ "			FROM(	SELECT A.SERVICEID,B.NEWVALUE,A.COMPLETEDATE "
				+ "					FROM SERVICEORDER A,SERVICEINFOCHANGEORDER B "
				+ "					WHERE A.ORDERID =B.ORDERID AND  B.FIELDID=3713 "
				+ "					UNION "
				+ "					SELECT A.SERVICEID,B.FIELDVALUE,A.COMPLETEDATE "
				+ "					FROM SERVICEORDER A,NEWSERVICEORDERINFO B "
				+ "					WHERE A.ORDERID =B.ORDERID AND   B.FIELDID=3713) "
				+ "			GROUP BY SERVICEID )B "
				+ "WHERE A.SERVICEID=B.SERVICEID AND A.COMPLETEDATE =B.COMPLETEDATE AND IMSI = '"+imsi+"' ";

		Statement st = null;
		ResultSet rs = null;
		
		try{
			st = getConn2().createStatement();
			System.out.println("query serviceid : "+new Date()+sql);
			rs =	st.executeQuery(sql);
			
			while(rs.next()){
				sServiceID = rs.getString("SERVICEID");
			}
		}finally{
			try {
				if(rs!=null)
					rs.close();
				if(st!=null)
					st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(sServiceID==null)
			return null;
		
		st = null;
		rs = null;
		
		String sql2=
				"SELECT A.MONTH,A.SERVICEID,A.CHARGE,A.VOLUME,A.SMS_TIMES,A.LAST_ALERN_THRESHOLD,A.LAST_ALERN_VOLUME,A.EVER_SUSPEND,A.LAST_FILEID "
				+ ",TO_CHAR(A.LAST_DATA_TIME,'yyyy/MM/dd hh24:mi:ss') LAST_DATA_TIME,TO_CHAR(A.UPDATE_DATE,'yyyy/MM/dd hh24:mi:ss') UPDATE_DATE,TO_CHAR(A.CREATE_DATE,'yyyy/MM/dd hh24:mi:ss') CREATE_DATE "
				+ "FROM HUR_CURRENT A where 1=1 "
				+ (from!=null &&!"".equals(from)?"AND A.MONTH >=  "+from+" ":"")
				+ (to!=null &&!"".equals(to)?"AND A.MONTH <=  "+to+" ":"")
				+ (suspend!=null && !"".equals(suspend)?"AND A.EVER_SUSPEND="+suspend+" ":"")
				+ (sServiceID!=null && !"".equals(sServiceID)? "AND A.SERVICEID in "+sServiceID+" ":"")
				+ "ORDER BY A.LAST_DATA_TIME DESC ";

		try{
			st = getConn1().createStatement();
			System.out.println("query execute : "+new Date()+sql2);
			rs = st.executeQuery(sql2);

				while(rs.next()){
	
				CurrentMonth c = new CurrentMonth();
				c.setMonth(rs.getString("MONTH"));
				c.setImsi(imsi);
				c.setCharge(FormatDouble(rs.getDouble("CHARGE"), "0.0000"));
				c.setVolume(rs.getDouble("VOLUME"));
				c.setSmsTimes(rs.getInt("SMS_TIMES"));
				c.setLastAlertThreshold(rs.getDouble("LAST_ALERN_THRESHOLD"));
				c.setLastAlertVolume(rs.getDouble("LAST_ALERN_VOLUME"));
				c.setEverSuspend(("0".equals(rs.getString("EVER_SUSPEND"))?false:true));
				c.setLastFileId(rs.getInt("LAST_FILEID"));
				c.setLastDataTime(rs.getString("LAST_DATA_TIME"));
				c.setUpdateDate(rs.getString("UPDATE_DATE"));
				c.setCreateDate(rs.getString("CREATE_DATE"));
				list.add(c);
			}
			
		}finally{
			try {
				if(rs!=null)
					rs.close();
				if(st!=null)
					st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
			
		return list;
	}
	
	public List<CurrentDay> queryCurrentDay() throws Exception{
		imsitoServiceID = CacheAction.getImsitoServiceID();
		serviceIDtoIMSI = CacheAction.getServiceIDtoIMSI();
		if(imsitoServiceID.size()==0){
			CacheAction.reloadServiceIDwithIMSIMappingCache();
			imsitoServiceID = CacheAction.getImsitoServiceID();
			serviceIDtoIMSI = CacheAction.getServiceIDtoIMSI();
			createConnection();
		}
		
		String sql=				
				"SELECT A.DAY,SUBSTR(MCCMNC,4)||'('||(case when B.NAME is not null then  B.NAME else substr(A.MCCMNC,0,3) end)||')' MCCMNC,A.SERVICEID,A.CHARGE,A.VOLUME,A.ALERT,A.LAST_FILEID "
				+ ",TO_CHAR(A.LAST_DATA_TIME,'yyyy/MM/dd hh24:mi:ss') LAST_DATA_TIME,TO_CHAR(A.UPDATE_DATE,'yyyy/MM/dd hh24:mi:ss') UPDATE_DATE,TO_CHAR(A.CREATE_DATE,'yyyy/MM/dd hh24:mi:ss') CREATE_DATE "
				+ "FROM HUR_CURRENT_DAY A,HUR_CUSTOMER_SERVICE_PHONE B "
				+ "where substr(A.MCCMNC,0,3) = B.CODE(+) "
				+ "ORDER BY A.DAY,A.LAST_DATA_TIME DESC ";
		
		List<CurrentDay> list = new ArrayList<CurrentDay>();
		
		
			Statement st = conn.createStatement();
			
			ResultSet rs=st.executeQuery(sql);
			
			while(rs.next()){
				String imsi = serviceIDtoIMSI.get(rs.getString("SERVICEID"));
				if(imsi==null || "".equals(imsi))
					imsi=rs.getString("SERVICEID");
				CurrentDay c = new CurrentDay();
				c.setDay(rs.getString("DAY"));
				c.setMccmnc(rs.getString("MCCMNC"));
				c.setImsi(imsi);
				c.setCharge(FormatDouble(rs.getDouble("CHARGE"), "0.0000"));
				c.setVolume(rs.getDouble("VOLUME"));
				c.setAlert(("0".equals(rs.getString("ALERT"))?false:true));
				c.setLastFileId(rs.getInt("LAST_FILEID"));
				c.setLastDataTime(rs.getString("LAST_DATA_TIME"));
				c.setUpdateDate(rs.getString("UPDATE_DATE"));
				c.setCreateDate(rs.getString("CREATE_DATE"));
				
				list.add(c);
			}
			rs.close();
			st.close();
			//closeConnection();
		return list;
		
	}
	
	public List<CurrentDay> queryCurrentDay(String imsi,String from,String to) throws Exception{

		List<CurrentDay> list = new ArrayList<CurrentDay>();
		String sServiceID = null;
		String sql = ""
				+ "SELECT A.SERVICEID,A.IMSI "
				+ "FROM( 	SELECT A.SERVICEID,B.NEWVALUE IMSI,A.COMPLETEDATE "
				+ "			FROM SERVICEORDER A,SERVICEINFOCHANGEORDER B "
				+ "			WHERE A.ORDERID =B.ORDERID AND  B.FIELDID=3713 "
				+ "			UNION "
				+ "			SELECT A.SERVICEID,B.FIELDVALUE IMSI,A.COMPLETEDATE "
				+ "			FROM SERVICEORDER A,NEWSERVICEORDERINFO B "
				+ "			WHERE A.ORDERID =B.ORDERID AND   B.FIELDID=3713 )A, "
				+ "		(	SELECT SERVICEID,MAX(COMPLETEDATE) COMPLETEDATE "
				+ "			FROM(	SELECT A.SERVICEID,B.NEWVALUE,A.COMPLETEDATE "
				+ "					FROM SERVICEORDER A,SERVICEINFOCHANGEORDER B "
				+ "					WHERE A.ORDERID =B.ORDERID AND  B.FIELDID=3713 "
				+ "					UNION "
				+ "					SELECT A.SERVICEID,B.FIELDVALUE,A.COMPLETEDATE "
				+ "					FROM SERVICEORDER A,NEWSERVICEORDERINFO B "
				+ "					WHERE A.ORDERID =B.ORDERID AND   B.FIELDID=3713) "
				+ "			GROUP BY SERVICEID )B "
				+ "WHERE A.SERVICEID=B.SERVICEID AND A.COMPLETEDATE =B.COMPLETEDATE AND IMSI = '"+imsi+"' ";

		Statement st = null;
		ResultSet rs = null;
		
		try{
			st = getConn2().createStatement();
			System.out.println("query serviceid : "+new Date()+sql);
			rs =	st.executeQuery(sql);
			
			while(rs.next()){
				sServiceID = rs.getString("SERVICEID");
			}
		}finally{
			try {
				if(rs!=null)
					rs.close();
				if(st!=null)
					st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		st = null;
		rs = null;
		
		if(sServiceID==null)
			return null;
		
		String sql2=
				"SELECT A.DAY,SUBSTR(MCCMNC,4)||'('||(case when B.NAME is not null then  B.NAME else substr(A.MCCMNC,0,3) end)||')' MCCMNC,A.SERVICEID,A.CHARGE,A.VOLUME,A.ALERT,A.LAST_FILEID  "
				+ ",TO_CHAR(A.LAST_DATA_TIME,'yyyy/MM/dd hh24:mi:ss') LAST_DATA_TIME,TO_CHAR(A.UPDATE_DATE,'yyyy/MM/dd hh24:mi:ss') UPDATE_DATE,TO_CHAR(A.CREATE_DATE,'yyyy/MM/dd hh24:mi:ss') CREATE_DATE "
				+ "FROM HUR_CURRENT_DAY A, HUR_CUSTOMER_SERVICE_PHONE B "
				+ "where substr(A.MCCMNC,0,3) = B.CODE(+) "
				+ (from!=null &&!"".equals(from)?"AND A.DAY >=  "+from+" ":"")
				+ (to!=null &&!"".equals(to)?"AND A.DAY <=  "+to+" ":"")
				//20150505 add
				+ (sServiceID!=null && !"".equals(sServiceID)?"AND A.SERVICEID in "+sServiceID+" " : "")
				+ "ORDER BY A.LAST_DATA_TIME DESC ";

		try{
			st = getConn1().createStatement();
			System.out.println("query execute : "+new Date()+sql2);
			rs = st.executeQuery(sql2);

			while(rs.next()){
				CurrentDay c = new CurrentDay();
				c.setDay(rs.getString("DAY"));
				c.setMccmnc(rs.getString("MCCMNC"));
				c.setImsi(imsi);
				c.setCharge(FormatDouble(rs.getDouble("CHARGE"), "0.0000"));
				c.setVolume(rs.getDouble("VOLUME"));
				c.setAlert(("0".equals(rs.getString("ALERT"))?false:true));
				c.setLastFileId(rs.getInt("LAST_FILEID"));
				c.setLastDataTime(rs.getString("LAST_DATA_TIME"));
				c.setUpdateDate(rs.getString("UPDATE_DATE"));
				c.setCreateDate(rs.getString("CREATE_DATE"));
				list.add(c);
			}
			
		}finally{
			try {
				if(rs!=null)
					rs.close();
				if(st!=null)
					st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		return list;
		
	}
	public Double FormatDouble(Double value,String form){
		
		if(form==null || "".equals(form)){
			form="0.00";
		}

		return Double.valueOf(new DecimalFormat(form).format(value));
	}
	

}
