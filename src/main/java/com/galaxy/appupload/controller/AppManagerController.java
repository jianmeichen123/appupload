package com.galaxy.appupload.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
	//转json
	private RDataToJson  Ifinte= new RDataToJson();
	//获取上传文件本地保存路径
	String upload_url = ReadProperties.getRescMap().get("upload_url");
	
	@Resource
	AppManagerService appManagerService;
	@Resource
	ValidDataFormat validDataFormat;
	
	/**
	 * 添加应用
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
	 * 保存应用
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
	 * 添加版本信息
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
	 * 保存版本信息
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
	 * 下载页面
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
	 * 获取版本类型信息
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
	 * 获取版本号信息
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
	 * 获取版本状态信息
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
	 * 获取二维码页面
	 * @param request
	 * @return
	 */
	@RequestMapping("getQRcode")
	public String getQRcodeMethod(HttpServletRequest request){
		List<ApplicationInfoBean> appList = appManagerService.getAppList();
		request.setAttribute("appList", appList);
		return "appmanager/QRCode";
		
	}
	
	/**
	 * 生成二维码
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
	 * 二维码扫描下载方法
	 */
	@RequestMapping("/qrCodeDownload")
	@ResponseBody
	public void qrCodeDownload(String filePath,HttpServletRequest request,HttpServletResponse response){
		OutputStream out = null;
		try{
			log.info("文件下载地址："+filePath);
			File file = new File(filePath);
			response.reset(); 
			response.setContentType("application/octet-stream; charset=utf-8"); 
			response.setHeader("Content-Disposition", "attachment; filename=" + file.getName()); 
			out = response.getOutputStream();
			out.write(FileUtils.readFileToByteArray(file)); 
			//IOUtils.copy(new FileInputStream(file), response.getOutputStream());
			out.flush();
			log.info("文件下载地址转换成功");
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
	 * 页面下载功能接口
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
		String status = request.getParameter("status");
		try{
			VersionInfoBean versionInfoBean = appManagerService.downloadFile(code,type,version,status);
			if(versionInfoBean!=null){
				path = upload_url+"/"+versionInfoBean.getFilepath();
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
	 * 检测版本接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="checkVersion",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String checkVersionMethod(@RequestBody CheckVersionBean checkVersionBean,HttpServletRequest request){
		Map<String,String> resmap=new HashMap<String,String>();
		resmap.put(Static_Commond.STATE, Static_Commond.FALSE);
		String resp ="";
		
		String clientName = checkVersionBean.getClientName();
		String clientVersion = checkVersionBean.getClientVersion();
		String systemType = checkVersionBean.getSystemType();
		String appcode = checkVersionBean.getAppCode();
		
		log.info("请求消息：clientName="+clientName+"clientVersion="+clientVersion+",systemType="+systemType+",appcode="+appcode);
		
		//业务逻辑
		try {
			//校验
			resmap=validDataFormat.validVersionInfo(resmap,clientName,clientVersion,systemType,appcode);
			if(resmap.get(Static_Commond.STATE).equals(Static_Commond.TRUE)){
				resp = Ifinte.getDataJson(resmap.get(Static_Commond.RESULTCODE),resmap.get(Static_Commond.RESMSG),"");
				log.info("返回结果:"+resp);
				return resp;
			}
			//获取版本信息
			resp = appManagerService.getVersionInfo(clientName,clientVersion,systemType,appcode);
			return resp;
		} catch (Exception e) {
			log.error("获取版本信息异常:"+e);
			resp = Ifinte.getDataJson(Static_Commond.ERROR, ReadProperties.getRescMap().get("error"),"");
			log.info("返回结果:"+resp);
			return resp;
		}
	}
	
	//获取本地磁盘上的图片
	@RequestMapping("showImage")  
	public void showImage(String filepath, HttpServletRequest request, HttpServletResponse response) throws Exception {
		InputStream inputStream = null;
		OutputStream writer = null;
		
		try {
			inputStream = new FileInputStream(new File(upload_url + '/' + filepath));
			writer = response.getOutputStream();

			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(buf)) != -1) {
				writer.write(buf, 0, len); // 写
			}
			inputStream.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
	}  
	
	/**
	 * 下载功能接口（暂时不用）
	 * @param request
	 * @param response
	 */
	@RequestMapping("/appdownload")
	@ResponseBody
	public void appdownloadMethod(String filepath,HttpServletRequest request,HttpServletResponse response){
		OutputStream out = null;
		String path="";
		try{
			path = upload_url + '/' + filepath;
			File file = new File(path);
			response.reset(); 
			response.setContentType("application/octet-stream; charset=utf-8"); 
			response.setHeader("Content-Disposition", "attachment; filename=" + file.getName()); 
			out = response.getOutputStream();
			out.write(FileUtils.readFileToByteArray(file)); 
			out.flush();
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
	
}
