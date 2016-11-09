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
		List<CurrentMonth> result = currentDao.queryCurrentMonth();
		return result;
	}
	
	public List<CurrentMonth> queryCurrentMonth(String serviceId,String from,String to,String suspend) throws Exception{
		System.out.println("ctr queryCurrentMonth..."+","+new Date());
		List<CurrentMonth> result = 
				((serviceId==null || "".equals(serviceId))&&(from==null || "".equals(from))&&(to==null || "".equals(to))&&("".equals(suspend)||suspend==null)?
						currentDao.queryCurrentMonth() : currentDao.queryCurrentMonth(serviceId,from,to,suspend));
		return result;
	}
	
	public List<CurrentDay> queryCurrentDay() throws Exception{		
		List<CurrentDay> result = currentDao.queryCurrentDay();
		return result;
	}
	
	public List<CurrentDay> queryCurrentDay(String serviceId,String from,String to) throws Exception{
		List<CurrentDay> result = ((serviceId==null || "".equals(serviceId))&&(from==null || "".equals(from))&&(to==null || "".equals(to))?
				currentDao.queryCurrentDay() : currentDao.queryCurrentDay(serviceId,from,to));
		return result;
	}


	public CurrentDao getCurrentDao() {
		return currentDao;
	}


	public void setCurrentDao(CurrentDao currentDao) {
		this.currentDao = currentDao;
	}

	
	
	
}
