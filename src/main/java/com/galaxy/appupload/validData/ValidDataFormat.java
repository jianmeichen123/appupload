package com.galaxy.appupload.validData;


import java.util.Map;


public interface ValidDataFormat {

	//���汾��Ϣ
	Map<String, String> validVersionInfo(Map<String, String> resmap,String clientName,String clientVersion, String systemType, String appCode);
}
