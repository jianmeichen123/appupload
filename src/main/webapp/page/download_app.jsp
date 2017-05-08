<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=8">
	<meta http-equiv="Expires" content="0">
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Cache-control" content="no-cache">
	<meta http-equiv="Cache" content="no-cache">
	
	<title>下载页面</title>
	<%@include file="common/common.jsp" %>

</head>
<body>
	
	<script type="text/javascript">
		$(function(){
			
			
			//判断当前手机类型，是Android还是ios
		    if(tools.isAndroid){
		    	if('${apptype}'=='android'||'${apptype}'=='Android'){
		    		if(tools.isQQBrowser||tools.isWeiXin){
		    			document.location = path + "/android/masking?app_url=${app_url}";
		    		}else{
		    			document.location = path + "/android/success?app_url=${app_url}";
		    		}
		    	}else{
		    		alert("对不起，此安装包仅支持ios系统，请退出！");
		    	}
		    }else if(tools.isIOS){
		    	alert("this is ios");
		    	/*if('${apptype}'=='ios'||'${apptype}'=='Ios'){
		    		if(tools.isSafari){
				    	document.location = path + "/ios/download?appFiles_url=${appFiles_url}";
				    }else{
				    	document.location = path + "/ios/masking?appFiles_url=${appFiles_url}";
				    }
		    	}else{
		    		alert("对不起，此安装包仅支持Android系统，请退出！");
		    	}*/
		    }else{
		    	alert("对不起，不支持该系统，请退出！");
		    }
			
		    
			
		});
	/*
		function isWeiXin(){ 
	    	var ua = window.navigator.userAgent.toLowerCase(); 
	    	if(ua.match(/MicroMessenger/i) == 'micromessenger'){ 
	    		return true; 
	    	}else{ 
	    		return false; 
	    	} 
	   }*/
		
	    
	    
	</script>
</body>
</html>