package com.galaxy.appupload.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;


public class RDataToJson {
	@SuppressWarnings("rawtypes")
	List list = new ArrayList();
	
	// ����json
	public String getDataJson(String code,String msg,String data){
		
		JSONObject json = new JSONObject();
		json.put("resultCode", code);
		//json.put("respMsg",msg);
		//base64����
		json.put("respMsg", Base64Coding.encode(msg));
		if(data.equals("")||data==null){
			json.put("data", list);
		}else{
			json.put("data",data);
		}
		return json.toString();
	}
}
