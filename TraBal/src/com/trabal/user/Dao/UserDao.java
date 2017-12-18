package com.trabal.user.Dao;

import java.util.ArrayList;

import com.trabal.user.Bean.UserBean;
import com.trabal.util.db.SqliteHelper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDao {
	private SqliteHelper helper;

	public UserDao(Context context,String telephone,int version) {
		this.helper = new SqliteHelper(context, telephone, null, version);
	}
	
	public void insert(UserBean user){
		SQLiteDatabase db=this.helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("telephone", user.getTelephone());
		values.put("password", user.getPassword());
		values.put("access_token", user.getAccess_token());
		db.close();
	}
	
	public void update(UserBean user){
		SQLiteDatabase db=this.helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("telephone", user.getTelephone());
		values.put("password", user.getPassword());
		values.put("access_token", user.getAccess_token());
		db.update("users", values, "access_token=?",new String[]{user.getAccess_token()});
		db.close();
	}
	
	public UserBean querryOne(String access_token){
		SQLiteDatabase db=this.helper.getWritableDatabase();
		String sql="select * from users where access_token=?";
		String[] selectionArgs=new String[]{access_token};
		Cursor c=db.rawQuery(sql, selectionArgs);
		UserBean user=new UserBean();
		while(c.moveToNext()){
			user.setTelephone(c.getString(c.getColumnIndex("telephone")));
			user.setPassword(c.getString(c.getColumnIndex("password")));
			user.setAccess_token(c.getString(c.getColumnIndex("access_token")));
		}
		return user;
	}
	
	public ArrayList<UserBean> querryAll(){
		ArrayList<UserBean> list=new ArrayList<UserBean>();
		SQLiteDatabase db=this.helper.getWritableDatabase();
		String sql="select * from users";
		Cursor c=db.rawQuery(sql, null);
		while(c.moveToNext()){
			UserBean user=new UserBean();
			user.setId(c.getLong(c.getColumnIndex("id")));
			user.setTelephone(c.getString(c.getColumnIndex("telephone")));
			user.setPassword(c.getString(c.getColumnIndex("password")));
			user.setAccess_token(c.getString(c.getColumnIndex("access_token")));
			list.add(user);
		}
		return list;
					
	}

}
