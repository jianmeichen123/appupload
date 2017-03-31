<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>下载版本信息</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.12.4.js"></script>
</head>

<style type="text/css">
#appName{
width:150px;
height: 25px;
}
#appType{
width:150px;
height: 25px;
}
#status{
width:150px;
height: 25px;
}
#version{
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
	<form id="fm" action="<%=request.getContextPath()%>/appManager/download" method="post" enctype="multipart/form-data">  
	<input type="hidden" id="flag" name="flag" value="${flag}">
		<table>
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
					<select style="with: 120px;height: 25px;" name="appType" id="appType" onchange="verStatus()">
						<option value="">请选择</option>
					</select>
					<span style="color: red;width:auto">*</span>
					<font color="red" id="typeinfo"></font>
				</td>
			</tr>
			<tr style="height: 5px;"></tr>
			<tr>
				<td>版本状态：</td>
				<td>
					<select style="with: 120px;height: 25px;" name="status" id="status" onchange="sysVersion()">
						<option value="">请选择</option>
					</select>
					<span style="color: red;width:auto">*</span>
					<font color="red" id="statusinfo"></font>
				</td>
			</tr>
			<tr style="height: 5px;"></tr>
			<tr>
				<td>版本型号：</td>
				<td>
					<select style="with: 120px;height: 25px;" name="version" id="version">
						<option value="">请选择</option>
					</select>
					<span style="color: red;width:auto">*</span>
					<font color="red" id="versioninfo"></font>
				</td>
			</tr>
			<tr style="height: 5px;"></tr>
			<tr>
				<td colspan="2">
					<input type="button" value="下载" onclick="subForm()"/>
				</td>
			</tr>
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

function sysVersion(){
	var appname = $("#appName").val();
	var apptype = $("#appType").val();
	var status = $("#status").val();
	if(status==''){
		$("#version").empty();
    	$("#version").append('<option value="">请选择</option>');
	}else{
	 $.ajax({
	        type: "post",
	        dataType: "json",
	        url: '<%=request.getContextPath()%>/appManager/getSysVersion.action',
	        data: {appname:appname,apptype:apptype,status:status},
	        success: function (data) {
	            if (data != "") {
	            	$("#version").empty();
	            	$("#version").append('<option value="">请选择</option>');
	            	var obj = eval(data);
	                for(var i=0;i<obj.length;i++){
	                	$("#version").append('<option value="'+obj[i].versionNo+'">'+obj[i].versionNo+'</option>');
	                }   
	            }else{
	            	$("#version").empty();
	            	$("#version").append('<option value="">请选择</option>');
	            }
	        }
	    });
	}
}
function verStatus(){
	var appname = $("#appName").val();
	var apptype = $("#appType").val();
	 $.ajax({
	        type: "post",
	        dataType: "json",
	        url: '<%=request.getContextPath()%>/appManager/getVerStatus.action',
	        data: {appname:appname,apptype:apptype},
	        success: function (data) {
	            if (data != "") {
	            	$("#status").empty();
	            	$("#status").append('<option value="">请选择</option>');
	            	$("#version").empty();
	            	$("#version").append('<option value="">请选择</option>');
	            	var obj = eval(data);
	                for(var i=0;i<obj.length;i++){
	                	if(obj[i].versionStatus==0){
		                	$("#status").append('<option value="'+obj[i].versionStatus+'">测试</option>');
	                	}else{
	                		$("#status").append('<option value="'+obj[i].versionStatus+'">发布</option>');
	                	}
	                }   
	            }else{
	            	$("#status").empty();
	            	$("#status").append('<option value="">请选择</option>');
	            	$("#version").empty();
	            	$("#version").append('<option value="">请选择</option>');
	            }
	        }
	    });
}
function sysType(){
	var appname = $("#appName").val();
	if(appname==''){
		$("#appType").empty();
    	$("#appType").append('<option value="">请选择</option>');
	}
	 $.ajax({
	        type: "post",
	        dataType: "json",
	        url: '<%=request.getContextPath()%>/appManager/getSysType.action',
	        data: {appname:appname},
	        success: function (data) {
	            if (data != "") {
	            	$("#appType").empty();
	            	$("#appType").append('<option value="">请选择</option>');
	            	$("#status").empty();
	            	$("#status").append('<option value="">请选择</option>');
	            	$("#version").empty();
	            	$("#version").append('<option value="">请选择</option>');
	            	var obj = eval(data);
	                for(var i=0;i<obj.length;i++){
	                	$("#appType").append('<option value="'+obj[i].systemType+'">'+obj[i].systemType+'</option>');
	                }   
	            }else{
	            	$("#status").empty();
	            	$("#status").append('<option value="">请选择</option>');
	            	$("#version").empty();
	            	$("#version").append('<option value="">请选择</option>');
	            }
	        }
	    });
}
function subForm(){
	var nameflag = false;
	var typeflag = false;
	var statusflag = false;
	var versionflag = false;
	
	var name = $("#appName").val();
	var type = $("#appType").val();
	var status = $("#status").val();
	var version = $("#version").val();
	
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
	
	if (version == "") {
		$("#versioninfo").html("请选择一种版本号");
		versionflag = false;
		return versionflag;
	} else {
		$("#versioninfo").html(acceptImg);
		versionflag = true;
	}
	
	if(nameflag&typeflag&statusflag&versionflag){
		var fm = document.getElementById("fm");
		fm.submit();
	}
	
}
</script>

</html>