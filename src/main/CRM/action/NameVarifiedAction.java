package main.CRM.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONObject;

import main.CRM.bean.NameVarified;
import main.CRM.service.DataRateService;
import main.CRM.service.NameVarifiedService;
import main.common.action.BaseAction;

public class NameVarifiedAction extends BaseAction{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Resource
	NameVarifiedService nameVarifiedService;

	String input;
	NameVarified c = new NameVarified();

	public String insertOrModifiedNameVarifiedData(){
		System.out.println(input);
		try {
			Map<String,String> p =new HashMap<String,String>();
			JSONObject jo = jsonToJSONObject(input);
			for(String name:JSONObject.getNames(jo)){
				p.put(name, jo.getString(name));
			}
			reflectSet(c,p);
			
			
			nameVarifiedService.insertOrModifiedNameVarifiedData(c);
			setSuccess("Insert Success!");
		} catch (Exception e) {
			return errorHandle(e);
		}
		
		return setSuccess("Insert Success.");
	}
	
	
	public String queeryNameVarifiedData(){
		
		try {
			NameVarified result = nameVarifiedService.queeryNameVarifiedData(input);
			setSuccess(result);
			
		} catch (Exception e) {
			return errorHandle(e);
		}
	
		return SUCCESS;
	}


	public NameVarifiedService getNameVarifiedService() {
		return nameVarifiedService;
	}


	public void setNameVarifiedService(NameVarifiedService nameVarifiedService) {
		this.nameVarifiedService = nameVarifiedService;
	}


	public String getInput() {
		return input;
	}


	public void setInput(String input) {
		this.input = input;
	}

}
