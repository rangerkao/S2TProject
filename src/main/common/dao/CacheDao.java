package main.common.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public class CacheDao extends BaseDao{

	public CacheDao() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	public Map<String,String> queryIMSItoServiceID() throws Exception{	
		
		Map<String,String> imsitoServiceID = new HashMap<String,String>();
		System.out.println("setImsitoServiceID...");
		String sql = " SELECT A.SERVICEID,A.IMSI          "
				+ "FROM("
				+ "		SELECT A.SERVICEID,B.NEWVALUE IMSI,A.COMPLETEDATE"
				+ "		FROM SERVICEORDER A,SERVICEINFOCHANGEORDER B"
				+ "		WHERE A.ORDERID =B.ORDERID AND  B.FIELDID=3713"
				+ "		UNION"
				+ "		SELECT A.SERVICEID,B.FIELDVALUE IMSI,A.COMPLETEDATE"
				+ "		FROM SERVICEORDER A,NEWSERVICEORDERINFO B"
				+ "		WHERE A.ORDERID =B.ORDERID AND   B.FIELDID=3713 )A,"
				+ "     	(SELECT IMSI,MAX(COMPLETEDATE) COMPLETEDATE"
				+ "     	  from(SELECT A.SERVICEID,B.NEWVALUE IMSI,A.COMPLETEDATE"
				+ "     	            FROM SERVICEORDER A,SERVICEINFOCHANGEORDER B"
				+ "     	            WHERE A.ORDERID =B.ORDERID AND  B.FIELDID=3713"
				+ "     	            UNION"
				+ "     	            SELECT A.SERVICEID,B.FIELDVALUE IMSI,A.COMPLETEDATE"
				+ "     	            FROM SERVICEORDER A,NEWSERVICEORDERINFO B"
				+ "     	            WHERE A.ORDERID =B.ORDERID AND   B.FIELDID=3713)"
				+ "     	  GROUP BY IMSI )B "
				+ " WHERE A.IMSI=B.IMSI AND A.COMPLETEDATE =B.COMPLETEDATE ";

		Statement st = null;
		
		ResultSet rs = null;
		
		try{
			st = getConn2().createStatement();
			
			rs = st.executeQuery(sql);
			while(rs.next()){
				imsitoServiceID.put(rs.getString("IMSI"), rs.getString("SERVICEID"));
			}
		}finally{
			try {
				if(rs!=null)
					rs.close();
				if(st!=null)
					st.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return imsitoServiceID;
	}
	
	public Map<String,String> queryServiceIDtoIMSI() throws Exception{	
		Map<String,String> serviceIDtoIMSI = new HashMap<String,String>();
		
		System.out.println("setServiceIDtoIMSI...");
		String sql = " SELECT A.SERVICEID,A.IMSI "
				+ "FROM( "
				+ "		SELECT A.SERVICEID,B.NEWVALUE IMSI,A.COMPLETEDATE "
				+ "		FROM SERVICEORDER A,SERVICEINFOCHANGEORDER B "
				+ "		WHERE A.ORDERID =B.ORDERID AND  B.FIELDID=3713 "
				+ "		UNION "
				+ "		SELECT A.SERVICEID,B.FIELDVALUE IMSI,A.COMPLETEDATE "
				+ "		FROM SERVICEORDER A,NEWSERVICEORDERINFO B "
				+ "		WHERE A.ORDERID =B.ORDERID AND   B.FIELDID=3713 )A, "
				+ "		(	SELECT SERVICEID,MAX(COMPLETEDATE) COMPLETEDATE "
				+ "			from(	SELECT A.SERVICEID,B.NEWVALUE,A.COMPLETEDATE "
				+ "					FROM SERVICEORDER A,SERVICEINFOCHANGEORDER B "
				+ "					WHERE A.ORDERID =B.ORDERID AND  B.FIELDID=3713 "
				+ "					UNION "
				+ "					SELECT A.SERVICEID,B.FIELDVALUE,A.COMPLETEDATE "
				+ "					FROM SERVICEORDER A,NEWSERVICEORDERINFO B "
				+ "					WHERE A.ORDERID =B.ORDERID AND   B.FIELDID=3713) "
				+ "			GROUP BY SERVICEID )B "
				+ "		WHERE A.SERVICEID=B.SERVICEID AND A.COMPLETEDATE =B.COMPLETEDATE ";
		Statement st = null;
		ResultSet rs = null;
		try {
			st = getConn2().createStatement();
			
			rs = st.executeQuery(sql);

			while(rs.next()){
				serviceIDtoIMSI.put(rs.getString("SERVICEID"), rs.getString("IMSI"));
			}
		}finally{
			try {
				if(rs!=null)
					rs.close();
				if(st!=null)
					st.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//closeConnection();
		return serviceIDtoIMSI;
	}
	
}
