package main.common.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.common.bean.User;

public class LoginDao extends BaseDao{

	public LoginDao() throws Exception{
		// TODO Auto-generated constructor stub
	}

	
	public User queryUser(String account) throws SQLException{
		
		User user = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.createStatement();
			
			String sql = "SELECT A.ACCOUNT,A.PASSWORD,A.ROLE FROM CRM_USER A WHERE A.ACCOUNT = '"+account+"' ";
			
			rs = st.executeQuery(sql);

			while(rs.next()){
				user = new User();
				user.setAccount(rs.getString("ACCOUNT"));
				user.setPassword(rs.getString("PASSWORD"));
				user.setRole(rs.getString("ROLE"));
			}
			
		} finally{
			try {
				if(st!=null)
					st.close();
				if(rs!=null)
					rs.close();
			} catch (SQLException e) {
			}
		}
		
		closeConnection();
		return user;
	}
}
