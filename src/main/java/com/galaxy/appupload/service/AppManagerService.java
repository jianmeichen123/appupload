package com.galaxy.appupload.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.galaxy.appupload.beans.ApplicationInfoBean;
import com.galaxy.appupload.beans.VersionInfoBean;

public interface AppManagerService {
	/**
	 * 保存应用信息
	 * @param applicationInfoBean
	 * @param request
	 * @param smallLogo
	 * @param bigLogo
	 * @return
	 */
	public int saveApplication(ApplicationInfoBean applicationInfoBean, HttpServletRequest request,MultipartFile smallLogo, MultipartFile bigLogo);
	/**
	 * 保存版本信息
	 * @param versionInfoBean
	 * @param request
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	public int saveVersion(VersionInfoBean versionInfoBean, HttpServletRequest request, MultipartFile file) throws Exception;
	/**
	 * 获取下载文件方法
	 * @param version 
	 * @param type 
	 * @param code 
	 * @param status 
	 * @return
	 */
	public VersionInfoBean downloadFile(String code, String type, String version, String status);
	/**
	 * 获取app应用list
	 * @return
	 */
	public List<ApplicationInfoBean> getAppList();
	/**
	 * 获取系统类型list
	 * @param appname
	 * @return
	 */
	public List<ApplicationInfoBean> getSysType(String appname);
	/**
	 * 获取版本号list
	 * @param appname
	 * @param apptype
	 * @param status 
	 * @return
	 */
	public List<VersionInfoBean> getSysVersion(String appname, String apptype, String status);
	/**
	 * 获取版本状态list
	 * @param appname
	 * @param apptype
	 * @return
	 */
	public List<VersionInfoBean> getVerStatus(String appname, String apptype);
	/**
	 * 获取版本信息
	 * @param systemType
	 * @param appCode
	 * @param appCode2 
	 * @return
	 */
	public String getVersionInfo(String clientName, String clientVersion,String systemType, String appCode);
	/**
	 * 通过appid ，status，获取最新版本信息
	 * @param id
	 * @param status
	 * @return
	 */
	public VersionInfoBean getNewVersionByStatus(String id, String status);
	

}
