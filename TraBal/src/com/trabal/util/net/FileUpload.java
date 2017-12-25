package com.trabal.util.net;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class FileUpload {
	private static String perfix = "http://172.27.10.174:8000/";
	private static String media_perfix = "http://172.27.10.174:8000/media/";

	@SuppressLint("NewApi")
	public static void transfer(String url, String method,
			ArrayList<BasicNameValuePair> parameters, Boolean is_verify,
			String access_token, ArrayList<HashMap<String, Object>> files)
			throws IOException {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		url = perfix + url + "/";

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams p=new RequestParams();
		client.addHeader("access-token", access_token);
		
		 p.put("title", "123");
		 p.put("subject", "123");
		 p.put("time", "2017-10-10 19:30:30");
		 p.put("person", "1");
		 p.put("price", "30");
		 p.put("introduction", "hahahhha");
		 p.put("type", "hhhhhhhhh");
		 p.put("telephone", "13222222222");
		 for(HashMap<String,Object> item:files){
			 for(String key : item.keySet()){
			 p.put(key,(File)item.get(key));
			 }
		 }
		 
		 client.post(url,p, new AsyncHttpResponseHandler(){

			@Override
			public void onFailure(int arg0,Header[] arg1, byte[] arg2,
					Throwable arg3) {
				Log.e("failure",arg3.getMessage());
				
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				String msg=new String(arg2);
				Log.e("success", msg);
				
			}});
	}

}
