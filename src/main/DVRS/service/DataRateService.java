package main.DVRS.service;

import java.sql.SQLException;
import java.util.List;

import main.DVRS.bean.DataRate;
import main.DVRS.dao.DataRateDao;
import main.common.service.BaseService;

public class DataRateService extends BaseService{
	
	private DataRateDao dataRateDao = new DataRateDao();
	
	
	
	public DataRateService() throws Exception {
		super();
	}

	public List<DataRate> queryDataRateList() throws SQLException{
		return dataRateDao.queryDataRateList();
	}

}
