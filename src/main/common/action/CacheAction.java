package main.common.action;
 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;









import main.common.dao.CacheDao;

import org.apache.log4j.PropertyConfigurator;

public class CacheAction  extends BaseAction{ 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Properties props =new Properties();
	
	//IMSI SERVICEID Mapping
	static Map<String,String> serviceIDtoIMSI = new HashMap<String,String>();
	static Map<String,String> imsitoServiceID = new HashMap<String,String>();
	
	static Thread reloadThread;
		
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
	
	static void loadProperties(String path) throws FileNotFoundException, IOException{
		props.load(new FileInputStream(path+ "/resource/Log4j.properties"));
		PropertyConfigurator.configure(props);
		System.out.println("loadProperties success!");
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
			sendMail("k1988242001@gmail.com","DVRS Cache Reload!","S2T Cache Reload! At "+new Date());
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
		
	//----------------doBatch reload------------------
	public String batchReloadCache(){
		if(reloadThread==null || reloadThread.isInterrupted() || !reloadThread.isAlive()){
			Thread reloadThread = new BatchThread(24);
			reloadThread.setDaemon(true);
			reloadThread.start();
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
			reloadCache();     
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
	
}
