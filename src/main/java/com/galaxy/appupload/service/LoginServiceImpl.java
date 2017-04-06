package com.galaxy.appupload.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.galaxy.appupload.beans.UserInfoBean;
import com.galaxy.appupload.dao.LoginDao;

@Repository
public class LoginServiceImpl implements LoginService {
	@Autowired
	private LoginDao loginDao;
	
	
	
	/**
	 * 获取登录信息
	 */
	public Boolean getLogin(String username, String password) {
		//dao
		UserInfoBean userInfoBean = loginDao.getUserInfo(username,password);
		if(userInfoBean!=null){
			return true;
		}
		return false;
	}
}
