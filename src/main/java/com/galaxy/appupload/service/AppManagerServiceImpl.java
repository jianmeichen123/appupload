package com.galaxy.appupload.service;

import java.io.File;
import java.io.FileInputStream;
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
			type="iOS";
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
		String oldPath ="";
		String basePath="";
		int result =0;
		String fileName="";
		int vstatus=0;
		String qr="";
		@SuppressWarnings("unused")
		String plistPath ="";
		
		//从页面获取值
		String appcode = request.getParameter("appName");
		String apptype = request.getParameter("appType");
		String version = request.getParameter("version");
		String describe = request.getParameter("describe");
		String status = request.getParameter("status");
		//版本判断
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
				
				//判断类型,之前上传的版本备份的路径
				if(apptype.equals("Android")||apptype.equals("android")){
					if(vstatus==0){
						path = upload_url+"/file/bate";
						oldPath = upload_url+"/file/android/bate";
					}else{
						path = upload_url+"/file/release";
						oldPath = upload_url+"/file/android/release";
					}
					
				}else{
					if(vstatus==0){
						plistPath=appFiles_url+"/file/bate/";
						path = upload_url+"/file/bate";
						oldPath = upload_url+"/file/ios/bate";
					}else{
						plistPath=appFiles_url+"/file/release/";
						path = upload_url+"/file/release";
						oldPath = upload_url+"/file/ios/release";
					}
				}
				
				//当前最新版本路径
				basePath =  path +"/"+fileName;
				
				//上传文件之前做备份且更新数据
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("fileName", fileName);
				params.put("status", status);
				VersionInfoBean ver = appManagerDao.getVersionInfo(params);
				if(ver!=null){
					String a[] = fileName.split("\\.");
					//备份的文件名：stars_1.0.apk
					String oldName=a[0]+"_"+ver.getVersionNo()+"."+a[1];
					//创建备份的文件夹
					String old = oldPath+"/"+oldName;
					File oldfiles = new File(oldPath);
					if(!oldfiles.exists() && !oldfiles.mkdirs()){
						oldfiles.mkdirs();
					}
					//文件复制
					FileInputStream fin = new FileInputStream(new File(basePath));
					FileOutputStream fout = new FileOutputStream(new File(old));
					int bytesRead; 
			        byte[] buf = new byte[4 * 1024];  // 4K 
			        while ((bytesRead = fin.read(buf)) != -1) { 
			            fout.write(buf, 0, bytesRead); 
			        } 
			        fout.flush(); 
			        fout.close(); 
			        fin.close();
			        //ios客户端，创建plist方法 
			        if(apptype.equals("iOS")|| apptype.equals("ios")){
						String ipa =oldPath+"/"+oldName;
						createplist(oldPath,ipa,ver.getVersionNo(),"_"+ver.getVersionNo(),request);
					}
			        //更新老板数据
			        ver.setFilename(oldName);
			        ver.setFilepath("file"+oldPath.split("file")[1]+"/"+oldName);
			        appManagerDao.updateVsersion(ver);
				}
				
				//上传最新版本
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
		
		//生成二维码
		String qrcode="";
		if("iOS".equals(apptype)||"ios".equals(apptype)){
			//ios，创建plist方法
			String ipa =appFiles_url+"/"+filePath;
			createplist(path,ipa,version,"",request);
			//qr=appupload_url+"download/app.action?appFiles_url="+plistPath+"&apptype="+apptype;
			qr=appupload_url+"download/app.action?flag="+vstatus;
			
			qrcode = QRCodeUtil.encode(qr, "",upload_url, true,apptype,vstatus);
		}else{
			//String app_url = upload_url+"/"+filePath;
			//qr =appupload_url+"download/androidApp.action?app_url="+app_url+"&apptype="+apptype;
			qr =appupload_url+"download/androidApp.action?flag="+vstatus;
			qrcode = QRCodeUtil.encode(qr, "",upload_url, true,apptype,vstatus);
		}
		
		
		//dao判断
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appid", appid);
		params.put("version", version);
		params.put("vstatus", vstatus);
		VersionInfoBean info = appManagerDao.isExistVersion(params);
		if(info!=null){
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
	public VersionInfoBean downloadFile(String code, String type,String version, String status) {
		String appid = appManagerDao.getAppId(code,type);
		int vstatus=0;
		if("0".equals(status)){
			vstatus=0;
		}else if("1".equals(status)){
			vstatus=1;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appid", appid);
		params.put("vstatus", vstatus);
		params.put("version", version);
		VersionInfoBean versionInfoBean= appManagerDao.getVersionNoInfo(params);
		return versionInfoBean;
	}
	/**
	 * 版本检测，获取最新的版本
	 */
	@Override
	public String getVersionInfo(String clientName, String clientVersion,String systemType, String appCode) {
		String resp="";
		int flag=0;
		String dataValue="";
		String app_url="";
		R_versionInfoBean r_versionInfo = new R_versionInfoBean();
		List<R_versionInfoBean> versionlist = new ArrayList<R_versionInfoBean>();
		try {
			//获取appid
			String appid = appManagerDao.getAppId(clientName,systemType);
			if(!StringUtils.isNullOrEmpty(appid)){
				if("beta".equals(appCode)){
					flag=0;
					app_url =appFiles_url+"/file/bate/";
				}else if("release".equals(appCode)){
					flag=1;
					app_url =appFiles_url+"/file/release/";
				}
				
				//执行dao
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("appid", appid);
				params.put("appcode", flag);
				params.put("versionNO", clientVersion);
				VersionInfoBean versionInfoBean = appManagerDao.getCheckVersionInfo(params);
				if(versionInfoBean!=null){
					if(compareVersion(versionInfoBean.getVersionNo(),clientVersion)==1){
						if("iOS".equals(systemType)||"ios".equals(systemType)){
							String url =appupload_url+"download/app.action?flag="+flag;
							String iosUrl="itms-services://?action=download-manifest&url="+app_url+"stars.plist";
							r_versionInfo.setUrl(url);
							r_versionInfo.setIosUrl(iosUrl);
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
					}
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp = Ifinte.getDataJson(Static_Commond.RESULTNULL,  ReadProperties.getRescMap().get("result_NULL"),"");
		log.info("未查到相关版本信息，返回结果:"+resp);
		return resp;
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
	public void createplist(String iosfile,String ipapath, String version, String oldVersionNo,HttpServletRequest request){
		log.info("创建plist文件开始");
		File file = new File(iosfile);
		if(!file.exists() && !file.mkdirs()){
			file.mkdirs();
		}
		
		String path = iosfile+"/"+"stars"+oldVersionNo+".plist";
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
	
	public static int compareVersion(String version1, String version2) throws Exception {  
		   
		if (version1 == null || version2 == null) { 
			throw new Exception("compareVersion error:illegal params."); 
		} 
		String[] versionArray1 = version1.split("\\.");//注意此处为正则匹配，不能用"."； 
		for(int i = 0 ; i<versionArray1.length ; i++){ //如果位数只有一位则自动补零（防止出现一个是04，一个是5 直接以长度比较）
			if(versionArray1[i].length() == 1){
				versionArray1[i] = "0" + versionArray1[i];
			}
		}
		String[] versionArray2 = version2.split("\\."); 
		for(int i = 0 ; i<versionArray2.length ; i++){//如果位数只有一位则自动补零
			if(versionArray2[i].length() == 1){
				versionArray2[i] = "0" + versionArray2[i];
			}
		}
		int idx = 0; 
		int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值 
		int diff = 0; 
		while (idx < minLength 
		&& (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度 
		&& (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符 
			++idx; 
		} 
		//如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大； 
		diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length; 
		return diff; 
	}
	
	public static void main(String[] args) throws Exception {
		String aa = "1.9";
		String bb = "1.10";
		System.out.println(compareVersion(bb,aa));
	}
	
	 
}
