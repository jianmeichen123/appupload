package com.galaxy.appupload.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.galaxy.appupload.beans.ApplicationInfoBean;
import com.galaxy.appupload.beans.R_versionInfoBean;
import com.galaxy.appupload.beans.VersionInfoBean;
import com.galaxy.appupload.controller.AppManagerController;
import com.galaxy.appupload.dao.AppManagerDao;
import com.galaxy.appupload.util.QRCodeUtil;
import com.galaxy.appupload.util.RDataToJson;
import com.galaxy.appupload.util.ReadProperties;
import com.galaxy.appupload.util.Static_Commond;
import com.galaxy.appupload.util.UUIDGenerator;
import com.google.gson.Gson;
import com.mysql.jdbc.StringUtils;

@Service(value="appManagerService")
public class AppManagerServiceImpl implements AppManagerService{
	
	private static final Logger log = LoggerFactory.getLogger(AppManagerController.class);
	//获取路径
	String upload_url = ReadProperties.getRescMap().get("upload_url");
	String appupload_url = ReadProperties.getRescMap().get("appupload_url");
	String appFiles_url = ReadProperties.getRescMap().get("appFiles_url");
	//转换器json
	Gson gson = new Gson();
	private RDataToJson  Ifinte= new RDataToJson();
	
	@Autowired
	AppManagerDao appManagerDao;
	
	/**
	 * 保存应用方法
	 */
	@Override
	public int saveApplication(ApplicationInfoBean applicationInfoBean, HttpServletRequest request,MultipartFile smallLogo, MultipartFile bigLogo) {
		String smallPath = "";
		String bigPath = "";
		String type="";
		int result=0;
		
		//生成32位ID
		String ID = UUIDGenerator.getUUID();
		//获取页面信息
		String appcode = request.getParameter("appcode");
		String systemType = request.getParameter("systemType");
		if("0".equals(systemType)){
			type="Ios";
		}else{
			type="Android";
		}
		//小图标获取
		if (!smallLogo.isEmpty()) {
			try {
				String[] s1 = smallLogo.getOriginalFilename().split("\\.");
				//生成随机6位数据
				String nums = getRandomString(6);
				// 数据库保存的文件类型
				String attachType = s1[1];
				String path = upload_url+"/imgs"+"/"+nums+"/";
				String basePath =  path + s1[0] + "." + attachType;
				File files = new File(basePath);
				if(!files.exists() && !files.mkdirs()){
					files.mkdirs();
				}
				smallLogo.transferTo(new File(basePath));
				smallPath = "imgs" + basePath.split("imgs")[1];
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//大图标获取
		if (!bigLogo.isEmpty()) {
	        try {
	        	String[] s1 = bigLogo.getOriginalFilename().split("\\.");
	        	//生成随机6位数据
	        	String nums = getRandomString(6);
				// 数据库保存的文件类型
				String attachType = s1[1];
				String path = upload_url+"/imgs"+"/"+nums+"/";
				String basePath =  path + s1[0] + "." + attachType;
				File files = new File(basePath);
				if(!files.exists() && !files.mkdirs()){
					files.mkdirs();
				}
				bigLogo.transferTo(new File(basePath));
				bigPath = "imgs" + basePath.split("imgs")[1];
			} catch (IOException e) {
				e.printStackTrace();
			} 

		}
		
		//dao
		ApplicationInfoBean application = appManagerDao.getAppBean(appcode,type);
		if(application!=null){
			if(!(StringUtils.isNullOrEmpty(bigPath)) && !(StringUtils.isNullOrEmpty(application.getLogoBigFile()))){
				File fl =new File(upload_url+"/"+application.getLogoBigFile());
				if(fl.isFile() && fl.exists()){
					fl.delete();
					fl.getParentFile().delete();
				}
			}
			if(!(StringUtils.isNullOrEmpty(smallPath)) && !(StringUtils.isNullOrEmpty(application.getLogoSmallFile()))){
				File f2 =new File(upload_url+"/"+application.getLogoSmallFile());
				if(f2.isFile() && f2.exists()){
					f2.delete();
					f2.getParentFile().delete();
				}
			}
			if(!(StringUtils.isNullOrEmpty(bigPath)&&StringUtils.isNullOrEmpty(smallPath))){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("small", smallPath);
				params.put("big", bigPath);
				params.put("id", application.getId());
				result = appManagerDao.updateApplication(params);
			}
			result=1;
		}else{
			applicationInfoBean.setId(ID);
			applicationInfoBean.setSystemType(type);
			applicationInfoBean.setAppcode(appcode);
			applicationInfoBean.setLogoSmallFile(smallPath);
			applicationInfoBean.setLogoBigFile(bigPath);
			result = appManagerDao.saveApplication(applicationInfoBean);
		}
		return result;
	}
	
	/**
	 * 保存版本信息方法
	 * @throws Exception 
	 */
	@Override
	public int saveVersion(VersionInfoBean versionInfoBean, HttpServletRequest request, MultipartFile file) throws Exception {
		String filePath = "";
		String path ="";
		int result =0;
		String fileName="";
		int vstatus=0;
		String qr="";
		
		//获取6位随机数
		String nums = getRandomString(6);
		//从页面获取值
		String appcode = request.getParameter("appName");
		String apptype = request.getParameter("appType");
		String version = request.getParameter("version");
		String describe = request.getParameter("describe");
		String status = request.getParameter("status");
		
		if("0".equals(status)){
			vstatus=0;
		}else{
			vstatus=1;
		}
		
		//获取appid
		String appid = appManagerDao.getAppId(appcode,apptype);
		//生成32位ID
		String ID = UUIDGenerator.getUUID();
		//系统当前时间
		SimpleDateFormat ff =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date= new Date();
		
		//app文件获取
		if (!file.isEmpty()) {
			try {
				fileName =file.getOriginalFilename();
				// 数据库保存的文件类型
				path = upload_url+"/file"+"/"+nums+"/";
				String basePath =  path + fileName;
				
				File files = new File(basePath);
				if(!files.exists() && !files.mkdirs()){
					files.mkdirs();
				}
				file.transferTo(new File(basePath));
				filePath = "file" + basePath.split("file")[1];
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if("Ios".equals(apptype)||"ios".equals(apptype)){
			//创建plist方法
			String ipa =appFiles_url+"/"+filePath;
			createplist(path,ipa,version,request);
			qr=appupload_url+"download/app.action?nums="+nums+"&appFiles_url="+appFiles_url+"&apptype="+apptype;
		}else{
			String app_url = appFiles_url+"/"+filePath;
			qr =appupload_url+"download/androidApp.action?app_url="+app_url+"&apptype="+apptype;
		}
		//生成二维码
		String qrcode = QRCodeUtil.encode(qr, "",upload_url, true);
		
		//dao判断
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appid", appid);
		params.put("version", version);
		params.put("vstatus", vstatus);
		VersionInfoBean info = appManagerDao.isExistVersion(params);
		if(info!=null){
			File fl =new File(upload_url+"/"+info.getQrCode());
			if(fl.isFile() && fl.exists()){
				fl.delete();
			}
			if(!(StringUtils.isNullOrEmpty(filePath)) && !(StringUtils.isNullOrEmpty(info.getFilepath()))){
				File f2 =new File(upload_url+"/"+info.getFilepath());
				File f3 =new File(f2.getParentFile().toString()+"/"+"stars.plist");
				if(f2.isFile() && f2.exists()){
					f2.delete();
				}
				if(f3.isFile() && f3.exists()){
					f3.delete();
				}
				f2.getParentFile().delete();
			}
			info.setCreateTime(ff.format(date));
			info.setFilename(fileName);
			info.setFilepath(filePath);
			info.setUpdatelog(describe);
			info.setQrCode(qrcode);
			result = appManagerDao.updateVsersion(info);
		}else{
			versionInfoBean.setVersionStatus(vstatus);
			versionInfoBean.setAppid(appid);
			versionInfoBean.setVersionNo(version);
			versionInfoBean.setUpdatelog(describe);
			versionInfoBean.setId(ID);
			versionInfoBean.setCreateTime(ff.format(date));
			versionInfoBean.setFilename(fileName);
			versionInfoBean.setFilepath(filePath);
			versionInfoBean.setQrCode(qrcode);
			result = appManagerDao.saveVsersion(versionInfoBean);
		}
		return result;
	}
	/**
	 * 获取app应用list
	 */
	public List<ApplicationInfoBean> getAppList(){
		return appManagerDao.getAppList();
	}
	/**
	 * 获取系统类型by appname
	 */
	public List<ApplicationInfoBean> getSysType(String appname) {
		return appManagerDao.getSysTypeList(appname);
	}
	/**
	 * 获取版本型号list
	 */
	@Override
	public List<VersionInfoBean> getSysVersion(String appname, String apptype,String status) {
		String appid = appManagerDao.getAppId(appname,apptype);
		List<VersionInfoBean> versionInfoBean= appManagerDao.getVersionList(appid,status);
		return versionInfoBean;
	}
	/**
	 * 获取版本状态list
	 */
	@Override
	public List<VersionInfoBean> getVerStatus(String appname, String apptype) {
		String appid = appManagerDao.getAppId(appname,apptype);
		List<VersionInfoBean> versionInfoBean= appManagerDao.getStatusList(appid);
		return versionInfoBean;
	}
	/**
	 * 二维码获取版本最新信息方法
	 */
	@Override
	public VersionInfoBean getNewVersionByStatus(String id, String status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appid", id);
		params.put("appcode", status);
		VersionInfoBean versionInfoBean= appManagerDao.getNewVersionInfo(params);
		return versionInfoBean;
	}
	
	/**
	 * 下载方法
	 */
	@Override
	public VersionInfoBean downloadFile(String code, String type, String status) {
		String appid = appManagerDao.getAppId(code,type);
		int flag=0;
		if("beta".equals(status)){
			flag=0;
		}else if("release".equals(status)){
			flag=1;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appid", appid);
		params.put("appcode", flag);
		VersionInfoBean versionInfoBean= appManagerDao.getNewVersionInfo(params);
		return versionInfoBean;
	}
	/**
	 * 获取最新版本信息
	 */
	@Override
	public String getVersionInfo(String clientName, String clientVersion,String systemType, String appCode) {
		String resp="";
		int flag=0;
		String dataValue="";
		
		R_versionInfoBean r_versionInfo = new R_versionInfoBean();
		List<R_versionInfoBean> versionlist = new ArrayList<R_versionInfoBean>();
		
		//获取appid
		String appid = appManagerDao.getAppId(clientName,systemType);
		if(!StringUtils.isNullOrEmpty(appid)){
			if("beta".equals(appCode)){
				flag=0;
			}else if("release".equals(appCode)){
				flag=1;
			}
			
			//执行dao
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("appid", appid);
			params.put("appcode", flag);
			params.put("versionNO", clientVersion);
			VersionInfoBean versionInfoBean = appManagerDao.getCheckVersionInfo(params);
			if(versionInfoBean!=null){
				if("Ios".equals(systemType)||"ios".equals(systemType)){
					String[] ss = versionInfoBean.getFilepath().split("/");
					String url =appupload_url+"download/app.action?nums="+ss[1]+"&appFiles_url="+appFiles_url;
					r_versionInfo.setUrl(url);
				}else{
					r_versionInfo.setUrl(appFiles_url+"/"+versionInfoBean.getFilepath());
				}
				r_versionInfo.setClientVersion(versionInfoBean.getVersionNo());
				r_versionInfo.setUpdateLog(versionInfoBean.getUpdatelog());
				
				//gson转json
				versionlist.add(r_versionInfo);
				if(versionlist.size()>0){
					dataValue = gson.toJson(versionlist);
				}
				// 获取消息json字符串
				resp = Ifinte.getDataJson(Static_Commond.SUCCESS,  ReadProperties.getRescMap().get("success"),dataValue);
				log.info("返回结果:"+resp);
				return resp;
			}else{
				// 获取消息json字符串
				resp = Ifinte.getDataJson(Static_Commond.RESULTNULL,  ReadProperties.getRescMap().get("result_NULL"),"");
				log.info("未查到相关版本信息，返回结果:"+resp);
				return resp;
			}
		}else{
			// 获取消息json字符串
			resp = Ifinte.getDataJson(Static_Commond.RESULTNULL,  ReadProperties.getRescMap().get("result_NULL"),"");
			log.info("未查到相关版本信息，返回结果:"+resp);
			return resp;
		}
	}
	
	//随机生成n位随机数据
	public String getRandomString(int n){
		char[] str1 = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		int i;
		int count = 0;
		StringBuffer authcode = new StringBuffer("");
		Random r = new Random();
		while (count < n) {
			i = Math.abs(r.nextInt(10));
			if (i >= 0 && i < str1.length) {
				authcode.append(str1[i]);
				count++;
			}
		}
		return authcode.toString();
	}
	
	//生成plist文件方法
	public void createplist(String iosfile,String ipapath, String version, HttpServletRequest request){
		log.info("创建plist文件开始");
		String path = iosfile+"/"+"stars.plist";
		FileOutputStream fos = null;
		try {
			// 创建文档类型
			DocType docType = new DocType("plist");
			docType.setPublicID("-//Apple//DTD PLIST 1.0//EN");
			docType.setSystemID("http://www.apple.com/DTDs/PropertyList-1.0.dtd");
			// 创建根节点 plist
			Element root = new Element("plist");
			root.setAttribute("version", "1.0");
			//
			Element rootDict = new Element("dict");
			rootDict.addContent(new Element("key").setText("items"));
			Element rootDictArray = new Element("array");
			Element rootDictArrayDict = new Element("dict");
			rootDictArrayDict.addContent(new Element("key").setText("assets"));
	
	
			Element rootDictArrayDictArray = new Element("array");
			Element rootDictArrayDictArrayDict1 = new Element("dict");
			rootDictArrayDictArrayDict1.addContent(new Element("key")
			.setText("kind"));
			rootDictArrayDictArrayDict1.addContent(new Element("string")
			.setText("software-package"));
			rootDictArrayDictArrayDict1.addContent(new Element("key")
			.setText("url"));
			rootDictArrayDictArrayDict1.addContent(new Element("string")
			.setText(ipapath));
			
			Element rootDictArrayDictArrayDict2 = new Element("dict");
			rootDictArrayDictArrayDict2.addContent(new Element("key")
			.setText("kind"));
			rootDictArrayDictArrayDict2.addContent(new Element("string")
			.setText("display-image"));
			rootDictArrayDictArrayDict2.addContent(new Element("key")
			.setText("needs-shine"));
			rootDictArrayDictArrayDict2.addContent(new Element("true")
			.setText(""));
			rootDictArrayDictArrayDict2.addContent(new Element("key")
			.setText("url"));
			rootDictArrayDictArrayDict2.addContent(new Element("string")
			.setText(""));
			
			Element rootDictArrayDictArrayDict3 = new Element("dict");
			rootDictArrayDictArrayDict3.addContent(new Element("key")
			.setText("kind"));
			rootDictArrayDictArrayDict3.addContent(new Element("string")
			.setText("full-size-image"));
			rootDictArrayDictArrayDict3.addContent(new Element("key")
			.setText("needs-shine"));
			rootDictArrayDictArrayDict3.addContent(new Element("true")
			.setText(""));
			rootDictArrayDictArrayDict3.addContent(new Element("key")
			.setText("url"));
			rootDictArrayDictArrayDict3.addContent(new Element("string")
			.setText(""));

	
			rootDictArrayDictArray.addContent(rootDictArrayDictArrayDict1);
			rootDictArrayDictArray.addContent(rootDictArrayDictArrayDict2);
			rootDictArrayDictArray.addContent(rootDictArrayDictArrayDict3);
			rootDictArrayDict.addContent(rootDictArrayDictArray);
			rootDictArrayDict.addContent(new Element("key").setText("metadata"));
	
			Element rootDictArrayDictDict = new Element("dict");
			rootDictArrayDictDict.addContent(new Element("key")
			.setText("bundle-identifier"));
			rootDictArrayDictDict.addContent(new Element("string")
			.setText("com.galaxyinternet.galaxystars"));
			rootDictArrayDictDict.addContent(new Element("key")
			.setText("bundle-version"));
			rootDictArrayDictDict.addContent(new Element("string").setText(version));
			rootDictArrayDictDict.addContent(new Element("key").setText("kind"));
			rootDictArrayDictDict.addContent(new Element("string")
			.setText("software"));
			rootDictArrayDictDict.addContent(new Element("key").setText("title"));
			rootDictArrayDictDict.addContent(new Element("string").setText("繁星"));
			rootDictArrayDict.addContent(rootDictArrayDictDict);
	
			rootDictArray.addContent(rootDictArrayDict);
			rootDict.addContent(rootDictArray);
			root.addContent(rootDict);
			// 根节点添加到文档中;
			Document Doc = new Document(root, docType);
			Format format = Format.getPrettyFormat();
			XMLOutputter XMLOut = new XMLOutputter(format);
			// 输出 user.xml 文件；
			fos = new FileOutputStream(new File(path));
			XMLOut.output(Doc, fos);
		} catch (IOException e) {
			e.printStackTrace();
			log.info("创建plist文件异常");
		}finally{
			if(fos!=null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
					log.info("创建plist文件异常");
				}
			}
		}
		log.info("创建plist文件成功");
	}
	 
}
