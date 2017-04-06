<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>上传下载</title>
<link href="<%=request.getContextPath()%>/js/login.css" type="text/css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/jquery-1.12.4.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/login.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/layer.js" type="text/javascript"></script>
</head>

<body class="login">
	<div class="loginpage_new">
		<div class="top">
    </div>
	<div class="min clearfix">
	 	<img src="<%=request.getContextPath()%>/img/loginbg.jpg" alt="">
		<div class="loginbox">
			 <div class="loginbox_top">
                <h2>快速登录</h2>
           </div>
			<form id="fm" action="<%=request.getContextPath()%>/login/userLogin" method="post" class="form-horizontal">
				<input id="username" name="username" type="text" class="txt ico uname" placeholder="用户名" value="${username}"/> 
				<input id="password" name="password" type="password" class="txt ico uword" placeholder="密码" />
				<label><font style="padding-left:26px;color:red">${msg}</font></label>
				<a href="javascript:;" class="login" onclick="checkform();">登录</a>
				<p class="tips ico">建议使用IE10以上浏览器<a href="http://windows.microsoft.com/zh-cn/internet-explorer/download-ie" class="blue">版本过低，点击下载！</a>
           		</p>
			</form>
		</div>
	</div>
   	<div class="btm">
    	星河互联旗下产品
    </div>
	</div>
</body>
</html>
