package main.DVRS.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.DVRS.bean.CardChange;
import main.common.dao.BaseDao;

public class HistoryDao extends BaseDao {

	public HistoryDao() throws Exception {
		super();
	}

	public List<CardChange> queryCardChangeHistory(String imsi) throws SQLException{
		List<CardChange> result = new ArrayList<CardChange>();
		
		if(imsi!=null &&!"".equals(imsi)){
			imsi = imsi.replace("*", "%");
		}
		
		String sql = "SELECT C.SUBSIDIARYID, C.SERVICECODE, C.SERVICEID, C.STATUS, C.PRICEPLANID, "
				+ "A.COMPLETEDATE, A.OLDVALUE, A.NEWVALUE "
				+ "FROM SERVICEINFOCHANGEORDER A, SERVICEORDER B, SERVICE C "
				+ "WHERE A.FIELDID=3713 AND A.ORDERID=B.ORDERID "
				+ "AND B.SERVICEID=C.SERVICEID "
				+ "AND a.oldvalue <> a.newvalue "
				//20150506 add
				+ (imsi!=null &&!"".equals(imsi) ? "AND (A.OLDVALUE LIKE '"+imsi+"' OR A.NEWVALUE LIKE '"+imsi+"' ) " :"")
				+ "ORDER BY A.COMPLETEDATE DESC ";
		
		Statement st = null;
		ResultSet rs = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);		
			while(rs.next()){
				
				//20150506 mod
				/*String oldvalue = rs.getString("OLDVALUE");
				String newvalue = rs.getString("NEWVALUE");
				if(imsi!=null &&!"^$".equals(imsi)){
					if((oldvalue==null&&!newvalue.matches(imsi)) || (!oldvalue.matches(imsi)&& newvalue==null)||(!oldvalue.matches(imsi)&&!newvalue.matches(imsi)) )
						continue;
				}*/
				
				CardChange c = new CardChange();
				c.setOldvalue(rs.getString("OLDVALUE"));
				c.setNewvalue(rs.getString("NEWVALUE"));
				c.setStstus(rs.getString("STATUS"));
				c.setCompletedate(rs.getString("COMPLETEDATE"));
				result.add(c);
			}
		} finally{
			if(st!=null)
				st.close();
			if(rs!=null)
				rs.close();
		}
		closeConnection();
		return result;
	}
	
	public List<CardChange> queryNumberChangeHistory(String imsi) throws SQLException{
		List<CardChange> result = new ArrayList<CardChange>();
		
		if(imsi!=null &&!"".equals(imsi)){
			imsi = imsi.replace("*", "%");
		}
		
		String sql = "SELECT A.ORDERID, A.OLDVALUE, A.NEWVALUE, A.COMPLETEDATE, C.SERVICEID, C.SERVICECODE, C.STATUS "
				+ "FROM SERVICEPARAMVALUECHANGEORDER A, SERVICEORDER B, SERVICE C "
				+ "WHERE A.ORDERID=B.ORDERID AND B.SERVICEID=C.SERVICEID "
				+ "AND A.PARAMETERVALUEID=3792 AND C.SUBSIDIARYID=59 "
				//20150506 add
				+ (imsi!=null &&!"".equals(imsi) ? "AND (A.OLDVALUE LIKE '"+imsi+"' OR A.NEWVALUE LIKE '"+imsi+"' ) " :"")
				+ "ORDER BY A.ORDERID DESC ";
		
		Statement st = null;
		ResultSet rs = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				
				//20150506 mod
				/*String oldvalue = rs.getString("OLDVALUE");
				String newvalue = rs.getString("NEWVALUE");
				if(imsi!=null &&!"^$".equals(imsi)){
					if((oldvalue==null&&!newvalue.matches(imsi)) || (!oldvalue.matches(imsi)&& newvalue==null)||(!oldvalue.matches(imsi)&&!newvalue.matches(imsi)) )
						continue;
				}*/
				
				CardChange c = new CardChange();
				c.setOldvalue(rs.getString("OLDVALUE"));
				c.setNewvalue(rs.getString("NEWVALUE"));
				c.setStstus(rs.getString("STATUS"));
				c.setCompletedate(rs.getString("COMPLETEDATE"));
				result.add(c);
			}
		}  finally{
			if(st!=null)
				st.close();
			if(rs!=null)
				rs.close();
		}
		closeConnection();
		return result;
	}
	
}
