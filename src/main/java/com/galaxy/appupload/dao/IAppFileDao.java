package com.galaxy.appupload.dao;

import com.galaxy.appupload.beans.AppFileBean;

public interface IAppFileDao {
	public void addAppFile(AppFileBean appFileBean);
	public AppFileBean getFileById(String id);
}
