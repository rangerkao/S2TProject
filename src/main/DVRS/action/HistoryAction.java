package main.DVRS.action;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import main.DVRS.bean.CardChange;
import main.DVRS.service.HistoryService;
import main.common.action.BaseAction;

public class HistoryAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String imsi;
	
	@Resource
	HistoryService historyService;
	
	
	public HistoryAction() throws Exception{
		super();
		// TODO Auto-generated constructor stub
	}

	public String queryCardChangeHistory() throws SQLException{
		try{
			System.out.println("imsi:"+imsi+","+new Date());
			List<CardChange> list = historyService.queryCardChangeHistory(imsi);
			return setSuccess(list);
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
			return setSuccess(list);
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

	public HistoryService getHistoryService() {
		return historyService;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}
	
	
	
}
