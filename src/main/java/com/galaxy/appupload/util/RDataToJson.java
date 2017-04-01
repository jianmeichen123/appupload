package com.galaxy.appupload.util;

import net.sf.json.JSONObject;


public class RDataToJson {
	//List list = new ArrayList();
	
	// 返回json
	public String getDataJson(String code,String msg,String data){
		
		JSONObject json = new JSONObject();
		json.put("resultCode", code);
		//json.put("respMsg",msg);
		//base64加密
		json.put("respMsg", Base64Coding.encode(msg));
		if(data.equals("")||data==null){
			json.put("data", null);
		}else{
			json.put("data",data);
		}
		return json.toString();
	}
}
