package main.CRM.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import main.CRM.bean.NameVarified;

@Repository
public class NameVarifiedDao extends CRMBaseDao{

	public NameVarifiedDao() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public List<NameVarified> queeryNameVarifiedData(String serviceId) throws Exception{
		List<NameVarified> result = new ArrayList<NameVarified>();
		
		String sql = "select A.SERVICEID,A.NAME,A.ID,A.TYPE,A.REMARK,DATE_FORMAT(A.create_time,'%Y/%m/%d %h:%i:%s') TIME,A.send_date,A.chtMsisdn,A.chinaMsisdn,A.status "
				+ "from CRM_DB.CRM_NAME_VERIFIED A "
				+ "where A.serviceid = "+serviceId+" order by A.create_time desc";
				//+ "and status = '1' ";
		
		Statement st = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn =getConn3();
			st = conn.createStatement();
			System.out.println("sql : "+sql);
			rs = st.executeQuery(sql);
			while(rs.next()){
				NameVarified c = new NameVarified();
				c.setServiceid(processData(rs.getString("SERVICEID")));
				c.setName(processData(rs.getString("NAME")));
				c.setId(processData(rs.getString("ID")));
				c.setType(processData(rs.getString("TYPE")));
				c.setRemark(processData(rs.getString("REMARK")));
				c.setCreateTime(processData(rs.getString("TIME")));
				c.setSendDate(processData(rs.getString("send_date")));
				c.setChinaMsisdn(processData(rs.getString("chinaMsisdn")));
				c.setChtMsisdn(processData(rs.getString("chtMsisdn")));
				c.setStatus(rs.getString("status"));
				result.add(c);
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
	
	public String  insertOrModifiedNameVarifiedData(NameVarified c) throws Exception{

		//查詢現行(status=1)的serviceID與中國號對應，如果不相符則新增資料
		String sql ="select count(1) CD from CRM_DB.CRM_NAME_VERIFIED where serviceid = "+c.getServiceid()+" and chinaMsisdn = '"+c.getChinaMsisdn()+"' and status = '1' ";
		
		Statement st = null,st2 = null;
		ResultSet rs = null;
		Connection conn = null,conn2 = null;
		boolean insertData = true;
		try {
			conn =getConn3();
			conn2 = getConn1();
			st = conn.createStatement();
			st2 = conn2.createStatement();
			System.out.println("sql : "+sql);
			rs = st.executeQuery(sql);
			if(rs.next()){
				if(rs.getInt("CD")!=0){
					insertData = false;
				}
			}
			rs.close();
			
			/*String chinaMsisdn = null,chtMsisdn = null;
			//查詢中華門號
			sql = "select A.SERVICEID,B.VLN,A.Followmenumber "
					+ "from FOLLOWMEDATA A,VLNNUMBER B "
					+ "where A.serviceid = B.serviceid and B.VLN like '861%' and B.VLNTYPE = 1 and a.serviceid = "+c.getServiceid()+" ";
			
			rs = st2.executeQuery(sql);
			
			if(rs.next()){
				chinaMsisdn = rs.getString("VLN");
				chtMsisdn = rs.getString("Followmenumber");
			}
			
			if(chinaMsisdn==null || "".equals(chinaMsisdn)||chtMsisdn==null||"".equals(chtMsisdn)){
				throw new Exception("查無有效的中國號或門號");
			}
			
			8525609nnnn->861841103nnnn
			8526640nnnn->861391048nnnn
			8526947nnnn->861391037nnn
			
			rs.close();*/
			
			if(insertData){
				//將過去的資料status設為0
				sql = "update CRM_DB.CRM_NAME_VERIFIED set status = '0' where serviceid = '"+c.getServiceid()+"' ";
				System.out.println("sql : "+sql);
				st.executeUpdate(sql);
				
				//insert 新資料
				sql = "insert into CRM_DB.CRM_NAME_VERIFIED(serviceid,name,id,type,remark,chtMsisdn,chinaMsisdn) "
					+ "values("+c.getServiceid()+",'"+c.getName()+"','"+c.getId()+"','"+c.getType()+"','"+processData(c.getRemark()).trim()+"','"+c.getChtMsisdn()+"','"+c.getChinaMsisdn()+"')";
			}
			else
				sql = "update CRM_DB.CRM_NAME_VERIFIED set name='"+c.getName()+"',id='"+c.getId()+"',type='"+c.getType()
				+"',remark='"+processData(c.getRemark()).trim()+"',chtMsisdn='"+c.getChtMsisdn()+"',chinaMsisdn='"+c.getChinaMsisdn()+"'  "
						+ "where serviceid = "+c.getServiceid()+" and status = '1' ";
			
			System.out.println("sql : "+sql);
			st.executeUpdate(sql);
			
		}finally{
			try {
				if(st!=null) st.close();
				if(rs!=null) rs.close();

			} catch (Exception e) { }
			closeConn3(conn);//closeConnection();
		}

		return "SUCCESS";
	}
	
	public String  cancelNameVarifiedDataSendDate(String serviceid) throws Exception{

		String  sql = "update CRM_DB.CRM_NAME_VERIFIED set send_Date=null where serviceid = "+serviceid+" and status = '1' ";
		
		Statement st = null;
		Connection conn = null;

		try {
			conn =getConn3();
			st = conn.createStatement();
			System.out.println("sql : "+sql);
			st.executeUpdate(sql);
			
		}finally{
			try {
				if(st!=null) st.close();

			} catch (Exception e) { }
			closeConn3(conn);//closeConnection();
		}

		return "SUCCESS";
	}

}
