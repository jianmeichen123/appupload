<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加版本信息</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.12.4.js"></script>
</head>

<style type="text/css">
#appName{
width:180px;
height: 25px;
}
#appType{
width:180px;
height: 25px;
}
#version{
width:180px;
height: 25px;
}
#file{
width:180px;
height: 25px;
}
#status{
width:180px;
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
	<form id="fm" action="<%=request.getContextPath()%>/appManager/saveVersion" method="post" enctype="multipart/form-data">  
	<input type="hidden" id="flag" name="flag" value="${flag}">
		<table border="0">
			<tr>
				<td>应用名称：</td>
				<td>
					<select style="with: 120px;height: 25px;" name="appName" id="appName" onchange="sysType()">
						<option value="">请选择</option>
						<c:if test="${appList!=null}" >
							<c:forEach items="${appList}" var="app">
								<option value="${app.appcode}">${app.appcode}</option>
							</c:forEach>
						</c:if>
					</select>
					<span style="color: red;width:auto">*</span>
					<font color="red" id="nameinfo"></font>
				</td>
			</tr>
			<tr style="height: 5px;"></tr>
			<tr>
				<td>版本类型：</td>
				<td>
					<select style="with: 120px;height: 25px;" name="appType" id="appType" onchange="sysStatus()">
						<option selected value="">请选择</option>
					</select>
					<span style="color: red;width:auto">*</span>
					<font color="red" id="typeinfo"></font>
				</td>
			</tr>
			<tr style="height: 5px;"></tr>
			<tr>
				<td>版本状态：</td>
				<td>
					<select style="with: 120px;height: 25px;" name="status" id="status">
						<option value="">请选择</option>
						<option value="0">测试</option>
						<option value="1">发布</option>
					</select>
					<span style="color:red;width:auto">*</span>
            		<font color="red" id="statusinfo"></font>
				</td>
			</tr>
			<tr style="height: 5px;"></tr>
			<tr>
				<td>版本型号：</td>
				<td>
					<input type="text" style="width:175px;height:20px;" name="version" id="version">
					<span style="color: red;width:auto">*</span>
					<font color="red" id="versioninfo"></font>
				</td>
			</tr>
			<tr style="height: 5px;"></tr>
			<tr>	
				<td>上传文件：</td>
				<td>
					<input type="file" name="file" id="file" multiple="multiple"/> 
					<span style="color: red;width:auto">*</span>
					<font color="red" id="fileinfo"></font>
				</td>
			</tr>
			<tr style="height: 5px;"></tr>
			<tr>	
				<td>版本描述：</td>
				<td>
					<textarea rows="4" cols="40" name="describe" id="describe"></textarea>
				</td>
			</tr>
			<tr style="height: 5px;"></tr>
			<tr>
				<td colspan="2">
					<input type="button" value="提交" onclick="subForm()"/>&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
			<!-- <tr>	
				<td>推送内容：</td>
				<td>
					<textarea rows="4" cols="40" name="pushInfo" id="pushInfo"></textarea>
					<span style="color: red;width:auto">*</span>
					<font color="red" id="pushinfo"></font>
				</td>
			</tr>
			<tr style="height: 5px;"></tr>
			<tr>
				<td colspan="2">
					<input type="button" value="推送" onclick="xgPush()"/>
				</td>
			</tr> -->
		</table>
	</form> 
</body>

<script type="text/javascript">
$(function(){
	var flag = $("#flag").val();
	if(flag==1){
		alert("保存成功!");
	}
});
function sysStatus(){
	var appType = $("#appType").val();
	if(appType==''){
		$("#status").val("");
	}
}
function sysType(){
	var appname = $("#appName").val();
	if(appname==''){
		$("#appType").empty();
    	$("#appType").append('<option value="">请选择</option>');
    	$("#status").val("");
	}
	 $.ajax({
	        type: "post",
	        dataType: "json",
	        url: '<%=request.getContextPath()%>/appManager/getSysType.action',
	        data: {appname:appname},
	        success: function (data) {
	            if (data != "") {
	            	$("#appType").empty();
	            	$("#status").val("");
	            	$("#appType").append('<option value="">请选择</option>');
	            	var obj = eval(data);
	                for(var i=0;i<obj.length;i++){
	                	$("#appType").append('<option value="'+obj[i].systemType+'">'+obj[i].systemType+'</option>');
	                }   
	            }
	        }
	    });
}

// 提交
function subForm(){
	var nameflag = false;
	var typeflag = false;
	var statusflag = false;
	var fileflag = false;
	var versionflag = false;
	
	var name = $("#appName").val();
	var type = $("#appType").val();
	var status = $("#status").val();
	var version = $("#version").val();
	var file = $("#file").val();
	var acceptImg = "<img src='<%=request.getContextPath()%>/img/accept.png'>";
	
	if (name == "") {
		$("#nameinfo").html("请选择一种应用");
		nameflag = false;
		return nameflag;
	} else {
		$("#nameinfo").html(acceptImg);
		nameflag = true;
	}
	
	if (type == "") {
		$("#typeinfo").html("请选择一种版本类型");
		typeflag = false;
		return typeflag;
	} else {
		$("#typeinfo").html(acceptImg);
		typeflag = true;
	}
	if (status == "") {
		$("#statusinfo").html("请选择一种版本状态");
		statusflag = false;
		return statusflag;
	} else {
		$("#statusinfo").html(acceptImg);
		statusflag = true;
	}
	
	if (version.replace(/\s/g, "") == '') {
		$("#versioninfo").html("版本型号不能为空");
		versionflag = false;
		return versionflag;
	} else if (version.replace(/\s/g, "").length > '100') {
		$("#versioninfo").html("版本型号长度最大100个字，请重新输入!");
		versionflag = false;
		return versionflag;
	} else {
		$("#versioninfo").html(acceptImg);
		versionflag = true;
	}
	
	if (file == "") {
		$("#fileinfo").html("上传文件不能为空");
		fileflag = false;
		return fileflag;
	} else {
		$("#fileinfo").html(acceptImg);
		fileflag = true;
	}
	
	if(nameflag&typeflag&statusflag&versionflag&fileflag){
		var fm = document.getElementById("fm");
		fm.submit();
	}
	
}
// 信鸽推送
function xgPush(){
	var typeflag = false;
	var statusflag = false;
	var infoflag = false;
	
	var acceptImg = "<img src='<%=request.getContextPath()%>/img/accept.png'>";
	
	var type = $("#appType").val();
	var status = $("#status").val();
	var pushInfo = $("#pushInfo").val();
	
	if (type == "") {
		$("#typeinfo").html("请选择一种版本类型");
		typeflag = false;
		return typeflag;
	} else {
		$("#typeinfo").html(acceptImg);
		typeflag = true;
	}
	if (status == "") {
		$("#statusinfo").html("请选择一种版本状态");
		statusflag = false;
		return statusflag;
	} else {
		$("#statusinfo").html(acceptImg);
		statusflag = true;
	}
	if (pushInfo == "") {
		$("#pushinfo").html("请输入推送内容");
		infoflag = false;
		return infoflag;
	} else {
		$("#pushinfo").html(acceptImg);
		infoflag = true;
	}
	if(typeflag && statusflag && infoflag){
		$.ajax({
	        type: "post",
	        dataType: "json",
	        url: '<%=request.getContextPath()%>/appManager/xgPush.action',
	        data: {
	        		type:type,
	        		status:status,
	        		pushInfo:pushInfo
	        	  },
	        success: function (data) {
	        	if(data){
				    alert("推送成功");
				}else{
					alert("推送失败");
				}
	        },
	        error:function(){
	        	alert("网络错误，推送失败");
	        }
	    });
	}
	 
}
</script>

</html>