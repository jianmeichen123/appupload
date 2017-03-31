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
			log.info("�ͻ�������Ϊ��");
			resultCode = Static_Commond.HEADERROR;
			map.put(Static_Commond.STATE, Static_Commond.TRUE);
			map.put(Static_Commond.RESULTCODE, resultCode);
			map.put(Static_Commond.RESMSG, ReadProperties.getRescMap().get("clientName_NULL"));
			return map;
		} else if (clientVersion == null || "".equals(clientVersion)) {
			log.info("�ͻ��˰汾��Ϊ��");
			resultCode = Static_Commond.HEADERROR;
			map.put(Static_Commond.STATE, Static_Commond.TRUE);
			map.put(Static_Commond.RESULTCODE, resultCode);
			map.put(Static_Commond.RESMSG, ReadProperties.getRescMap().get("clientVersion_NULL"));
			return map;
		}  else if (systemType == null || "".equals(systemType)) {
			log.info("�ͻ���ϵͳ����Ϊ��");
			resultCode = Static_Commond.HEADERROR;
			map.put(Static_Commond.STATE, Static_Commond.TRUE);
			map.put(Static_Commond.RESULTCODE, resultCode);
			map.put(Static_Commond.RESMSG, ReadProperties.getRescMap().get("systemType_NULL"));
			return map;
		}else if (appCode == null || "".equals(appCode)) {
			log.info("Ӧ�ð汾����Ϊ��");
			resultCode = Static_Commond.HEADERROR;
			map.put(Static_Commond.STATE, Static_Commond.TRUE);
			map.put(Static_Commond.RESULTCODE, resultCode);
			map.put(Static_Commond.RESMSG, ReadProperties.getRescMap().get("appCode_NULL"));
			return map;
		}
		return map;
	}
	
}
