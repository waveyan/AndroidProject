package com.trabal.user.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.trabal.user.Bean.UserBean;
import com.trabal.user.Dao.UserDao;

public class UserService {
	
	private UserDao userDao;
	
	public UserService(Context context, String telephone,int version){
		userDao=new UserDao(context, telephone, version);
	}
	
	public void InsertUser(UserBean user){
		userDao.insert(user);
	}
	
	public void UpdateUser(UserBean user){
		userDao.update(user);
	}
	
	public UserBean QueryAUser(String access_token){
		return userDao.querryOne(access_token);
	}
	
	
}
