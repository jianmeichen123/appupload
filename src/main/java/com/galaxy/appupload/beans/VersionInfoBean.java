package com.galaxy.appupload.beans;

public class VersionInfoBean {
	
	private String id;					//����id
	private String versionNo;			//�汾��
	private String createTime;			//����ʱ��
	private String filename;			//�ļ���
	private String filepath;			//�ļ�·��
	private String updatelog;			//��������
	private String appid;				//���id
	private int versionStatus;			//�汾״̬
	private String qrCode;				//��ά��·��
	
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
