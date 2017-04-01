package com.galaxy.appupload.beans;

public class CheckVersionBean {
	private String clientName;			//客户端名称
	private String clientVersion;		//客户端版本
	private String systemType;			//系统类型
	private String appCode;				//应用版本（beta：测试， release：发布）
	
	public String getSystemType() {
		return systemType;
	}
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientVersion() {
		return clientVersion;
	}
	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}
}
