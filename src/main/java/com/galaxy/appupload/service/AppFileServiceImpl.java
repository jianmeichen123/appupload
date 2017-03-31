package com.galaxy.appupload.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.appupload.beans.AppFileBean;
import com.galaxy.appupload.dao.IAppFileDao;

@Service(value="appFileService")
public class AppFileServiceImpl implements IAppFileService{
	@Autowired
	private IAppFileDao dao;

	@Override
	public AppFileBean getAppFileById(String id) {
		return dao.getFileById(id);
	}

	
}
