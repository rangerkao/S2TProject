package main.common.action;
 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import main.CRM.service.ExcelService;
import main.common.dao.CacheDao;

import org.apache.log4j.PropertyConfigurator;

public class CacheAction  extends BaseAction { 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	

	public static Properties props =new Properties();
	
	//IMSI SERVICEID Mapping
	static Map<String,String> serviceIDtoIMSI = new HashMap<String,String>();
	static Map<String,String> imsitoServiceID = new HashMap<String,String>();
	
	static Thread reloadThread;
		
	static connectionPooling cp;
	static connectionPooling cp2;
	static connectionPooling cp3;
	
	static Long ConnectionTime1;
	static Long ConnectionTime2;
	static Long ConnectionTime3;
	
	static Connection conn1;
	static Connection conn2;
	static Connection conn3;
	
	static{
		System.out.println("loadPropertiesing...!");
		try {
			loadProperties(CacheAction.class.getClassLoader().getResource("").toString().replace("file:", "").replace("%20", " "));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static boolean connectionWatcher=true;
	static long stopTime = 1000*60*10;
	
	static class ConnectTimeWatcherClass implements Runnable{

		@Override
		public void run() {
			while(connectionWatcher){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
				
				//System.out.println("watcher");
				Long now = System.currentTimeMillis();
				
				try {
					if(conn1!=null && now - ConnectionTime1>stopTime){
						try {
							conn1.close();
						} catch (SQLException e) {
						}
						conn1 = null;
					}
					if(conn2!=null && now - ConnectionTime2>stopTime){
						try {
							conn2.close();
						} catch (SQLException e) {
						}
						conn2 = null;
					}
					if(conn3!=null && now - ConnectionTime3>stopTime){
						try {
							conn3.close();
						} catch (SQLException e) {
						}
						conn3 = null;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	
	static void loadProperties(String path) throws FileNotFoundException, IOException{
		props.load(new FileInputStream(path+ "/resource/Log4j.properties"));
		PropertyConfigurator.configure(props);
		System.out.println("loadProperties success!");
		
		Thread t = new Thread(new ConnectTimeWatcherClass());
		t.start();
		
		
		/*cp = new connectionPooling("cp1",
				props.getProperty("Oracle.DriverClass"), 
				props.getProperty("Oracle.URL")
				.replace("{{Host}}", props.getProperty("Oracle.Host"))
				.replace("{{Port}}", props.getProperty("Oracle.Port"))
				.replace("{{ServiceName}}", 	(props.getProperty("Oracle.ServiceName")!=null?props.getProperty("Oracle.ServiceName"):""))
				.replace("{{SID}}", (props.getProperty("Oracle.SID")!=null?props.getProperty("Oracle.SID"):"")), 
				props.getProperty("Oracle.UserName"), props.getProperty("Oracle.PassWord"));
		System.out.println("creat connectionpooling1 success!");*/
		
		/*cp2 = new connectionPooling("cp2",
				props.getProperty("mBOSS.DriverClass"), 
				props.getProperty("mBOSS.URL")
				.replace("{{Host}}", props.getProperty("mBOSS.Host"))
				.replace("{{Port}}", props.getProperty("mBOSS.Port"))
				.replace("{{ServiceName}}", (props.getProperty("mBOSS.ServiceName")!=null?props.getProperty("mBOSS.ServiceName"):""))
				.replace("{{SID}}", (props.getProperty("mBOSS.SID")!=null?props.getProperty("mBOSS.SID"):"")), 
				props.getProperty("mBOSS.UserName"), 
				props.getProperty("mBOSS.PassWord"));
		System.out.println("creat connectionpooling2 success!");*/
		
		/*cp3 = new connectionPooling("cp3",
				props.getProperty("CRMDB.DriverClass"), 
				props.getProperty("CRMDB.URL")
				.replace("{{Host}}", props.getProperty("CRMDB.Host"))
				.replace("{{Port}}", props.getProperty("CRMDB.Port"))
				.replace("{{DBName}}", props.getProperty("CRMDB.DBName")), 
				props.getProperty("CRMDB.UserName"), props.getProperty("CRMDB.PassWord"));
		System.out.println("creat connectionpooling3 success!");*/
		
	}
	
	 private static Connection cerateConnection(String driver,String url,String user,String pass) throws ClassNotFoundException, SQLException{
		 System.out.println("create connction."+url);
		 Class.forName(driver);
		 return DriverManager.getConnection(url, user, pass); 
	 }
	
	
	public static Connection getConn1() throws ClassNotFoundException, SQLException{
		ConnectionTime1 = System.currentTimeMillis();
		if(conn1==null){
			conn1 = cerateConnection(
					props.getProperty("Oracle.DriverClass"), 
					props.getProperty("Oracle.URL")
					.replace("{{Host}}", props.getProperty("Oracle.Host"))
					.replace("{{Port}}", props.getProperty("Oracle.Port"))
					.replace("{{ServiceName}}", 	(props.getProperty("Oracle.ServiceName")!=null?props.getProperty("Oracle.ServiceName"):""))
					.replace("{{SID}}", (props.getProperty("Oracle.SID")!=null?props.getProperty("Oracle.SID"):"")), 
					props.getProperty("Oracle.UserName"), props.getProperty("Oracle.PassWord"));
		}else{
			Statement st = null;
			try {
				st = conn1.createStatement();
				st.executeQuery("select 'OK' ab  from dual");
			} catch (Exception e) {
				conn1.close();
				conn1 = cerateConnection(
						props.getProperty("Oracle.DriverClass"), 
						props.getProperty("Oracle.URL")
						.replace("{{Host}}", props.getProperty("Oracle.Host"))
						.replace("{{Port}}", props.getProperty("Oracle.Port"))
						.replace("{{ServiceName}}", 	(props.getProperty("Oracle.ServiceName")!=null?props.getProperty("Oracle.ServiceName"):""))
						.replace("{{SID}}", (props.getProperty("Oracle.SID")!=null?props.getProperty("Oracle.SID"):"")), 
						props.getProperty("Oracle.UserName"), props.getProperty("Oracle.PassWord"));
			}	finally{
				if(st!=null) st.close();
			}
		}
			
		return conn1;
	}
	public static void releaseConn1(Connection conn){
//		if(conn==null) return;
//		cp.releaseConnection(conn);
	}
	
	public static Connection getConn2() throws ClassNotFoundException, SQLException{
		ConnectionTime2 = System.currentTimeMillis();
		if(conn2==null){
			conn2 = cerateConnection(
					props.getProperty("mBOSS.DriverClass"), 
					props.getProperty("mBOSS.URL")
					.replace("{{Host}}", props.getProperty("mBOSS.Host"))
					.replace("{{Port}}", props.getProperty("mBOSS.Port"))
					.replace("{{ServiceName}}", (props.getProperty("mBOSS.ServiceName")!=null?props.getProperty("mBOSS.ServiceName"):""))
					.replace("{{SID}}", (props.getProperty("mBOSS.SID")!=null?props.getProperty("mBOSS.SID"):"")), 
					props.getProperty("mBOSS.UserName"), 
					props.getProperty("mBOSS.PassWord"));
		}else{
			Statement st = null;
			try {
				st = conn2.createStatement();
				st.executeQuery("select 'OK' ab  from dual");
			} catch (Exception e) {
				conn2.close();
				conn2 = cerateConnection(
						props.getProperty("mBOSS.DriverClass"), 
						props.getProperty("mBOSS.URL")
						.replace("{{Host}}", props.getProperty("mBOSS.Host"))
						.replace("{{Port}}", props.getProperty("mBOSS.Port"))
						.replace("{{ServiceName}}", (props.getProperty("mBOSS.ServiceName")!=null?props.getProperty("mBOSS.ServiceName"):""))
						.replace("{{SID}}", (props.getProperty("mBOSS.SID")!=null?props.getProperty("mBOSS.SID"):"")), 
						props.getProperty("mBOSS.UserName"), 
						props.getProperty("mBOSS.PassWord"));
			}finally{
				if(st!=null) st.close();
			}
		}
			
		return conn2;
	}
	public static void releaseConn2(Connection conn){
//		if(conn==null) return;
//		cp2.releaseConnection(conn);
	}
	
	public static Connection getConn3() throws ClassNotFoundException, SQLException{
		ConnectionTime3 = System.currentTimeMillis();
		if(conn3==null){
			conn3 = cerateConnection(
					props.getProperty("CRMDB.DriverClass"), 
					props.getProperty("CRMDB.URL")
					.replace("{{Host}}", props.getProperty("CRMDB.Host"))
					.replace("{{Port}}", props.getProperty("CRMDB.Port"))
					.replace("{{DBName}}", props.getProperty("CRMDB.DBName")), 
					props.getProperty("CRMDB.UserName"), props.getProperty("CRMDB.PassWord"));
		}else{
			Statement st = null;
			try {
				st = conn3.createStatement();
				st.executeQuery("select 'OK' ab  from dual");
			} catch (Exception e) {
				conn3.close();
				conn3 = cerateConnection(
						props.getProperty("CRMDB.DriverClass"), 
						props.getProperty("CRMDB.URL")
						.replace("{{Host}}", props.getProperty("CRMDB.Host"))
						.replace("{{Port}}", props.getProperty("CRMDB.Port"))
						.replace("{{DBName}}", props.getProperty("CRMDB.DBName")), 
						props.getProperty("CRMDB.UserName"), props.getProperty("CRMDB.PassWord"));
			}	finally{
				if(st!=null) st.close();
			}
		}
			
		return conn3;
	}
	public static void releaseConn3(Connection conn){
//		if(conn==null) return;
//		cp3.releaseConnection(conn);
	}
	
	
	//-------------flush Cache---------------------
	
	public String flushCache(){
		flushServiceIDwithIMSIMappingCache();
		
		return SUCCESS;
	}
		
	public static String flushServiceIDwithIMSIMappingCache(){
		serviceIDtoIMSI.clear();
		imsitoServiceID.clear();
		
		return SUCCESS;
	}
	
	//-------------reload Cache--------------------
	
	public String reloadCache(){
		reloadServiceIDwithIMSIMappingCache();

		System.out.println("reload End!");
		return SUCCESS;
	}
		
		
		
	public static String reloadServiceIDwithIMSIMappingCache(){
		flushServiceIDwithIMSIMappingCache();
		
		try {
			CacheDao cacheDao = new CacheDao();
			serviceIDtoIMSI = cacheDao.queryServiceIDtoIMSI();
			imsitoServiceID = cacheDao.queryIMSItoServiceID();
			sendMail("k1988242001@gmail.com","CRM Cache Reload!","S2T Cache Reload! At "+new Date());
			return setSuccess(SUCCESS);
		} catch (SQLException e) {
			StringWriter s = new StringWriter();
			e.printStackTrace(new PrintWriter(s));
			sendMail("k1988242001@gmail.com","DVRS Cache Reload Error!","S2T Cache Reload Error! At "+new Date()+"\n"+s);
			return errorHandle(e);
		} catch (Exception e) {
			StringWriter s = new StringWriter();
			e.printStackTrace(new PrintWriter(s));
			sendMail("k1988242001@gmail.com","DVRS Cache Reload Error!","S2T Cache Reload Error! At "+new Date()+"\n"+s);
			return errorHandle(e);
		}
	}
		
	public String createExcel(){
		try {
			ExcelService.createSubscribersExcel();
		} catch (Exception e) {
			sendMail("Create Excel Error","Create Excel Error at "+new Date(),"CRM_Web","k1988242001@gmail.com,ranger.kao@sim2travel.com");
		}
		
		return SUCCESS;
	}
	static boolean batchExcute = false;
	//----------------doBatch reload------------------
	public String batchReloadCache(){
		if(!batchExcute){
			Thread reloadThread = new BatchThread(24);
			reloadThread.start();
			batchExcute =true;
			result="Batch Started!";
		}else{
			result="Batch had been Started!";
		}
		
		return SUCCESS;
	}
		
	public class BatchThread extends Thread implements Runnable {
    	int ThreadPeriod =0;
    	public BatchThread(int ThreadPeriod){
    		this.ThreadPeriod=ThreadPeriod;
    	}
        public void run() { // implements Runnable run()
        	
        	Timer timer = new Timer();
        	if(ThreadPeriod==0){
        		timer.schedule(new taskClass(), 0);
        	}else{
        		Calendar calendar = Calendar.getInstance();
        		//以明天0點為第一次啟動，之後隔ThreadPeriod啟動一次
        		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)+1,0,0,0);
        		timer.schedule(new taskClass(), calendar.getTime(), ThreadPeriod*60*60*1000);
        	}
        }
    }
	public class taskClass extends TimerTask{

		public void run(){
			//reloadCache();
			try {
				//reloadServiceIDwithIMSIMappingCache();
				ExcelService.createSubscribersExcel();
			} catch (Exception e) {
				sendMail("Create Excel Error","Create Excel Error at "+new Date(),"CRM_Web","k1988242001@gmail.com,ranger.kao@sim2travel.com");
			}
		}
	}
	
	

	
	
	/**
	 * Error mail
	 * 
	 * @param content
	 */
	protected static void sendMail(String Receiver,String Subject,String Content){
	
		try {
			sendMail(Subject, Content, "DVRS UI", Receiver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	static void sendMail(String mailSubject,String mailContent,String mailSender,String mailReceiver){
		String [] cmd=new String[3];
		cmd[0]="/bin/bash";
		cmd[1]="-c";
		cmd[2]= "/bin/echo \""+mailContent+"\" | /bin/mail -s \""+mailSubject+"\" -r "+mailSender+" "+mailReceiver;

		try{
			Process p = Runtime.getRuntime().exec (cmd);
			p.waitFor();
			System.out.println("send mail cmd:"+cmd);
		}catch (Exception e){
			System.out.println("send mail fail:"+mailContent);
		}
	}


	public static Map<String, String> getImsitoServiceID() {
		return imsitoServiceID;
	}


	public static Map<String, String> getServiceIDtoIMSI() {
		return serviceIDtoIMSI;
	}
	
	 public static class connectionPooling {
		 
		 	final String cpName;
		 	final int min = 1;
		 	final int max = 20;
		 	int existed = 0;
		 	final int waitingLimit = 100; 
			long checkStartTime = new Date().getTime();
			List<Connection> connections = new ArrayList<Connection>();
			 
			String DriverClass;
			String URL;
			String UserName;
			String PassWord;
			 
			public connectionPooling(String cpName,String DriverClass,String URL,String UserName,String PassWord){
				 System.out.println("Initialize.");
				 this.cpName = cpName;
				 this.DriverClass = DriverClass;
				 this.URL = URL;
				 this.UserName = UserName;
				 this.PassWord = PassWord;

				 //確認Connection連線狀態
				 new Timer().schedule(new taskClass(cpName,connections),0,1000*60*30);
			 }
			
			public Connection getConnection() throws ClassNotFoundException, SQLException{
				return getConnection(0);
			}
			public Connection getConnection(int i) throws ClassNotFoundException, SQLException{
				 
				if(connections.size()<=min){
					if(existed >= max && i<waitingLimit){
						System.out.println(cpName+" wait connction. Existed:max="+existed+":"+max);
						try {
							Thread.sleep(1000*10);
						} catch (InterruptedException e) {}
						getConnection(i+10);
					}
					
					Connection c = cerateConnection();
					System.out.println(cpName+" created result . remain "+connections.size()+" connction. existed = "+existed);
					return c;
				}else{
					
					int index = connections.size()-1;
					//只將連結正常的傳出，不正常的移除
					Connection c= connections.remove(index);
					
					if(tryConnection(c)){
						System.out.println(cpName+" get connction index="+index+".");
						return c;
					}else{
						System.out.println(cpName+" connction index="+index+" is bad. continue find next.");
						existed--;
						return getConnection(0);
					}
				}
			 }
			
			
			public boolean tryConnection(Connection conn){
				Statement st = null;
				try {
					st = conn.createStatement();
					//System.out.println("check");
					st.execute("select 'OK' from dual");
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}finally{
					try {
						st.close();
					} catch (Exception e) {}
				}
				return true;
			}
			 
			public void releaseConnection(Connection conn){
				 System.out.println(cpName+" release connction.");
				 if(existed > max){
					try {
						conn.close();
					} catch (SQLException e) {}
				 }else{
					 connections.add(conn);
				 }
				 System.out.println(cpName+" remain "+connections.size()+" connction. existed = "+existed);
			 }
			 
			 private Connection cerateConnection() throws ClassNotFoundException, SQLException{
				 System.out.println("create connction.");
				 existed++;
				 Class.forName(DriverClass);
				 return DriverManager.getConnection(URL, UserName, PassWord); 
			 }
			 
			 public class taskClass extends TimerTask{
				 String cpName;
				 List<Connection> connections;
				 taskClass(String cpName,List<Connection> connections){
					 this.cpName = cpName;
					 this.connections = connections;
				 }
				@Override
				public void run() {
					System.out.println("check connction.");
					synchronized(connections){
						Iterator<Connection> it = connections.iterator();
						Statement st = null;
						while(it.hasNext()){
							Connection c = it.next();
							if(!tryConnection(c)){
								try {
									c.close();
								} catch (SQLException e1) {}
								it.remove();
								existed--;
							}
						}

						System.out.println(cpName+" check result . remain "+connections.size()+" connction. existed = "+existed);
					}
				}
			}
		 }
	
}
