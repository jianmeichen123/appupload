<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>安装成功</title>
	<meta name="viewport" content="width=device-width; initial-scale=0.6; user-scalable=no" />
	<%@include file="../common/common.jsp" %>
	
	<style type="text/css">
		body{margin:0;padding:0;}
		.div_container {
			height: auto;
			width: 100%;
			font-family: "微软雅黑";
			font-size: 2em;
			color: #428de0;
			position: absolute;
			bottom: 200px;
		}
		.div_container p{
			text-align: center;
		}
		
	</style>
</head>
<body>
	<img id="bg_img" alt="" src="${path }/img/bg_setup_success.png" style="position: absolute;width: 100%;height: 100%;z-index: -1000;">
	<div class="div_container">
		<p style="">软件已经开始下载安装</p>
		<p>请返回屏幕桌面稍作等待</p>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		document.location = path + "/appManager/qrCodeDownload?filePath=${app_url}";
	});
</script>

</html>