<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>安装页面</title>
	<meta name="viewport" content="width=device-width; initial-scale=0.6; user-scalable=no" />
	
	<%@include file="../common/common.jsp" %>
	
	<style type="text/css">
		body{margin:0;padding:0;}
	</style>
	
	<script type="text/javascript">
		$(function(){
			var doc = document;
			var element = doc.documentElement; 
			
 		    if(!tools.isSafari){
		    	document.location = "ios_download_masking.html";
		    }
			
			function initSize(){
				var clientWidth = document.body.clientWidth;
				
				if(clientWidth>1080){
					$("#div_container").css("width",1080);
				}else{
					$("#div_container").css("width",clientWidth);
				}
				$("#img_background").css("width",$("#div_container").width());
			}
			
			initSize();
			
			$(window).resize(function(){
				initSize();
			});
			
			//点击iphone下载
			$("#btn_downloadapp").click(function(){
				location.href = 'itms-services://?action=download-manifest&url=${appFiles_url}stars.plist';
				//just_main();
				
				setTimeout("just_main()", 3000);  
			});
			
			
		});
		function just_main(){
			window.location.href = path + "/ios/success";
		}
	</script>
</head>
<body>
	<div id="div_container" style="width: 300px;height: 100px;align: center;margin-left: auto;margin-right: auto;z-index: 200;">
		<img id="img_background" alt="" src="${path }/img/xiazai.jpg">
		<div style="height: 800px;">
			<div style="height: 35%;">&nbsp;</div>
			<div style="height: 50%;" align="center">
				<div>
					<img id="btn_downloadapp" alt="" style="width: 40vw;" src="${path }/img/anniu_3x.png">
				</div>
				<div style="font-size: 28px;color: #4399e1;font-style: bold;padding-top: 10px;">&nbsp;</div>
			</div>
			<div style="height: 15%;font-size: 26px;color: #999;font-style: bold;" align="center">
				星河互联旗下产品
			</div>
		</div>
	</div>

</body>
</html>