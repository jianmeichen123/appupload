package com.galaxy.appupload.beans;

public class VersionInfoBean {
	
	private String id;					//主键id
	private String versionNo;			//版本号
	private String createTime;			//创建时间
	private String filename;			//文件名
	private String filepath;			//文件路径
	private String updatelog;			//更新内容
	private String appid;				//外键id
	private int versionStatus;			//版本状态
	private String qrCode;				//二维码路径
	
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getUpdatelog() {
		return updatelog;
	}
	public void setUpdatelog(String updatelog) {
		this.updatelog = updatelog;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public int getVersionStatus() {
		return versionStatus;
	}
	public void setVersionStatus(int versionStatus) {
		this.versionStatus = versionStatus;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
}
