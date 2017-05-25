<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>二维码</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.12.4.js"></script>
</head>

<style type="text/css">
#appName{
width:150px;
height: 25px;
}
#status{
width:150px;
height: 25px;
}
</style>
<body>
	<a href="<%=request.getContextPath()%>/appManager/addApplication.action">添加应用信息</a>
	<a href="<%=request.getContextPath()%>/appManager/addVersion.action">添加版本信息</a>
	<a href="<%=request.getContextPath()%>/appManager/downloadFile.action">下载版本功能</a>
	<a href="<%=request.getContextPath()%>/appManager/getQRcode.action">二维码下载</a>
	<br>
	<br>
	<hr>
	<form action="" method="post">  
		<table>
			<tr>
				<td>应用名称：</td>
				<td>
					<select style="with: 120px;height: 25px;" name="appName" id="appName" onchange="resetStatus()">
						<option value="">请选择</option>
						<c:if test="${appList!=null}" >
							<c:forEach items="${appList}" var="app">
								<option value="${app.appcode}">${app.appcode}</option>
							</c:forEach>
						</c:if>
					</select>
				</td>
			</tr>
			<tr style="height: 5px;"></tr>
			<tr>
				<td>版本状态：</td>
				<td>
					<select style="with: 120px;height: 25px;" name="status" id="status" onchange="getQRcode()">
						<option value="">请选择</option>
						<option value="0">测试</option>
						<option value="1">发布</option>
					</select>
					<span style="color: red;width:auto"></span>
					<font color="red" id="error"></font>
				</td>
			</tr>
			<tr>
				<td></td>
				<td id="ios"></td>
				<td id="android"></td>
			</tr>
			<tr style="height: 5px;"></tr>
		</table>
	</form> 
</body>

<script type="text/javascript">
function resetStatus(){
	$("#status").val("");
	$("#ios").empty();
	$("#android").empty();
}
function getQRcode(){
	var appname=$("#appName").val();
	var status=$("#status").val();
	$("#ios").empty();
	$("#android").empty();
	$.ajax({
        type: "post",
        dataType: "json",
        url: '<%=request.getContextPath()%>/appManager/createQRcode.action',
        data: {appname:appname,status:status},
        success: function (data) {
            if (isEmptyObject(data)){
            	$("#error").empty();
            	for(var key in data) { 
            	    if(key=="ios"){
            	    	$("#ios").append("<span style='padding-left:5px'>iOS：</span></br><img src='<%=request.getContextPath()%>/appManager/showImage.action?filepath="+data[key]+"' style='width:150px;height:150px'/>");
            	    }else if(key=="android"){
            	    	$("#android").append("<span style='padding-left:40px'>Android：</span></br><img src='<%=request.getContextPath()%>/appManager/showImage.action?filepath="+data[key]+"' style='padding-left:35px;width:150px;height:150px'/>");
            	    }
            	} 
            }else{
            	$("#error").html("此版本没有二维码！");
            }
        }
    });
}

function isEmptyObject(obj) {
	for (var key in obj) {
	  return true;
	}
	return false;
}

</script>

</html>