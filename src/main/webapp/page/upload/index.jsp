<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>  
<head>  
<meta charset="utf-8">  
<title>上传图片</title>  
</head>  
<body>  
	<form action="R2/star/uploadFile" method="post" enctype="multipart/form-data">  
	
		<table>
			<tr>
				<td>应用名称</td>
				<td>
					<select style="with: 120px;height: 25px;" name="appName">
						<option value="star">繁星AppIos</option>
						<option value="other">繁星AppAndroid</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>版本号</td>
				<td>
					<input type="text" style="width: 120px;height: 22px;" name="appVersion">
				</td>
			</tr>
			<tr>	
				<td>大图标</td>
				<td>
					<input type="file" name="tdjgamtam" /> 
				</td>
			</tr>
			<tr>	
				<td>小图标</td>
				<td>
					<input type="file" name="tdjgamtam" /> 
				</td>
			</tr>
			<tr>	
				<td>上传文件</td>
				<td>
					<input type="file" name="tdjgamtam" /> 
				</td>
			</tr>
			
			
			<tr>
				<td colspan="2">
					<input type="submit" value="Submit" />
				</td>
			</tr>
		</table>
		
	</form>  
	
	<br>
	<hr>
	
	<a href="R2/downloadAppFile?fileName=a15.mp3">下载</a>
	
</body>  
</html>  