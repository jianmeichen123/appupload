package com.galaxy.appupload.validData;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.galaxy.appupload.util.ReadProperties;
import com.galaxy.appupload.util.Static_Commond;


@Service(value="validDataFormat")
public class ValidDataFormatImpl implements ValidDataFormat{
	private static final Logger log = LoggerFactory.getLogger(ValidDataFormatImpl.class);

	@Override
	public Map<String, String> validVersionInfo(Map<String, String> map,String clientName,String clientVersion, String systemType, String appCode) {

		String resultCode = null;
		if (clientName == null || "".equals(clientName)) {
			log.info("客户端名称为空");
			resultCode = Static_Commond.HEADERROR;
			map.put(Static_Commond.STATE, Static_Commond.TRUE);
			map.put(Static_Commond.RESULTCODE, resultCode);
			map.put(Static_Commond.RESMSG, ReadProperties.getRescMap().get("clientName_NULL"));
			return map;
		} else if (clientVersion == null || "".equals(clientVersion)) {
			log.info("客户端版本号为空");
			resultCode = Static_Commond.HEADERROR;
			map.put(Static_Commond.STATE, Static_Commond.TRUE);
			map.put(Static_Commond.RESULTCODE, resultCode);
			map.put(Static_Commond.RESMSG, ReadProperties.getRescMap().get("clientVersion_NULL"));
			return map;
		}  else if (systemType == null || "".equals(systemType)) {
			log.info("客户端系统类型为空");
			resultCode = Static_Commond.HEADERROR;
			map.put(Static_Commond.STATE, Static_Commond.TRUE);
			map.put(Static_Commond.RESULTCODE, resultCode);
			map.put(Static_Commond.RESMSG, ReadProperties.getRescMap().get("systemType_NULL"));
			return map;
		}else if (appCode == null || "".equals(appCode)) {
			log.info("应用版本类型为空");
			resultCode = Static_Commond.HEADERROR;
			map.put(Static_Commond.STATE, Static_Commond.TRUE);
			map.put(Static_Commond.RESULTCODE, resultCode);
			map.put(Static_Commond.RESMSG, ReadProperties.getRescMap().get("appCode_NULL"));
			return map;
		}
		return map;
	}
	
}
