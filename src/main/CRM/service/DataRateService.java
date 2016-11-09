package main.CRM.service;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import main.CRM.bean.DataRate;
import main.CRM.dao.DataRateDao;
import main.common.service.BaseService;
@Service
public class DataRateService extends BaseService{
	@Resource
	private DataRateDao dataRateDao;
	
	
	
	public DataRateService() throws Exception {
		super();
	}

	public List<DataRate> queryDataRateList() throws Exception{
		List<DataRate> result = dataRateDao.queryDataRateList();
		return result;
	}

	public DataRateDao getDataRateDao() {
		return dataRateDao;
	}

	public void setDataRateDao(DataRateDao dataRateDao) {
		this.dataRateDao = dataRateDao;
	}

	
}
