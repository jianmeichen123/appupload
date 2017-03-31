package com.galaxy.appupload.beans;

public class R_versionInfoBean {
	private String clientVersion;		//客户端版本号
	private String url;					//文件地址
	private String updateLog;			//更新内容
	
	public String getClientVersion() {
		return clientVersion;
	}
	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUpdateLog() {
		return updateLog;
	}
	public void setUpdateLog(String updateLog) {
		this.updateLog = updateLog;
	}
	
}
