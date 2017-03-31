package com.galaxy.appupload.util;

public class Static_Commond {
	
	/**
	 * *********************************消息返回码定义******************************************************************
	 */
	//所有接口
	public static final String SUCCESS="0000";						//处理成功 
	public static final String ERROR="9999";						//未定义错误
	public static final String HEADERROR="1001";					//请求消息格式错误
	public static final String BOBYERROR="1002";					//响应消息格式错误
	public static final String RESULTNULL="2001";					//未查到相关信息
	/**
	 * *********************************消息返回码变量定义****************************************************************
	 */
	public static final String RESULTCODE="resultcode";				//返回结果码变量定义
	public static final String RESMSG="resMsg";						//返回消息提示变量定义
	public static final String STATE="state";						//为返回消息map的判断初始
	public static final String FALSE="false";
	public static final String TRUE="true";
}
