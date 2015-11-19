package main.CRM.action;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Date;

import main.CRM.service.ExcelService;
import main.common.action.BaseAction;

public class ExcelAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public ExcelAction() throws Exception{
		super();
	}
	private InputStream excelStream;  //輸出變量
	private static String excelFileName; //下載文件名稱

	ExcelService excelService = new ExcelService();
	
	public String createSubscribersExcel() throws SQLException{

		excelFileName = "customer"+new Date()+".xls";
		
		setExcelStream(excelService.createSubscribersExcel());

		return SUCCESS;
	}


	public String getExcelFileName() {
		return excelFileName;
	}


	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}


	public InputStream getExcelStream() {
		return excelStream;
	}


	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	
	
	
}
