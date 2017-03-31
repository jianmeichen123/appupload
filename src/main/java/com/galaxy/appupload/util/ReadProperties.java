package com.galaxy.appupload.util;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class ReadProperties {
	
	private static Map<String,String> rescMap = new HashMap<String,String>();

	public static void Init(){
		Properties prop1 = new Properties();
		try {
			
			prop1.load(ReadProperties.class.getResourceAsStream("/resMsg.properties"));
			String[] service1 = prop1.getProperty("key").split(",");
			setMapValue(service1,rescMap,prop1);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			prop1.clear();
		}
	}
	
	public static void setMapValue(String[] service,Map<String,String> map,Properties prop){
		for(int i=0,j=service.length;i<j;i++){
			String filename = prop.getProperty(service[i]);
			/*try {
				filename = new String(filename.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}*/
			map.put(service[i],filename);
		}
	}
	
	public static Map<String, String> getRescMap() {
		return rescMap;
	}

	public static void setRescMap(Map<String, String> rescMap) {
		ReadProperties.rescMap = rescMap;
	}
	
}
