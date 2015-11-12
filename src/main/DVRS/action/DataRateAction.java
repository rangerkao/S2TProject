package main.DVRS.action;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.DVRS.bean.DataRate;
import main.DVRS.service.DataRateService;
import main.common.action.BaseAction;

public class DataRateAction extends BaseAction{

	public DataRateAction() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<DataRate> dataRateList=new ArrayList<DataRate>();
	DataRateService dataRateService = new DataRateService();
	
	public String queryDataRate(){
		try {
			dataRateList=dataRateService.queryDataRateList();
			return setSuccess(dataRateList);
		} catch (SQLException e) {
			return errorHandle(e);
		} catch (Exception e) {
			return errorHandle(e);
		}
	}
}
