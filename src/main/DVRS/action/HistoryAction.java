package main.DVRS.action;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import main.BaseAction;
import main.DVRS.bean.CardChange;
import main.DVRS.service.HistoryService;

public class HistoryAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String imsi;
	
	HistoryService historyService = new HistoryService();
	
	
	public HistoryAction() throws Exception{
		super();
		// TODO Auto-generated constructor stub
	}

	public String queryCardChangeHistory() throws SQLException{
		try{
			System.out.println("imsi:"+imsi+","+new Date());
			List<CardChange> list = historyService.queryCardChangeHistory(imsi);
			return setResult(SUCCESS, list);
		} catch (SQLException e) {
			return errorHandle(e);
		} catch (Exception e) {
			return errorHandle(e);
		}
	}
	
	public String queryNumberChangeHistory(){
		try{
			System.out.println("imsi:"+imsi+","+new Date());
			List<CardChange> list = historyService.queryNumberChangeHistory(imsi);
			return setResult(SUCCESS, list);
		} catch (SQLException e) {
			return errorHandle(e);
		} catch (Exception e) {
			return errorHandle(e);
		}
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	
	
}
