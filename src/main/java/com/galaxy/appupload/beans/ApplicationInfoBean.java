package com.galaxy.appupload.beans;


public class ApplicationInfoBean {
	
	private String id;				//����id
	private String appcode;			//Ӧ������
	private String systemType;		//�汾����
	private String logoBigFile;		//��ͼ��
	private String logoSmallFile;	//Сͼ��
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAppcode() {
		return appcode;
	}
	public void setAppcode(String appcode) {
		this.appcode = appcode;
	}
	public String getSystemType() {
		return systemType;
	}
	public void setSystemType(String systemType) {
		this.systemType = systemType;
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
