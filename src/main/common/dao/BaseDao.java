package main.common.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import main.common.action.CacheAction;

public class BaseDao {

	protected String sql="";
	protected Properties props =null;
	protected Connection conn=null;
	protected Connection conn2=null;

	public BaseDao() throws Exception{
		props=CacheAction.props;
		createConnection();
	}
	protected void createConnection() throws Exception{
		conn=connectDB();
		conn2=connectDB2();
		System.out.print("Create connect!");
	}
	protected void closeConnection() throws SQLException{
		conn.close();
		conn2.close();
		System.out.print("Close connect!");
	}
		//---------------建立DB 連結 conn1 主資料庫、conn2 Mboss----

		protected Connection connectDB() throws Exception{
			Connection conn = null;
			String url=props.getProperty("Oracle.URL")
					.replace("{{Host}}", props.getProperty("Oracle.Host"))
					.replace("{{Port}}", props.getProperty("Oracle.Port"))
					.replace("{{ServiceName}}", 
							(props.getProperty("Oracle.ServiceName")!=null?props.getProperty("Oracle.ServiceName"):""))
					.replace("{{SID}}", (props.getProperty("Oracle.SID")!=null?props.getProperty("Oracle.SID"):""));
			
			conn=connDB(props.getProperty("Oracle.DriverClass"), url, 
					props.getProperty("Oracle.UserName"), 
					props.getProperty("Oracle.PassWord")
					);
			
				if(conn==null){
					throw new Exception("DB Connect null !");
				}
			return conn;
		}
		protected Connection connectDB2() throws Exception {
			Connection conn2=null;
			String url=props.getProperty("mBOSS.URL")
					.replace("{{Host}}", props.getProperty("mBOSS.Host"))
					.replace("{{Port}}", props.getProperty("mBOSS.Port"))
					.replace("{{ServiceName}}", 
							(props.getProperty("mBOSS.ServiceName")!=null?props.getProperty("mBOSS.ServiceName"):""))
					.replace("{{SID}}", (props.getProperty("mBOSS.SID")!=null?props.getProperty("mBOSS.SID"):""));
			conn2=connDB(props.getProperty("mBOSS.DriverClass"), url, 
					props.getProperty("mBOSS.UserName"), 
					props.getProperty("mBOSS.PassWord")
					);
				if(conn2==null){
					throw new Exception("DB Connect2 null !");
				}
			return conn2;
		}
		
		public Connection connDB(String DriverClass, String URL,
				String UserName, String PassWord) throws ClassNotFoundException, SQLException {
			Connection conn = null;

				Class.forName(DriverClass);
				conn = DriverManager.getConnection(URL, UserName, PassWord);
			return conn;
		}
	
	
}
