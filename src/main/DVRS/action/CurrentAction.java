package main.DVRS.action;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import main.BaseAction;
import main.DVRS.bean.CurrentDay;
import main.DVRS.bean.CurrentMonth;
import main.DVRS.service.CurrentService;


public class CurrentAction extends BaseAction {

	public CurrentAction() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String imsi;
	String from;
	String to;
	String suspend;
	
	CurrentService currentControl=new CurrentService();
	
	
	public String queryCurrentMonth(){
		
		try {
			System.out.println("imsi:"+imsi+",from:"+from+",to:"+to+",suspend:"+suspend+","+new Date());
			List<CurrentMonth> list = currentControl.queryCurrentMonth(imsi,from.replace("-",""),to.replace("-",""),suspend);
			return setResult(SUCCESS, list);
		} catch (SQLException e) {
			e.printStackTrace();
			StringWriter s = new StringWriter();
			e.printStackTrace(new PrintWriter(s));
			return setFail(ERROR, s.toString());
		} catch (Exception e) {
			e.printStackTrace();
			StringWriter s = new StringWriter();
			e.printStackTrace(new PrintWriter(s));
			return setFail(ERROR, s.toString());
		}
	}
	
	public String queryCurrentDay(){
		
		try {
			System.out.println("imsi:"+imsi+",from:"+from+",to:"+to);
			List<CurrentDay> list = currentControl.queryCurrentDay(imsi,from.replace("-",""),to.replace("-",""));
			return setResult(SUCCESS, list);
		} catch (SQLException e) {
			e.printStackTrace();
			StringWriter s = new StringWriter();
			e.printStackTrace(new PrintWriter(s));
			return setFail(ERROR, s.toString());
		} catch (Exception e) {
			e.printStackTrace();
			StringWriter s = new StringWriter();
			e.printStackTrace(new PrintWriter(s));
			return setFail(ERROR, s.toString());
		}
	}
	
	/********************************************/

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSuspend() {
		return suspend;
	}

	public void setSuspend(String suspend) {
		this.suspend = suspend;
	}
	
	
	
}