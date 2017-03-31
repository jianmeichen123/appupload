package com.galaxy.appupload.controller;

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
	public String downloadApp(){
		return "download_app";
	}
	
	@RequestMapping("/ios/download")
	public String iosDownload(){
		return "ios/ios_download";
	}
	
	@RequestMapping("/ios/masking")
	public String iosDownloadMasking(){
		return "ios/ios_download_masking";
	}
	
	@RequestMapping("/ios/success")
	public String iosDownloadSuccess(){
		return "ios/ios_setup_success";
	}
	
	
	
	
	
}
