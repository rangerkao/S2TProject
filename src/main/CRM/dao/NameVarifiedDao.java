package main.CRM.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import main.CRM.bean.NameVarified;

@Repository
public class NameVarifiedDao extends CRMBaseDao{

	public NameVarifiedDao() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public NameVarified queeryNameVarifiedData(String serviceId) throws Exception{
		NameVarified result = new NameVarified();
		
		String sql = "select A.SERVICEID,A.NAME,A.ID,A.TYPE,A.REMARK,DATE_FORMAT(A.create_time,'%Y/%m/%d %h:%i:%s') TIME "
				+ "from CRM_DB.CRM_NAME_VERIFIED A "
				+ "where A.serviceid = "+serviceId+" "
				+ "and status = '1' ";
		
		Statement st = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn =getConn3();
			st = conn.createStatement();
			System.out.println("sql : "+sql);
			rs = st.executeQuery(sql);
			while(rs.next()){
				result.setServiceid(rs.getString("SERVICEID"));
				result.setName(rs.getString("NAME"));
				result.setId(rs.getString("ID"));
				result.setType(rs.getString("TYPE"));
				result.setRemark(rs.getString("REMARK"));
				result.setCreateTime(rs.getString("TIME"));
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

		String sql ="update CRM_DB.CRM_NAME_VERIFIED set status = '0' where serviceid = "+c.getServiceid()+" ";
		
		Statement st = null;
		Connection conn = null;
		try {
			conn =getConn3();
			st = conn.createStatement();
			
			
			System.out.println("sql : "+sql);
			st.executeUpdate(sql);
			
			sql = "insert into CRM_DB.CRM_NAME_VERIFIED(serviceid,name,id,type,remark) "
					+ "values("+c.getServiceid()+",'"+c.getName()+"','"+c.getId()+"','"+c.getType()+"','"+c.getRemark()+"')";
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
