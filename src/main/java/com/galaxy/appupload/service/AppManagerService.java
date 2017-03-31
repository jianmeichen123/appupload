package com.galaxy.appupload.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.galaxy.appupload.beans.ApplicationInfoBean;
import com.galaxy.appupload.beans.VersionInfoBean;

public interface AppManagerService {
	/**
	 * ����Ӧ����Ϣ
	 * @param applicationInfoBean
	 * @param request
	 * @param smallLogo
	 * @param bigLogo
	 * @return
	 */
	public int saveApplication(ApplicationInfoBean applicationInfoBean, HttpServletRequest request,MultipartFile smallLogo, MultipartFile bigLogo);
	/**
	 * ����汾��Ϣ
	 * @param versionInfoBean
	 * @param request
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	public int saveVersion(VersionInfoBean versionInfoBean, HttpServletRequest request, MultipartFile file) throws Exception;
	/**
	 * ��ȡ�����ļ�����
	 * @param version 
	 * @param type 
	 * @param code 
	 * @return
	 */
	public VersionInfoBean downloadFile(String code, String type, String version);
	/**
	 * ��ȡappӦ��list
	 * @return
	 */
	public List<ApplicationInfoBean> getAppList();
	/**
	 * ��ȡϵͳ����list
	 * @param appname
	 * @return
	 */
	public List<ApplicationInfoBean> getSysType(String appname);
	/**
	 * ��ȡ�汾��list
	 * @param appname
	 * @param apptype
	 * @param status 
	 * @return
	 */
	public List<VersionInfoBean> getSysVersion(String appname, String apptype, String status);
	/**
	 * ��ȡ�汾״̬list
	 * @param appname
	 * @param apptype
	 * @return
	 */
	public List<VersionInfoBean> getVerStatus(String appname, String apptype);
	/**
	 * ��ȡ�汾��Ϣ
	 * @param systemType
	 * @param appCode
	 * @param appCode2 
	 * @return
	 */
	public String getVersionInfo(String clientName, String systemType, String appCode);
	/**
	 * ͨ��appid ��status����ȡ���°汾��Ϣ
	 * @param id
	 * @param status
	 * @return
	 */
	public VersionInfoBean getNewVersionByStatus(String id, String status);
	

}
