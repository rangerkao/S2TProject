package main.common.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class Excel extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Excel() throws Exception {
		super();
	}
	private InputStream excelStream;  //輸出變量
	private static String excelFileName; //下載文件名稱

	String reportDataList;
	String reportColHead;
	String reportName;
	
	public String createExcelbyJSON(){
		
		System.out.println("createExcelbyJSON...");
		//proccess head
		List<Map<String, String>> head = null;
		List<Map<String, Object>> data = null;
		try {
			JSONArray jaHead = new JSONArray(java.net.URLDecoder.decode(reportColHead,"UTF-8"));
			head = new ArrayList<Map<String,String>>();
			
			for(int i = 0 ; i < jaHead.length() ; i++){
				JSONObject jo = jaHead.getJSONObject(i);
				Map<String,String> m = new HashMap<String,String>();
				
				String name = jo.getString("name");
				String colName = jo.getString("col");
				if(!"button".equals(name)){
					m.put("name", name);
					m.put("col", colName);
					head.add(m);
				}
			}
			
			data = new ArrayList<Map<String,Object>>();
			if(reportDataList==null || "".equals(reportDataList)||"[]".equals(reportDataList))
				return "success";
			
			JSONArray jaData = new JSONArray(java.net.URLDecoder.decode(reportDataList,"UTF-8"));
			for(int i = 0 ; i < jaData.length() ; i++){
				JSONObject jo = jaData.getJSONObject(i);			
				String [] names = JSONObject.getNames(jo);

				Map<String,Object> m = new HashMap<String,Object>();
			
				for(int j = 0 ; j< names.length ; j++){
					String key = names[j];
					String value = JSONObject.valueToString(jo.get(key));
					m.put(key, value);
				}
				data.add(m);
			}
			excelFileName = reportName+".xls";
			
			HSSFWorkbook wb = createExcel(head,data);
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();  
            wb.write(os);  
            byte[] fileContent = os.toByteArray();  

			excelStream = new ByteArrayInputStream(fileContent);  
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "success";
	}
	
	public static HSSFWorkbook createExcel(List<Map<String,String>> head,List<Map<String,Object>> data){
		InputStream inputStream = null;
		int rowN = 0;
		int sheetN = 0;
		HSSFWorkbook wb = null;
		//XSSFWorkbook 建立xls
		try {  
			//第一步，創建webbook文件
            wb = new HSSFWorkbook();  

            //第二步，添加sheet
            HSSFSheet sheet = wb.createSheet("sheet"+sheetN++);  
              
            //第四步，設定樣式
            HSSFCellStyle style = wb.createCellStyle();  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            //第五部，建立單元格
            HSSFCell cell;  

            //第三步添加表頭第0行
            HSSFRow row = sheet.createRow(rowN++);
            for(int k = 0 ; k < head.size() ; k++){
    			String name=head.get(k).get("name");
    			
				cell = row.createCell(k);  
                cell.setCellValue(name);  
                cell.setCellStyle(style); 
    		}		

  
          //第六部，寫入內容
            for (int i = 0; i < data.size(); i++) {  
                row = sheet.createRow(rowN++);  
                
                for(int j = 0 ; j < head.size() ; j++){
                	String col=head.get(j).get("col");
                	Map<String,Object> m = data.get(i);
                	if(data.get(i).get(col)!=null)
                		row.createCell(j).setCellValue(m.get(col).toString());
                	else
                		row.createCell(j).setCellValue("");
        		}	
                
                //避免超過65535限制row數
                if(i!=0&&i%65530==0){
                	sheet = wb.createSheet("sheet"+sheetN++);
                	rowN = 0;
                	//添加表頭
                	row = sheet.createRow(rowN++);
                	for(int k = 0 ; k < head.size() ; k++){
             			String name=head.get(k).get("name");
         				cell = row.createCell(k);  
                        cell.setCellValue(name);  
                        cell.setCellStyle(style); 
             		}		
                }
            }  

            
          //第七步，放置串流  
            /*ByteArrayOutputStream os = new ByteArrayOutputStream();  
            wb.write(os);  
            byte[] fileContent = os.toByteArray();  
            ByteArrayInputStream is = new ByteArrayInputStream(fileContent);  
            
            inputStream = is;   */         
            
            //reportName=java.net.URLDecoder.decode(reportName,"UTF-8");
            
            /*if(rName==null || "".equals(rName))
            	rName="report";            	
            
            excelFileName = rName+".xls"; //���W��
*/        }  
        catch(Exception e) {  
            e.printStackTrace();  
        }  
  
        return wb;  
	}

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportDataList() {
		return reportDataList;
	}

	public void setReportDataList(String reportDataList) {
		this.reportDataList = reportDataList;
	}

	public String getReportColHead() {
		return reportColHead;
	}

	public void setReportColHead(String reportColHead) {
		this.reportColHead = reportColHead;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	

}
