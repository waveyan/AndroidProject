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
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.trabal.hotspot.Bean.HotSpotBean;
import com.trabal.user.Bean.UserBean;

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
			Boolean is_verify,String access_token) throws IOException {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		url = perfix + url + "/";
		HttpClient client = new DefaultHttpClient();
		if ("post".equals(method)) {
			HttpPost httppost = new HttpPost(url);
			if (is_verify) {
				httppost.setHeader("access-token",access_token);
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
			if(parameters!=null){
				for (BasicNameValuePair item : parameters) {
					url += item.getName().toString().trim() + "="
							+ URLEncoder.encode(item.getValue()) + "&";
				}
			}
			HttpGet httpget = new HttpGet(url);
			if (is_verify) {
				httpget.setHeader("access-token",access_token);
			}
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
		else if("put".equals(method)){
			HttpPut httpput = new HttpPut(url);
			if (is_verify) {
				httpput.setHeader("access-token",access_token);
			}
			httpput.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
			HttpResponse response = client.execute(httpput);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				InputStream is = response.getEntity().getContent();
				String text = Tools.readFromStream(is);
				return text;
			} else {
				return "{'msg':'«Î«Û ß∞‹','status':'fail'}";
			}
		}
		return "{'msg':'«Î«Û ß∞‹','status':'fail'}";

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
			hsb.setIsfavour(json.getInt("isfavour"));
			return hsb;

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public UserBean handle_user_data(String data,UserBean user){
		
		try {
			UserBean u =user;
			JSONObject json = new JSONObject(data);
			u.setAddress(json.getString("address"));
			u.setBirthday(json.getString("birthday"));
			u.setCredit(json.getInt("credit"));
			u.setEmail(json.getString("email"));
			u.setGender(json.getString("gender"));
			u.setIntroduction(json.getString("introduction"));
			u.setName(json.getString("name"));
			u.setPic((this.media_perfix+json.getString("pic")));
			return u;
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
