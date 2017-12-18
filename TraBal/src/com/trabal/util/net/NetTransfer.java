package com.trabal.util.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.StrictMode;

public class NetTransfer {
	
	private static String perfix="http://172.27.10.174:8000/";
	private String status;
	private String msg;
	private String access_token;
	
    @SuppressLint("NewApi")
	public static String transfer(String url, String method,ArrayList<BasicNameValuePair> parameters,
			Map<String, String> headers) throws IOException {
    	StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        url=perfix+url+"/";
		HttpClient client = new DefaultHttpClient();
		if ("post".equals(method)) {
			HttpPost httppost = new HttpPost(url);
			if (headers != null) {
				httppost.setHeader("access_token", headers.get("access_token")
						.toString());
			}
			httppost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
			HttpResponse response = client.execute(httppost);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				InputStream is = response.getEntity().getContent();
				String text = Tools.readFromStream(is);
				return text;
			} else {
				return "{'msg':'«Î«Û ß∞‹','status':'fail'}";
			}
		} else if ("get".equals(method)) {
			url += "?";
			for (BasicNameValuePair item : parameters) {
				url += item.getName().toString().trim() + "="
						+ URLEncoder.encode(item.getValue()) + "&";
			}
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = client.execute(httpget);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				InputStream is = response.getEntity().getContent();
				String text = Tools.readFromStream(is);
				return text;
			} else {
				return "{'msg':'«Î«Û ß∞‹'}";
			}
		}
		return "{'msg':'«Î«Û ß∞‹','status':'fail'}";

	}

	public void return_data(String data) {
		try {
			JSONObject json = new JSONObject(data);
			msg=json.getString("msg");
			status = json.getString("status");
			access_token = json.getString("access_token");

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	
	
}
