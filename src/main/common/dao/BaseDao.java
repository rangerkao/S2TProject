package main.common.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import main.CRM.bean.SMS;
import main.common.action.CacheAction;

public class BaseDao {

	protected static Properties props =null;
	protected static Connection conn=null;
	protected static Connection conn2=null;
	protected static Connection conn3=null;
	
	public BaseDao() throws Exception{
		props=CacheAction.props;
	}
	
	protected Connection getConn1() throws Exception{
		Statement st = null;
		try {
			st = conn.createStatement();
		} catch (Exception e) {
			createConnection();
		}finally{
			try {
				if(st!=null)
					st.close();
			} catch (Exception e) {
			}
		}
		return conn;
	}
	protected Connection getConn2() throws Exception{
		Statement st = null;
		try {
			st = conn2.createStatement();
		} catch (Exception e) {
			createConnection2();
		}finally{
			try {
				if(st!=null)
					st.close();
			} catch (Exception e) {
			
			}
		}
		return conn2;
	}
	protected static Connection getConn3() throws Exception{
		Statement st = null;
		try {
			st = conn3.createStatement();
		} catch (Exception e) {
			createConnection3();
		}finally{
			try {
				if(st!=null)
					st.close();
			} catch (Exception e) {
			
			}
		}
		return conn3;
	}
	protected void createConnection() throws Exception{
		conn=connectDB();
		System.out.println("Create connect1!");
	}
	protected void createConnection2() throws Exception{
		conn2=connectDB2();
		System.out.println("Create connect2!");
	}
	protected static void createConnection3() throws Exception{
		conn3=connectDB3();
		System.out.println("Create connect3!");
	}
	protected void closeConnection() throws SQLException{
		if(conn!=null)
			conn.close();
		if(conn2!=null)
			conn2.close();
		if(conn3!=null)
			conn3.close();
		System.out.println("Close connect!");
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
		protected static Connection connectDB2() throws Exception {
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
		protected static Connection connectDB3() throws Exception {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn3=null;
			String url=props.getProperty("CRMDB.URL")
					.replace("{{Host}}", props.getProperty("CRMDB.Host"))
					.replace("{{Port}}", props.getProperty("CRMDB.Port"))
					.replace("{{DBName}}", props.getProperty("CRMDB.DBName"));
			
			conn3=connDB(props.getProperty("CRMDB.DriverClass"), url, 
					props.getProperty("CRMDB.UserName"), 
					props.getProperty("CRMDB.PassWord")
					);
				if(conn3==null){
					throw new Exception("DB Connect3 null !");
				}
			return conn3;
		}
		
		public static Connection connDB(String DriverClass, String URL,
				String UserName, String PassWord) throws ClassNotFoundException, SQLException {
			Connection conn = null;

				Class.forName(DriverClass);
				conn = DriverManager.getConnection(URL, UserName, PassWord);
			return conn;
		}
		
		public long getTimeValue(String s) throws ParseException{
			Long value = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			value=sdf.parse(s).getTime();
			return value;
		}
		
		
		public void sort(List<SMS> l) throws ParseException{
			
			int size = l.size();
			
			for(int i = 0 ; i<size;i++){
				for(int j = 1 ;j<size;j++){
					swap(l,j-1,getTimeValue(l.get(j-1).getSendTime()),j,getTimeValue(l.get(j).getSendTime()));
				}
			}
			
		}
		
		
		public void swap(List<SMS> l,int i ,long vi,int j,long vj){
			
			if(vi<vj){
				SMS io = l.get(i);
				SMS jo = l.get(j);
				l.set(i, jo);
				l.set(j, io);
			}
		}
		
		public String processData(String data){
			return (data==null?" ":data);
		}
		public String processEncodeData(String data,String sCharSet,String dCharSet){
			if(data==null)
				return " ";
			
			
			try {
				if(sCharSet==null || "".equals(sCharSet))
					data = new String(data.getBytes(),dCharSet);
				else
					data = new String(data.getBytes(sCharSet),dCharSet);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			return data;
		}
		
		
	
	
}
