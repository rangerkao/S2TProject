package main.DVRS.service;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import main.DVRS.bean.CardChange;
import main.DVRS.dao.HistoryDao;
import main.common.service.BaseService;
@Service
public class HistoryService extends BaseService{

	@Resource
	HistoryDao historyDao;
	
	public HistoryService() throws Exception {
		super();
	}

	public List<CardChange> queryCardChangeHistory(String imsi) throws Exception{
		List<CardChange> result = historyDao.queryCardChangeHistory(imsi);
		return result;
	}
	
	public List<CardChange> queryNumberChangeHistory(String imsi) throws Exception{
		return historyDao.queryNumberChangeHistory(imsi);
	}

	public HistoryDao getHistoryDao() {
		return historyDao;
	}

	public void setHistoryDao(HistoryDao historyDao) {
		this.historyDao = historyDao;
	}
	
	
}
