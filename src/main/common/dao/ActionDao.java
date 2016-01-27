package main.common.dao;

import java.sql.SQLException;
import java.sql.Statement;

public class ActionDao extends BaseDao{

	public ActionDao() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static void insertAction(String account,String params,String function,String result) throws SQLException, Exception{
		String sql = "Insert into CRM_ACTION_LOG (ACCOUNT,PARAMS,FUNCTION,RESULT,CREATEDATE) "
				+ "VALUES('"+account+"','"+params+"','"+function+"','"+result+"',now())";
		Statement st = null;

		try{
			st = getConn3().createStatement();
			System.out.println("Action Log sql:"+sql);
			st.execute(sql);
		}finally{
			if(st!=null)
				st.close();
		}
	}

}
