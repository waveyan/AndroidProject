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

import com.trabal.hotspot.Bean.HotSpotBean;

import android.annotation.SuppressLint;
import android.os.StrictMode;

public class NetTransfer {

	private static String perfix = "http://172.27.10.174:8000/";
	private static String media_perfix="http://172.27.10.174:8000/media/";
	private String status;
	private String msg;
	private String access_token;

	@SuppressLint("NewApi")
	public static String transfer(String url, String method,
			ArrayList<BasicNameValuePair> parameters,
			Boolean is_verify) throws IOException {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		url = perfix + url + "/";
		HttpClient client = new DefaultHttpClient();
		if ("post".equals(method) || "put".equals(method)) {
			HttpPost httppost = new HttpPost(url);
			if (is_verify) {
				httppost.setHeader("access_token","6d016c076367aa61010480f64244393c");
			}
			httppost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
			HttpResponse response = client.execute(httppost);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				InputStream is = response.getEntity().getContent();
				String text = Tools.readFromStream(is);
				return text;
			} else {
				return "{'msg':'����ʧ��','status':'fail'}";
			}
		} else if ("get".equals(method)) {
			url += "?";
			for (BasicNameValuePair item : parameters) {
				url += item.getName().toString().trim() + "="
						+ URLEncoder.encode(item.getValue()) + "&";
			}
			HttpGet httpget = new HttpGet(url);
			if (is_verify) {
				httpget.setHeader("access-token","6d016c076367aa61010480f64244393c");
			}
			HttpResponse response = client.execute(httpget);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				InputStream is = response.getEntity().getContent();
				String text = Tools.readFromStream(is);
				return text;
			} else {
				return "{'msg':'����ʧ��'}";
			}
		}
		return "{'msg':'����ʧ��','status':'fail'}";

	}

	public void return_data(String data) {
		try {
			JSONObject json = new JSONObject(data);
			msg = json.getString("msg");
			status = json.getString("status");
			access_token = json.getString("access_token");

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public HotSpotBean handle_HS_data(String data){
		try {
			HotSpotBean hsb=new HotSpotBean();
			JSONObject json = new JSONObject(data);
			hsb.setAddress(json.getString("address"));
			hsb.setArrvied(json.getInt("arrived"));
			hsb.setCity(json.getString("city"));
			hsb.setContent(json.getString("content"));
			hsb.setLike(json.getInt("like"));
			hsb.setName(json.getString("name"));
			hsb.setEnglishName(json.getString("englishname"));
			hsb.setPic1(this.media_perfix+json.getString("pic1"));
			hsb.setPic2(this.media_perfix+json.getString("pic2"));
			hsb.setPic3(this.media_perfix+json.getString("pic3"));
			hsb.setPic1_text(json.getString("pic1_text"));
			hsb.setPic2_text(json.getString("pic2_text"));
			hsb.setPic3_text(json.getString("pic3_text"));
			hsb.setPrice(json.getString("price"));
			hsb.setType(json.getString("type"));
			hsb.setWord(json.getString("word"));
			hsb.setWorktime(json.getString("worktime"));
			hsb.setTelephone(json.getString("telephone"));
			hsb.setUrl(json.getString("url"));
			return hsb;

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
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
