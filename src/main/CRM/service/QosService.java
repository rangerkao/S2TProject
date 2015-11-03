package main.CRM.service;

import java.sql.SQLException;
import java.util.List;

import main.CRM.bean.QosBean;
import main.CRM.dao.QosDao;
import main.common.service.BaseService;

public class QosService extends BaseService {

	public QosService() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	QosDao qosDao = new QosDao();
	
	public List<QosBean> queryQos(String imsi,String msisdn) throws SQLException{
		if((imsi==null||"".equals(imsi)) && (msisdn==null||"".equals(msisdn)))
				return qosDao.queryQosList();
		else
			return qosDao.queryQosList(imsi, msisdn);
	}

}
