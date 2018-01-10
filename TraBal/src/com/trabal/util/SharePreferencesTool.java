package com.trabal.util;

import com.trabal.user.Bean.UserBean;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharePreferencesTool {

	private Context context;
	private SharedPreferences sp;

	public SharePreferencesTool(Context context, int mode) {
		this.context = context;
		sp = context.getSharedPreferences("userData", mode);
	}

	public void putInto(String key, String value) {
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public String popOut(String key) {
		return sp.getString(key, "");
	}
	
	public void remove(String key){
		Editor editor = sp.edit();
		editor.remove(key);
		editor.commit();
	}

}
