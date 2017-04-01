package com.galaxy.appupload.validData;


import java.util.Map;


public interface ValidDataFormat {

	//检测版本信息
	Map<String, String> validVersionInfo(Map<String, String> resmap,String clientName,String clientVersion, String systemType, String appCode);
}
