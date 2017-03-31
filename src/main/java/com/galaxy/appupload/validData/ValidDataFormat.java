package com.galaxy.appupload.validData;


import java.util.Map;


public interface ValidDataFormat {

	//ºÏ≤‚∞Ê±æ–≈œ¢
	Map<String, String> validVersionInfo(Map<String, String> resmap,String clientName,String clientVersion, String systemType, String appCode);
}
