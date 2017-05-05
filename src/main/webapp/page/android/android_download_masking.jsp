<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8"> 
	<title>下载页面</title>
	<meta name="viewport" content="width=device-width; initial-scale=0.6; user-scalable=no" />
	<%@include file="../common/common.jsp" %>
	<style type="text/css">
		body,iframe,div{margin:0;padding:0;border: 0;}
	</style>
	
	<script type="text/javascript">
		$(function(){
			var doc = document;
			var element = doc.documentElement;
			//不是QQ浏览器或微信浏览器，就直接到安装成功页面
			if(!(tools.isQQBrowser||isWeiXin())){
		   		$("body div").hide();
				window.location.href = path + "/android/success?app_url=${app_url}";
		    }
		    
		  	//只有当终端不是PC的时候执行，判断横竖屏并执行相关方法
			function hengshuping(){  
				if(window.orientation==180||window.orientation==0){  
			        //alert("竖屏状态！");
			        setVSize();
					//initV();
			   	}else if(window.orientation==90||window.orientation==-90){ 
					//alert("横屏状态！") 
					//initH();
				}
			}
		  
		    //判断是PC还是phone，如果为phone就判断是横屏还是竖屏
		    if(tools.isPhone){
		    	hengshuping();
				window.addEventListener("onorientationchange" in window ? "orientationchange" : "resize", hengshuping, false);
		    }else{
		    	setVSize();
		    	$(window).resize(function(){
		    		setVSize();
		    	});
		    }
		    
		    function setVSize(){
		    	var clientWidth = document.body.clientWidth;
				var clientHeight = document.documentElement.clientHeight;
				$("#div_masking").css("width",clientWidth);
				$("#div_masking").css("height",clientHeight);
				$("#ifr_container").css("height",clientHeight);
				//设置箭头部分
				$("#img_guest").css("width",557);
				$("#img_guest").css("height",1001);
		    }
		});
		
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
<body style="overflow: hidden;" scrolling="no">  
	<div id="div_masking" style="position: absolute;background: url(${path}/img/beijing_2x.png);z-index: 1000;overflow: hidden;">
		<img id="img_guest" alt="" src="${path }/img/andriod-zhixiang_3x.png" style="position: absolute;right: 50px;top: 30px;width: 220px;height: 400px;">
		<div style="position: absolute;width: 100%;height: 80px;bottom: 50px;line-height: 80px;font-size: 36px;color: #fff;" align="center">
			仅限：<img alt="" src="${path }/img/andriod_2x.png"/>&nbsp;android用户  
		</div>
	</div>
	
	<div id="ifr_container" style="overflow: hidden;">
		<img src="${path }/img/xiazai.jpg" style="width: 100%;"/>
	</div>
</body>
</html>