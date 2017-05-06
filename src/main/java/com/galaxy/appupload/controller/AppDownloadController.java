package com.galaxy.appupload.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.galaxy.appupload.beans.AppFileBean;
import com.galaxy.appupload.service.IAppFileService;

@Controller
@RequestMapping
public class AppDownloadController {
	@Autowired
	private IAppFileService appFileService;
	
	@RequestMapping("/index")
	public String index(){
		AppFileBean appFileBean = appFileService.getAppFileById("111");
		if(appFileBean!=null){
			System.out.println(appFileBean.getSrcName());
		}
		return "index";
	}
	//ios
	@RequestMapping("/download/app")
	public String downloadApp(String appFiles_url,String apptype,HttpServletRequest request){
		request.setAttribute("appFiles_url", appFiles_url);
		request.setAttribute("apptype", apptype);
		return "download_app";
	}
	
	@RequestMapping("/ios/download")
	public String iosDownload(String appFiles_url,HttpServletRequest request){
		request.setAttribute("appFiles_url", appFiles_url);
		return "ios/ios_download";
	}
	
	@RequestMapping("/ios/masking")
	public String iosDownloadMasking(String appFiles_url,HttpServletRequest request){
		request.setAttribute("appFiles_url", appFiles_url);
		return "ios/ios_download_masking";
	}
	
	@RequestMapping("/ios/success")
	public String iosDownloadSuccess(){
		return "ios/ios_setup_success";
	}
	
	//android
	@RequestMapping("/download/androidApp")
	public String downloadAndroidApp(String app_url,String apptype,HttpServletRequest request){
		request.setAttribute("app_url", app_url);
		request.setAttribute("apptype", apptype);
		return "download_app";
	}
	@RequestMapping("/android/masking")
	public String androidDownloadMasking(String app_url,HttpServletRequest request){
		request.setAttribute("app_url", app_url);
		return "android/android_download_masking";
	}
	
	@RequestMapping("/android/success")
	public String androidDownloadSuccess(String app_url,HttpServletRequest request){
		request.setAttribute("app_url", app_url);
		return "android/android_setup_success";
	}
	
}
