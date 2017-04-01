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

	@RequestMapping("/download/app")
	public String downloadApp(String nums,String appupload_url,HttpServletRequest request){
		request.setAttribute("nums", nums);
		request.setAttribute("appupload_url", appupload_url);
		return "download_app";
	}
	
	@RequestMapping("/ios/download")
	public String iosDownload(String nums,String appupload_url,HttpServletRequest request){
		request.setAttribute("nums", nums);
		request.setAttribute("appupload_url", appupload_url);
		return "ios/ios_download";
	}
	
	@RequestMapping("/ios/masking")
	public String iosDownloadMasking(String nums,String appupload_url,HttpServletRequest request){
		request.setAttribute("nums", nums);
		request.setAttribute("appupload_url", appupload_url);
		return "ios/ios_download_masking";
	}
	
	@RequestMapping("/ios/success")
	public String iosDownloadSuccess(){
		return "ios/ios_setup_success";
	}
	
}
