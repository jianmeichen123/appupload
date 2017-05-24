<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加应用信息</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.12.4.js"></script>
</head>

<style type="text/css">
*{ margin:0; padding:0;}
#demo{ margin:50px auto; width:198px; min-height:40px; background:#CF9}
#systemType{
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
	<form id ="fm" action="<%=request.getContextPath()%>/appManager/saveApplication" method="post" enctype="multipart/form-data">  
	<input type="hidden" id="flag" name="flag" value="${flag}">
		<table>
			<tr>
				<td>应用名称：</td>
				<td>
					<input type="text" style="width:177px;height:20px;" name="appcode" id="appcode">
					<span style="color:red;width:auto">*</span>
            		<font color="red" id="codeinfo"></font>
				</td>
			</tr>
			<tr style="height: 5px;"></tr>
			<tr>
				<td>版本类型：</td>
				<td>
					<select style="with: 120px;height: 25px;" name="systemType" id="systemType">
						<option value="">请选择</option>
						<option value="0">ios</option>
						<option value="1">Android</option>
					</select>
					<span style="color:red;width:auto">*</span>
            		<font color="red" id="typeinfo"></font>
				</td>
			</tr>
			<tr style="height: 5px;"></tr>
			<tr>	
				<td>大图标：</td>
				<td>
					<input type="file" name="bigLogo" id="bigLogo" onchange="previewBigLogo(this)"/>
				</td>
			</tr>
			<tr>	
				<td></td>
				<td id="previewBigLogo">
				</td>
			</tr>
			<tr style="height: 5px;"></tr>
			<tr>	
				<td>小图标：</td>
				<td>
					<input type="file" name="smallLogo" id="smallLogo" onchange="previewSmallLogo(this)"/> 
				</td>
			</tr>
			<tr>	
				<td></td>
				<td id="previewSmallLogo">
				</td>
			</tr>
			<tr style="height: 5px;"></tr>
			<tr>
				<td colspan="2">
					<input type="button" value="提交" onclick="subForm()"/>
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

function subForm(){
	var code = $("#appcode").val();
	var type = $("#systemType").val();
	var acceptImg = "<img src='<%=request.getContextPath()%>/img/accept.png'>";
	var codeflag = false;
	var typeflag = false;
	if (code.replace(/\s/g, "") == '') {
		$("#codeinfo").html("应用名称不能为空");
		codeflag = false;
		return codeflag;
	} else if (code.replace(/\s/g, "").length > '100') {
		$("#codeinfo").html("应用名称长度最大100个字，请重新输入!");
		codeflag = false;
		return codeflag;
	} else {
		$("#codeinfo").html(acceptImg);
		codeflag = true;
	}
	if (type == "") {
		$("#typeinfo").html("请选择一种版本类型");
		typeflag = false;
		return typeflag;
	} else {
		$("#typeinfo").html(acceptImg);
		typeflag = true;
	}
	if(codeflag&typeflag){
		var fm = document.getElementById("fm");
		fm.submit();
	}
}

function previewBigLogo(file)
{
  var MAXWIDTH  = 260; 
  var MAXHEIGHT = 180;
  var div = document.getElementById('previewBigLogo');
  if (file.files && file.files[0])
  {
      div.innerHTML ='<img id=imgBigLogo>';
      var img = document.getElementById('imgBigLogo');
      img.onload = function(){
	        var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
	        img.width  =  rect.width;
	        img.height =  rect.height;
	        //img.style.marginTop = rect.top+'px';
      }
      var reader = new FileReader();
      reader.onload = function(evt){img.src = evt.target.result;}
      reader.readAsDataURL(file.files[0]);
      $("#previewBigLogo").show();
  }else{
	//兼容IE
    var sFilter='filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
    file.select();
    var src = document.selection.createRange().text;
    div.innerHTML = '<img id=imgBigLogo>';
    var img = document.getElementById('imgBigLogo');
    img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
    var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
    status =('rect:'+rect.top+','+rect.left+','+rect.width+','+rect.height);
    div.innerHTML = "<div id=divhead style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;"+sFilter+src+"\"'></div>";
  }
}

function previewSmallLogo(file)
{
  var MAXWIDTH  = 260; 
  var MAXHEIGHT = 180;
  var div = document.getElementById('previewSmallLogo');
  if (file.files && file.files[0])
  {
      div.innerHTML ='<img id=imgheadSmallLogo>';
      var img = document.getElementById('imgheadSmallLogo');
      img.onload = function(){
	        var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
	        img.width  =  rect.width;
	        img.height =  rect.height;
	        //img.style.marginTop = rect.top+'px';
      }
      var reader = new FileReader();
      reader.onload = function(evt){img.src = evt.target.result;}
      reader.readAsDataURL(file.files[0]);
      $("#previewSmallLogo").show();
  }else{
	//兼容IE
    var sFilter='filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
    file.select();
    var src = document.selection.createRange().text;
    div.innerHTML = '<img id=imgheadSmallLogo>';
    var img = document.getElementById('imgheadSmallLogo');
    img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
    var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
    status =('rect:'+rect.top+','+rect.left+','+rect.width+','+rect.height);
    div.innerHTML = "<div id=divheads style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;"+sFilter+src+"\"'></div>";
  }
}

function clacImgZoomParam( maxWidth, maxHeight, width, height ){
    var param = {top:0, left:0, width:width, height:height};
    if( width>maxWidth || height>maxHeight )
    {
        rateWidth = width / maxWidth;
        rateHeight = height / maxHeight;
        if( rateWidth > rateHeight )
        {
            param.width =  maxWidth;
            param.height = Math.round(height / rateWidth);
        }else
        {
            param.width = Math.round(width / rateHeight);
            param.height = maxHeight;
        }
    }
    param.left = Math.round((maxWidth - param.width) / 2);
    param.top = Math.round((maxHeight - param.height) / 2);
    return param;
}

</script>

</html>