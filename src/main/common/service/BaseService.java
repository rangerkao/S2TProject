package main.common.service;



import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class BaseService {

	protected String beanToJSONArray(List list){
		JSONArray jo = (JSONArray) JSONObject.wrap(list);
		return jo.toString();
	}
	protected String beanToJSONObject(Object object){
		JSONObject jo = (JSONObject) JSONObject.wrap(object);
		return jo.toString();
	}
}
