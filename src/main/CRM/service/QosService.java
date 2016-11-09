package main.CRM.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import main.CRM.bean.QosBean;
import main.CRM.dao.QosDao;
import main.common.service.BaseService;

@Service
public class QosService extends BaseService {

	public QosService() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Resource
	QosDao qosDao;
	
	public List<QosBean> queryQos(String imsi,String msisdn,String activedate,String canceldate) throws Exception{
		
		List<QosBean> result =((imsi==null||"".equals(imsi)) && (msisdn==null||"".equals(msisdn))?
				qosDao.queryQosList():qosDao.queryQosList(imsi, msisdn,activedate, canceldate));
		return result;
		
		
	}

	public QosDao getQosDao() {
		return qosDao;
	}

	public void setQosDao(QosDao qosDao) {
		this.qosDao = qosDao;
	}
	
	

}
