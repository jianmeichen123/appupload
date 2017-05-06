<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>下载页面</title>
	<%@include file="common/common.jsp" %>
	
	<script type="text/javascript">
	    //判断当前手机类型，是Android还是ios
	    if(tools.isAndroid){
	    	if('${apptype}'=='android'||'${apptype}'=='Android'){
	    		if(tools.isQQBrowser||isWeiXin()){
	    			document.location = path + "/android/masking?app_url=${app_url}";
	    		}else{
	    			document.location = path + "/android/success?app_url=${app_url}";
	    		}
	    	}else{
	    		alert("对不起,此安装包仅支持ios系统，请退出！");
	    	}
	    }else if(tools.isIOS){
	    	if('${apptype}'=='ios'||'${apptype}'=='Ios'){
	    		if(tools.isSafari){
			    	document.location = path + "/ios/download?appFiles_url=${appFiles_url}";
			    }else{
			    	document.location = path + "/ios/masking?appFiles_url=${appFiles_url}";
			    }
	    	}else{
	    		alert("对不起,此安装包仅支持Android系统，请退出！");
	    	}
	    }else{
	    	alert("对不起,不支持该系统，请退出！");
	    }
	    
	    function isWeiXin(){ 
	    	var ua = window.navigator.userAgent.toLowerCase(); 
	    	if(ua.match(/MicroMessenger/i) == 'micromessenger'){ 
	    		return true; 
	    	}else{ 
	    		return false; 
	    	} 
	   }
	</script>
</head>
<body>
	
</body>
</html>