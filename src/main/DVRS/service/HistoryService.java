package main.DVRS.service;

import java.sql.SQLException;
import java.util.List;

import main.DVRS.bean.CardChange;
import main.DVRS.dao.HistoryDao;
import main.common.service.BaseService;

public class HistoryService extends BaseService{

	HistoryDao historyDao = new HistoryDao();
	
	public HistoryService() throws Exception {
		super();
	}

	public List<CardChange> queryCardChangeHistory(String imsi) throws SQLException{
		return historyDao.queryCardChangeHistory(imsi);
	}
	
	public List<CardChange> queryNumberChangeHistory(String imsi) throws SQLException{
		return historyDao.queryNumberChangeHistory(imsi);
		
	}
}
