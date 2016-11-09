package main.CRM.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import main.CRM.dao.ExcelDao;
import main.common.action.Excel;
import main.common.service.BaseService;


public class ExcelService extends BaseService {
	
	static 	ExcelDao excelDao;
	static{
		try {
			excelDao = new ExcelDao();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 
	public static InputStream  getExcel() throws Exception{
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String filePath = ExcelService.class.getClassLoader().getResource("").toString().replace("file:", "").replace("%20", " ");
		String fileName = sdf.format(new Date())+".xls";
		File newfile = new File(filePath+fileName);
		if(!newfile.exists())
			return null;
		FileInputStream in = new FileInputStream(newfile);*/
		
		return createSubscribersExcel();
	}

	public static InputStream  createSubscribersExcel() throws Exception{
		
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

		HSSFWorkbook wb = Excel.createExcel(head, data);
		
		System.out.println("get excel data end.");
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();  
        wb.write(os);  
        byte[] fileContent = os.toByteArray();  
        ByteArrayInputStream is = new ByteArrayInputStream(fileContent);  
        
		
		/*try {
			//20160524 add
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String filePath = ExcelService.class.getClassLoader().getResource("").toString().replace("file:", "").replace("%20", " ");
			System.out.println("filePath:"+filePath);
			Calendar cal = Calendar.getInstance();
			String fileName = sdf.format(cal.getTime())+".xls";
			cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR)-1);
			String pastName = sdf.format(cal.getTime())+".xls"; 
			
			
			System.out.println("delete past file."+filePath+pastName);
			File pastfile = new File(filePath+pastName);
			pastfile.delete();
			
			System.out.println("create new file"+filePath+fileName);
			File newfile = new File(filePath+fileName);
			FileOutputStream out = new FileOutputStream(newfile);
			wb.write(out);

			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
		return is;
	}

	private static Map<String,String> mapSetting(String name,String col){
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
