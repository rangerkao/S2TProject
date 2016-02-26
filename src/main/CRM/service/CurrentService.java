package main.CRM.service ;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import main.CRM.bean.CurrentMonth;
import main.CRM.dao.CurrentDao;
import main.DVRS.bean.CurrentDay;
import main.common.service.BaseService;

@Service
public class CurrentService extends BaseService {

	@Resource
	CurrentDao currentDao;
	
	public CurrentService() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public List<CurrentMonth> queryCurrentMonth() throws Exception{
		return currentDao.queryCurrentMonth();
	}
	
	public List<CurrentMonth> queryCurrentMonth(String imsi,String from,String to,String suspend) throws Exception{
		System.out.println("ctr queryCurrentMonth..."+","+new Date());
		if((imsi==null || "".equals(imsi))&&(from==null || "".equals(from))&&(to==null || "".equals(to))&&("".equals(suspend)||suspend==null))
			return currentDao.queryCurrentMonth();
		
		return currentDao.queryCurrentMonth(imsi,from,to,suspend);
	}
	
	public List<CurrentDay> queryCurrentDay() throws Exception{
		return currentDao.queryCurrentDay();
	}
	
	public List<CurrentDay> queryCurrentDay(String imsi,String from,String to) throws Exception{
		if((imsi==null || "".equals(imsi))&&(from==null || "".equals(from))&&(to==null || "".equals(to)))
			return currentDao.queryCurrentDay();
		
		return currentDao.queryCurrentDay(imsi,from,to);
	}


	public CurrentDao getCurrentDao() {
		return currentDao;
	}


	public void setCurrentDao(CurrentDao currentDao) {
		this.currentDao = currentDao;
	}

	
	
	
}
