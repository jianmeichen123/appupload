package com.galaxy.appupload.dao;

import java.util.List;
import java.util.Map;

import com.galaxy.appupload.beans.ApplicationInfoBean;
import com.galaxy.appupload.beans.VersionInfoBean;

public interface AppManagerDao {
	/**
	 * ����Ӧ����Ϣ����
	 * @param applicationInfoBean
	 * @return
	 */
	public int saveApplication(ApplicationInfoBean applicationInfoBean);
	/**
	 * ����Ӧ����Ϣ
	 * @param params
	 * @return
	 */
	public int updateApplication(Map<String, Object> params);
	/**
	 * ����汾��Ϣ����
	 * @param versionInfoBean
	 * @return
	 */
	public int saveVsersion(VersionInfoBean versionInfoBean);
	/**
	 * ���°汾��Ϣ����
	 * @param versionInfoBean
	 * @return
	 */
	public int updateVsersion(VersionInfoBean versionInfoBean);
	
	/**
	 * ��ȡappӦ��list����
	 * @return
	 */
	public List<ApplicationInfoBean> getAppList();
	/**
	 * ��ȡϵͳ����list����
	 * @param appname
	 * @return
	 */
	public List<ApplicationInfoBean> getSysTypeList(String appname);
	/**
	 * appid��ȡ�汾��Ϣlist
	 * @param appid
	 * @param status 
	 * @return
	 */
	public List<VersionInfoBean> getVersionList(String appid, String status);
	/**
	 * ��ȡappid by appcode+apptype
	 * @param appcode
	 * @param apptype
	 * @return
	 */
	public String getAppId(String appcode, String apptype);
	/**
	 * ͨ��appid��appCode��systemType�� version ��ȡ�汾��Ϣ
	 * @param version 
	 * @return
	 */
	public VersionInfoBean getDownloadFile(String appid, String version);
	/**
	 * appid��ȡ���°汾��Ϣ����
	 * @param params
	 * @return
	 */
	public VersionInfoBean getNewVersionInfo(Map<String, Object> params);
	/**
	 * ͨ��appid��version��status�Ƿ��������
	 * @param params
	 * @return
	 */
	public VersionInfoBean isExistVersion(Map<String, Object> params);
	/**
	 * ��ȡ�汾״̬list
	 * @param appid
	 * @return
	 */
	public List<VersionInfoBean> getStatusList(String appid);
	/**
	 * ��ȡӦ����Ϣbean 
	 * @param appcode
	 * @param type
	 * @return
	 */
	public ApplicationInfoBean getAppBean(String appcode, String type);
	

}
