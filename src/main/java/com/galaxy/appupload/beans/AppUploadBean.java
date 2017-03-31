package com.galaxy.appupload.beans;

public class AppUploadBean {
	private String id;
	private String versionNo;
	private int systemType;
	private String onlineTime;
	private String createTime;
	private String downAppFile;
	private String logoBigFile;
	private String logoSmallFile;
	
	public AppUploadBean(){}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public int getSystemType() {
		return systemType;
	}

	public void setSystemType(int systemType) {
		this.systemType = systemType;
	}

	public String getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDownAppFile() {
		return downAppFile;
	}

	public void setDownAppFile(String downAppFile) {
		this.downAppFile = downAppFile;
	}

	public String getLogoBigFile() {
		return logoBigFile;
	}

	public void setLogoBigFile(String logoBigFile) {
		this.logoBigFile = logoBigFile;
	}

	public String getLogoSmallFile() {
		return logoSmallFile;
	}

	public void setLogoSmallFile(String logoSmallFile) {
		this.logoSmallFile = logoSmallFile;
	}
}
