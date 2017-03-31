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
	    	alert("Android");
	    }else if(tools.isIOS){
	    	if(tools.isSafari){
		    	document.location = path + "/ios/download";
		    }else{
		    	document.location = path + "/ios/masking";
		    }
	    }else{
	    	alert("对不起,不支持该系统，请退出！");
	    }
	</script>
</head>
<body>
	
</body>
</html>