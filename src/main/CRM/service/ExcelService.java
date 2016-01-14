package main.CRM.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import main.CRM.dao.ExcelDao;
import main.common.action.Excel;
import main.common.service.BaseService;

@Service
public class ExcelService extends BaseService{
	
	public ExcelService() throws Exception {
		super();
	}
	@Resource
	ExcelDao excelDao;

	public InputStream  createSubscribersExcel() throws Exception{
		
		List<Map<String,Object>> data = excelDao.querySubscribers();
		
		List<Map<String,String>> head = new ArrayList<Map<String,String>>();
		head.add(mapSetting("審核日","VERIFIED_DATE"));
		head.add(mapSetting("中華主號","PARTNERMSISDN"));
		head.add(mapSetting("香港號碼","SERVICECODE"));
		head.add(mapSetting("ServiceID(帳務用)","SERVICEID"));
		head.add(mapSetting("開通時間","DATEACTIVATED"));
		head.add(mapSetting("退租時間","DATECANCELED"));
		head.add(mapSetting("客戶名稱","SUBS_NAME"));
		head.add(mapSetting("統一編號/身分證字號","SUBS_ID_TAXID"));
		head.add(mapSetting("公司戶負責人名稱","CHAIRMAN"));
		head.add(mapSetting("公司戶負責人身分證號","CHAIRMAN_ID"));
		head.add(mapSetting("聯絡電話","SUBS_PHONE"));
		head.add(mapSetting("生日","SUBS_BIRTHDAY"));
		head.add(mapSetting("戶籍地址","SUBS_PERMANENT_ADDRESS"));
		head.add(mapSetting("帳單地址","SUBS_BILLING_ADDRESS"));
		head.add(mapSetting("E-Mail","SUBS_EMAIL"));
		head.add(mapSetting("代辦處代號","AGENCY_ID"));
		head.add(mapSetting("其他備註","REMARK"));

		InputStream excelStream = Excel.createExcel(head, data);
		return excelStream;
	}

	private Map<String,String> mapSetting(String name,String col){
		Map<String, String> m = new HashMap<String,String>();
		
		m.put("name", name);
		m.put("col", col);
		
		return m;
	}

	public ExcelDao getExcelDao() {
		return excelDao;
	}

	public void setExcelDao(ExcelDao excelDao) {
		this.excelDao = excelDao;
	}
	
	
}
