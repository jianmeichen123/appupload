package com.galaxy.appupload.dao;

import com.galaxy.appupload.beans.UserInfoBean;

public interface LoginDao {
	/**
	 * 获取用户信息
	 * @param username
	 * @param password
	 * @return
	 */
	public UserInfoBean getUserInfo(String username, String password);
}
