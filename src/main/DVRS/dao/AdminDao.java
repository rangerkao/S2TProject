package main.DVRS.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import main.DVRS.bean.Admin;
import main.common.dao.BaseDao;
@Repository
public class AdminDao extends BaseDao {	

	public AdminDao() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public List<Admin> queryAdminList() throws Exception{
		String sql=
				"SELECT A.ID,A.ACCOUNT,A.PASSWORD,A.ROLE "
				+ "FROM HUR_ADMIN A ";
		List<Admin> list=new ArrayList<Admin>();
		

		Statement st = null;
		ResultSet rs = null;
		Connection conn = getConn1();
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Admin admin = new Admin();
				admin.setUserid(rs.getString("ID"));
				admin.setAccount(rs.getString("ACCOUNT"));
				admin.setPassword(rs.getString("PASSWORD"));
				admin.setRole(rs.getString("ROLE"));
				list.add(admin);
			} 
		} finally {
			if(st!=null) st.close();
			if(rs!=null) rs.close();
			closeConn1(conn);
		}
		
		//closeConnection();

		return list;
		
	}
	
	public Admin queryAdminByAccount(String account) throws Exception{
		Admin admin =null;
		String sql=
				"SELECT A.ID,A.ACCOUNT,A.PASSWORD,A.ROLE "
				+ "FROM HUR_ADMIN A "
				+ "WHERE A.ACCOUNT=? ";
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conn = getConn1();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, account);
			rs = pst.executeQuery();
			while (rs.next()) {
				admin = new Admin();
				admin.setUserid(rs.getString("ID"));
				admin.setAccount(rs.getString("ACCOUNT"));
				admin.setPassword(rs.getString("PASSWORD"));
				admin.setRole(rs.getString("ROLE"));
			}
			
		} finally {
			if(pst!=null) pst.close();
			if(rs!=null) rs.close();
			closeConn1(conn);
			//closeConnection();
		}
		return admin;
	}
	public int insert(Admin admin) throws Exception{
		String sql=
				"INSERT INTO HUR_ADMIN(ID,ACCOUNT,PASSWORD,ROLE,CREATE_DATE)"
				+ "VALUES(?,?,?,?,sysdate)";
		int result=0;
		PreparedStatement pst = null;
		Connection conn = getConn1();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, admin.getUserid());
			pst.setString(2, admin.getAccount());
			pst.setString(3, admin.getPassword());
			pst.setString(4, admin.getRole());
			result = pst.executeUpdate();
		} finally {
			if(pst!=null) pst.close();
			closeConn1(conn);
		}
		
		//closeConnection();

		
		return result;
	}
	public int update(Admin admin) throws Exception{
		String sql=
				"UPDATE HUR_ADMIN "
				+ "SET ID=?,PASSWORD=?,ROLE=?,UPDATE_DATE=sysdate "
				+ "WHERE ACCOUNT=?";
		int result=0;
		PreparedStatement pst = null;
		Connection conn = getConn1();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, admin.getUserid());
			pst.setString(2, admin.getPassword());
			pst.setString(3, admin.getRole());
			pst.setString(4, admin.getAccount());
			result = pst.executeUpdate();
		} finally {
			
			if(pst!=null) pst.close();
			closeConn1(conn);
		}
		//closeConnection();

		return result;
	}
	
	public int delete(Admin admin) throws Exception{
		String sql=
				"DELETE HUR_ADMIN "
				+ "WHERE ACCOUNT=?";
		int result=0;
		PreparedStatement pst = null;
		Connection conn = getConn1();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, admin.getAccount());
			result = pst.executeUpdate();
		} finally {
			if(pst!=null) pst.close();
			closeConn1(conn);
		}

		return result;
	}
}
