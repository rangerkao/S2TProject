package main.CRM.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONObject;

import main.CRM.bean.NameVarified;
import main.CRM.bean.NameVarifiedSet;
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
	String type;
	NameVarified c = new NameVarified();	
	
	public String queeryNameVarifiedData(){
		System.out.println("input:"+input+":type:"+type);
		try {
			if(type!=null && !"".equals(type)){
				List<NameVarified> result = nameVarifiedService.queeryNameVarifiedData(input,type);
				setSuccess(result);
			}else{
				String [] param = input.split(",");
				NameVarified c = new NameVarified();
				if(param.length>0) c.setServiceid(param[0]);
				if(param.length>1) c.setMsisdn(param[1]);
				if(param.length>2) c.setVln(param[2]);
				
				
				List<NameVarified>  result = nameVarifiedService.queeryNameVarifiedData(c,input);
				setSuccess(result);
			}
			
		} catch (Exception e) {
			return errorHandle(e);
		}
	
		return SUCCESS;
	}
	
	public String queeryNameVarifiedDataSameMsisdn(){
		System.out.println("input:"+input);
		try {
				List<NameVarified>  result = nameVarifiedService.queeryNameVarifiedDataSameMsisdn(input);
				setSuccess(result);
		} catch (Exception e) {
			return errorHandle(e);
		}
	
		return SUCCESS;
	}
	
	public String queryVLN(){
		System.out.println("input:"+input+":type:"+type);
		try {				
				List<String>  result = nameVarifiedService.queryVLN(input);
				setSuccess(result);			
		} catch (Exception e) {
			return errorHandle(e);
		}
	
		return SUCCESS;
	}
	
	
	public String updateNameVarifiedData(){
		System.out.println(input);
		try {
			Map<String,String> p =new HashMap<String,String>();
			JSONObject jo = jsonToJSONObject(input);
			for(String name:JSONObject.getNames(jo)){
				p.put(name, jo.getString(name));
			}
			reflectSet(c,p);
			setSuccess(nameVarifiedService.updateNameVarifiedData(c,input));
		} catch (Exception e) {
			return errorHandle(e);
		}
		
		return setSuccess("Insert Success.");
	}

	
	public String addNameVarifiedData(){
		
		try {
			
			Map<String,String> p =new HashMap<String,String>();
			JSONObject jo = jsonToJSONObject(input);
			for(String name:JSONObject.getNames(jo)){
				p.put(name, jo.getString(name));
			}
			reflectSet(c,p);
			String result = nameVarifiedService.addNameVarifiedData(c,input);
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


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	
	

}
