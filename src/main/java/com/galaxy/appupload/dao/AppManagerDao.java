package com.galaxy.appupload.dao;

import java.util.List;
import java.util.Map;

import com.galaxy.appupload.beans.ApplicationInfoBean;
import com.galaxy.appupload.beans.VersionInfoBean;

public interface AppManagerDao {
	/**
	 * 保存应用信息方法
	 * @param applicationInfoBean
	 * @return
	 */
	public int saveApplication(ApplicationInfoBean applicationInfoBean);
	/**
	 * 更新应用信息
	 * @param params
	 * @return
	 */
	public int updateApplication(Map<String, Object> params);
	/**
	 * 保存版本信息方法
	 * @param versionInfoBean
	 * @return
	 */
	public int saveVsersion(VersionInfoBean versionInfoBean);
	/**
	 * 更新版本信息方法
	 * @param versionInfoBean
	 * @return
	 */
	public int updateVsersion(VersionInfoBean versionInfoBean);
	
	/**
	 * 获取app应用list方法
	 * @return
	 */
	public List<ApplicationInfoBean> getAppList();
	/**
	 * 获取系统类型list方法
	 * @param appname
	 * @return
	 */
	public List<ApplicationInfoBean> getSysTypeList(String appname);
	/**
	 * appid获取版本信息list
	 * @param appid
	 * @param status 
	 * @return
	 */
	public List<VersionInfoBean> getVersionList(String appid, String status);
	/**
	 * 获取appid by appcode+apptype
	 * @param appcode
	 * @param apptype
	 * @return
	 */
	public String getAppId(String appcode, String apptype);
	/**
	 * 通过appid（appCode，systemType） version 获取版本信息
	 * @param version 
	 * @return
	 */
	public VersionInfoBean getDownloadFile(String appid, String version);
	/**
	 * appid获取最新版本信息方法
	 * @param params
	 * @return
	 */
	public VersionInfoBean getNewVersionInfo(Map<String, Object> params);
	/**
	 * 通过appid，version，status是否存在数据
	 * @param params
	 * @return
	 */
	public VersionInfoBean isExistVersion(Map<String, Object> params);
	/**
	 * 获取版本状态list
	 * @param appid
	 * @return
	 */
	public List<VersionInfoBean> getStatusList(String appid);
	/**
	 * 获取应用信息bean 
	 * @param appcode
	 * @param type
	 * @return
	 */
	public ApplicationInfoBean getAppBean(String appcode, String type);
	/**
	 * 版本检测获取最新且大于当前版本信息
	 * @param params
	 * @return
	 */
	public VersionInfoBean getCheckVersionInfo(Map<String, Object> params);
	/**
	 * 通过文件名获取版本信息
	 * @param fileName
	 * @param vstatus 
	 * @return
	 */
	public VersionInfoBean getVersionInfo(Map<String, Object> params);
	/**
	 * 根据版本号下载对应的版本
	 * @param params
	 * @return
	 */
	public VersionInfoBean getVersionNoInfo(Map<String, Object> params);
	

}
