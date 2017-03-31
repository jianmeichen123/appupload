package com.galaxy.appupload.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.galaxy.appupload.beans.ApplicationInfoBean;
import com.galaxy.appupload.beans.CheckVersionBean;
import com.galaxy.appupload.beans.VersionInfoBean;
import com.galaxy.appupload.service.AppManagerService;
import com.galaxy.appupload.util.RDataToJson;
import com.galaxy.appupload.util.ReadProperties;
import com.galaxy.appupload.util.Static_Commond;
import com.galaxy.appupload.validData.ValidDataFormat;

@Controller
@RequestMapping("/appManager")
public class AppManagerController {
	
	private static final Logger log = LoggerFactory.getLogger(AppManagerController.class);
	@Resource
	AppManagerService appManagerService;
	@Resource
	ValidDataFormat validDataFormat;
	//תjson
	private RDataToJson  Ifinte= new RDataToJson();
	
	/**
	 * ���Ӧ��
	 * @param applicationInfoBean
	 * @param request
	 * @param smallLogo
	 * @param bigLogo
	 * @return
	 */
	@RequestMapping("addApplication")
	public String addApplicationMethod(String flag,HttpServletRequest request){
		request.setAttribute("flag", flag);
		return "appmanager/addApplication";
		
	}
	
	/**
	 * ����Ӧ��
	 * @param applicationInfoBean
	 * @param request
	 * @param smallLogo
	 * @param bigLogo
	 * @return
	 */
	@RequestMapping("saveApplication")
	public String saveApplicationMethod(ApplicationInfoBean applicationInfoBean,HttpServletRequest request,
			@RequestParam("smallLogo") MultipartFile smallLogo,@RequestParam("bigLogo") MultipartFile bigLogo){
		int flag = appManagerService.saveApplication(applicationInfoBean,request,smallLogo,bigLogo);
		return "redirect:/appManager/addApplication?flag="+flag;
		
	}
	
	/**
	 * ��Ӱ汾��Ϣ
	 * @param versionInfoBean
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping("addVersion")
	public String addVersionMethod(HttpServletRequest request,String flag){
		List<ApplicationInfoBean> appList = appManagerService.getAppList();
		if(!StringUtils.isEmpty(flag)){
			request.setAttribute("flag", flag);
		}
		request.setAttribute("appList", appList);
		return "appmanager/addVersion";
		
	}
	/**
	 * ����汾��Ϣ
	 * @param versionInfoBean
	 * @param request
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("saveVersion")
	public String saveVesionMethod(VersionInfoBean versionInfoBean,HttpServletRequest request,
			@RequestParam("file") MultipartFile file) throws Exception{
		int flag = 0;
		try {
			flag = appManagerService.saveVersion(versionInfoBean,request,file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/appManager/addVersion.action?flag="+flag;
		
	}
	/**
	 * ����ҳ��
	 * @param applicationInfoBean
	 * @param request
	 * @param smallLogo
	 * @param bigLogo
	 * @return
	 */
	@RequestMapping("downloadFile")
	public String downloadFileMethod(String flag,HttpServletRequest request){
		List<ApplicationInfoBean> appList = appManagerService.getAppList();
		if(!StringUtils.isEmpty(flag)){
			request.setAttribute("flag", flag);
		}
		request.setAttribute("appList", appList);
		return "appmanager/downloadFile";
	}
	
	/**
	 * ��ȡ�汾������Ϣ
	 * @param versionInfoBean
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping("getSysType")
	@ResponseBody
	public List<ApplicationInfoBean> getSysType(String appname,HttpServletRequest request){
		List<ApplicationInfoBean> appList = appManagerService.getSysType(appname);
		return appList;
	}
	
	/**
	 * ��ȡ�汾����Ϣ
	 * @param versionInfoBean
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping("getSysVersion")
	@ResponseBody
	public List<VersionInfoBean> getSysVersion(String appname,String apptype,String status,HttpServletRequest request){
		List<VersionInfoBean> versionList = appManagerService.getSysVersion(appname,apptype,status);
		return versionList;
	}
	/**
	 * ��ȡ�汾״̬��Ϣ
	 * @param appname
	 * @param apptype
	 * @param request
	 * @return
	 */
	@RequestMapping("getVerStatus")
	@ResponseBody
	public List<VersionInfoBean> getVerStatus(String appname,String apptype,HttpServletRequest request){
		List<VersionInfoBean> versionList = appManagerService.getVerStatus(appname,apptype);
		return versionList;
	}
	/**
	 * ��ȡ��ά��
	 * @param request
	 * @return
	 */
	@RequestMapping("getQRcode")
	public String getQRcodeMethod(HttpServletRequest request){
		List<ApplicationInfoBean> appList = appManagerService.getAppList();
		request.setAttribute("appList", appList);
		return "appmanager/QRCode";
		
	}
	
	//����ios���ص�htmlҳ��
	@RequestMapping("jumpHtml")
	public String jumpHtml(String nums,String appupload_url,HttpServletRequest request){
		request.setAttribute("nums", nums);
		request.setAttribute("appupload_url", appupload_url);
		return "appmanager/iosDownload";
		
	}
	
	/**
	 * ���ɶ�ά��
	 * @param appname
	 * @param status
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("createQRcode")
	@ResponseBody
	public Map<String, Object> createQRcode(String appname,String status,HttpServletRequest request) throws Exception{
		List<ApplicationInfoBean> appList = appManagerService.getSysType(appname);
		Map<String, Object> params = new HashMap<String, Object>();
		for(ApplicationInfoBean app :appList){
			String id="0";
			if("ios".equals(app.getSystemType())||"Ios".equals(app.getSystemType())){
				id=app.getId();
				VersionInfoBean iosVersion = appManagerService.getNewVersionByStatus(id,status);
				if(iosVersion!=null){
					params.put("ios", iosVersion.getQrCode());
				}
			}else if("android".equals(app.getSystemType())||"Android".equals(app.getSystemType())){
				id=app.getId();
				VersionInfoBean androidVersion = appManagerService.getNewVersionByStatus(id,status);
				if(androidVersion!=null){
					params.put("android", androidVersion.getQrCode());
				}
			}
		}
		return params;
	}
	
	/*
	 * ��ά��ɨ�����ط���
	 */
	@RequestMapping("/qrCodeDownload")
	@ResponseBody
	public void qrCodeDownload(String filePath,HttpServletRequest request,HttpServletResponse response){
		//OutputStream out = null;
		String path="";
		try{
			path = request.getSession().getServletContext().getRealPath("/")+filePath;
			File file = new File(path);
			response.reset(); 
			response.setContentType("application/octet-stream; charset=utf-8"); 
			response.setHeader("Content-Disposition", "attachment; filename=" + file.getName()); 
			//out = response.getOutputStream();
			//out.write(FileUtils.readFileToByteArray(file)); 
			IOUtils.copy(new FileInputStream(file), response.getOutputStream());
			//out.flush();
		}catch(Exception e){
			e.printStackTrace();
//		}finally { 
//			if (out != null) { 
//				try{
//					out.close();
//				}catch (IOException e) {
//					e.printStackTrace(); 
//				}
//			} 
		} 
	}
	
	/**
	 * ���ع��ܽӿ�
	 * @param request
	 * @param response
	 */
	@RequestMapping("/download")
	public void downloadMethod(HttpServletRequest request,HttpServletResponse response){
		OutputStream out = null;
		String path="";
		String code = request.getParameter("appName");
		String type = request.getParameter("appType");
		String version = request.getParameter("version");
		try{
			VersionInfoBean versionInfoBean = appManagerService.downloadFile(code,type,version);
			if(versionInfoBean!=null){
				path = request.getSession().getServletContext().getRealPath("/")+"\\"+versionInfoBean.getFilepath();
				File file = new File(path);
				response.reset(); 
				response.setContentType("application/octet-stream; charset=utf-8"); 
				response.setHeader("Content-Disposition", "attachment; filename=" + file.getName()); 
				out = response.getOutputStream();
				out.write(FileUtils.readFileToByteArray(file)); 
				out.flush();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally { 
			if (out != null) { 
				try{
					out.close();
				}catch (IOException e) {
					e.printStackTrace(); 
				}
			} 
		} 
	}
	
	/**
	 * ���ع��ܽӿ�
	 * @param request
	 * @param response
	 */
	@RequestMapping("/appdownload")
	@ResponseBody
	public void appdownloadMethod(@RequestBody CheckVersionBean checkVersionBean,HttpServletRequest request,HttpServletResponse response){
		String clientName = request.getHeader("clientName");
		String clientVersion = request.getHeader("clientVersion");
		log.info("������Ϣ��clientName="+clientName+"clientVersion="+clientVersion+",systemType="+checkVersionBean.getSystemType()+",appcode="+checkVersionBean.getAppCode());
		
		OutputStream out = null;
		String path="";
		try{
			VersionInfoBean versionInfoBean = appManagerService.downloadFile(clientName,checkVersionBean.getSystemType(),checkVersionBean.getAppCode());
			if(versionInfoBean!=null){
				path = request.getSession().getServletContext().getRealPath("/")+"\\"+versionInfoBean.getFilepath();
				File file = new File(path);
				response.reset(); 
				response.setContentType("application/octet-stream; charset=utf-8"); 
				response.setHeader("Content-Disposition", "attachment; filename=" + file.getName()); 
				out = response.getOutputStream();
				out.write(FileUtils.readFileToByteArray(file)); 
				out.flush();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally { 
			if (out != null) { 
				try{
					out.close();
				}catch (IOException e) {
					e.printStackTrace(); 
				}
			} 
		} 
	}
	
	/**
	 * ���汾�ӿ�
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("checkVersion")
	@ResponseBody
	public String checkVersionMethod(@RequestBody CheckVersionBean checkVersionBean,HttpServletRequest request){
		Map<String,String> resmap=new HashMap<String,String>();
		resmap.put(Static_Commond.STATE, Static_Commond.FALSE);
		String resp ="";
		
		String clientName = request.getHeader("clientName");
		String clientVersion = request.getHeader("clientVersion");
		log.info("������Ϣ��clientName="+clientName+"clientVersion="+clientVersion+",systemType="+checkVersionBean.getSystemType()+",appcode="+checkVersionBean.getAppCode());
		
		//ҵ���߼�
		try {
			//У��
			resmap=validDataFormat.validVersionInfo(resmap,clientName,clientVersion,checkVersionBean.getSystemType(),checkVersionBean.getAppCode());
			if(resmap.get(Static_Commond.STATE).equals(Static_Commond.TRUE)){
				resp = Ifinte.getDataJson(resmap.get(Static_Commond.RESULTCODE),resmap.get(Static_Commond.RESMSG),"");
				log.info("���ؽ��:"+resp);
				return resp;
			}
			//��ȡ�汾��Ϣ
			resp = appManagerService.getVersionInfo(clientName,checkVersionBean.getSystemType(),checkVersionBean.getAppCode());
			return resp;
		} catch (Exception e) {
			log.error("��ȡ�汾��Ϣ�쳣:"+e);
			resp = Ifinte.getDataJson(Static_Commond.ERROR, ReadProperties.getRescMap().get("error"),"");
			log.info("���ؽ��:"+resp);
			return resp;
		}
	}
	
}
