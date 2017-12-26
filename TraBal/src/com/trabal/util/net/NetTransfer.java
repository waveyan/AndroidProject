package com.trabal.util.net;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.trabal.activity.Bean.ActivityBean;
import com.trabal.hotspot.Bean.HotSpotBean;
import com.trabal.user.Bean.EvaluationBean;
import com.trabal.user.Bean.UserBean;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

public class NetTransfer {

	private static String perfix = "http://172.27.10.174:8000/";
	private static String media_perfix = "http://172.27.10.174:8000/media/";
	private String status;
	private String msg;
	private String access_token;
	private String id;

	@SuppressLint("NewApi")
	public static String transfer(String url, String method,
			ArrayList<BasicNameValuePair> parameters, Boolean is_verify,
			String access_token, HashMap<String, Object> files)
			throws IOException {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		url = perfix + url + "/";
		HttpClient client = new DefaultHttpClient();
		//若有文件，也会上传
		if ("post".equals(method)) {
			HttpPost httppost = new HttpPost(url);
			if (is_verify) {
				httppost.setHeader("access-token", access_token);
			}
			httppost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
			HttpResponse response = client.execute(httppost);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				InputStream is = response.getEntity().getContent();
				String text = Tools.readFromStream(is);
				NetTransfer nt=new NetTransfer();
				nt.return_data(text);
				if (files != null && "success".equals(nt.getStatus())) {
					HashMap<String, String> foolish=new HashMap<String, String>();
					for (BasicNameValuePair nv : parameters){
						foolish.put(nv.getName(), nv.getValue());
					}
					foolish.put("action", "upload_pic");
					foolish.put("instance_id", nt.id);
					NetTransfer.asynctransfer(url, foolish, access_token, files);
				}
				return text;
			} else {
				return "{'msg':'请求失败','status':'fail'}";
			}
		} else if ("get".equals(method)) {
			
			if (parameters != null) {
				url += "?";
				for (BasicNameValuePair item : parameters) {
					url += item.getName().toString().trim() + "="
							+ URLEncoder.encode(item.getValue()) + "&";
				}
			}
			HttpGet httpget = new HttpGet(url);
			if (is_verify) {
				httpget.setHeader("access-token", access_token);
			}
			HttpResponse response = client.execute(httpget);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				InputStream is = response.getEntity().getContent();
				String text = Tools.readFromStream(is);
				return text;
			} else {
				return "{'msg':'请求失败'}";
			}
		} else if ("put".equals(method)) {
			HttpPut httpput = new HttpPut(url);
			if (is_verify) {
				httpput.setHeader("access-token", access_token);
			}
			httpput.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
			HttpResponse response = client.execute(httpput);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				InputStream is = response.getEntity().getContent();
				String text = Tools.readFromStream(is);
				return text;
			} else {
				return "{'msg':'请求失败','status':'fail'}";
			}
		}
		return "{'msg':'请求失败','status':'fail'}";

	}

	/**
	 * 上传文件专用
	 * @throws FileNotFoundException 
	 */
	@SuppressLint("NewApi") 
	private static void asynctransfer(String url,
			HashMap<String, String> data, String access_token,
			HashMap<String, Object> files) throws FileNotFoundException {

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

//		url = perfix + url + "/";

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams p = new RequestParams();
		client.addHeader("access-token", access_token);

			for (String key : data.keySet()) {
				p.put(key, data.get(key));
			}

		if (files != null) {
				for (String key : files.keySet()) {
					p.put(key, (File)files.get(key));
				}
		}

		client.post(url, p, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				String msg = new String(arg2);
				Log.e("failure", arg3.getMessage());

			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				String msg = new String(arg2);
				Log.e("success", msg);

			}
		});
		

	}

	public void return_data(String data) {
		try {
			JSONObject json = new JSONObject(data);
			msg = json.getString("msg");
			status = json.getString("status");
			access_token = json.getString("access_token");
			id=json.getString("id");

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public HotSpotBean handle_hs_data(String data) {
		try {
			HotSpotBean hsb = new HotSpotBean();
			JSONObject json = new JSONObject(data);
			hsb.setAddress(json.getString("address"));
			hsb.setArrvied(json.getInt("arrived"));
			hsb.setCity(json.getString("city"));
			hsb.setContent(json.getString("content"));
			hsb.setLike(json.getInt("like"));
			hsb.setName(json.getString("name"));
			hsb.setEnglishName(json.getString("englishname"));
			hsb.setPic1(this.media_perfix + json.getString("pic1"));
			hsb.setPic2(this.media_perfix + json.getString("pic2"));
			hsb.setPic3(this.media_perfix + json.getString("pic3"));
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
			hsb.setId(json.getString("id"));
			return hsb;

		} catch (JSONException e) {
			Log.e("hb", e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList handle_hs_list(String data) {
		try {
			ArrayList<HotSpotBean> hs_list = new ArrayList<HotSpotBean>();
			JSONObject json = new JSONObject(data);
			JSONArray json_list = json.getJSONArray("hotspot");
			for (int i = 0; i < json_list.length(); i++) {
				HotSpotBean hs =handle_hs_data(json_list.getString(i));
				hs_list.add(hs);
			}
			return hs_list;

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public UserBean handle_user_data(String data, UserBean user) {

		try {
			UserBean u = user;
			JSONObject json = new JSONObject(data);
			u.setAddress(json.getString("address"));
			u.setBirthday(json.getString("birthday"));
			u.setCredit(json.getInt("credit"));
			u.setEmail(json.getString("email"));
			u.setGender(json.getString("gender"));
			u.setIntroduction(json.getString("introduction"));
			u.setName(json.getString("name"));
			u.setPic((this.media_perfix + json.getString("pic")));
			try{
			u.setEb(handle_eb_list(json.getString("evaluation")));
			}
			catch(Exception e){}
			return u;
		} catch (JSONException e) {
			Log.e("handle_user_data", e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<UserBean> handle_follow_user_list(String data){
		
		try {
			UserBean user=new UserBean();
			ArrayList<UserBean> user_list=new ArrayList<UserBean>();
			JSONObject json = new JSONObject(data);
			JSONArray json_list = json.getJSONArray("following");
			for (int i = 0; i < json_list.length(); i++) {
				user =handle_user_data(json_list.getString(i),user);
				user_list.add(user);
			}
			return user_list;

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public ArrayList<UserBean> handle_fans_list(String data){
		
		try {
			
			ArrayList<UserBean> user_list=new ArrayList<UserBean>();
			JSONObject json = new JSONObject(data);
			JSONArray json_list = json.getJSONArray("follower");
			for (int i = 0; i < json_list.length(); i++) {
				UserBean user=new UserBean();
				user =handle_user_data(json_list.getString(i),user);
				user_list.add(user);
			}
			return user_list;

		} catch (JSONException e) {
			Log.e("handle_fans_list",e.getMessage());
			e.printStackTrace();
			return null;
		}
		
	}
	
	//不处理人的
	public EvaluationBean handle_eb_data(String data){
		try {
			JSONObject json = new JSONObject(data);
			EvaluationBean e=new EvaluationBean();
			try{
			e.setHs(handle_hs_data(json.getString("hotspot")));
			}catch(Exception e1){}
			e.setMood(json.getString("feeling"));
			e.setPic1(json.getString("pic1"));
			e.setPic2(json.getString("pic2"));
			e.setPic3(json.getString("pic3"));
			e.setPrice(json.getDouble("price"));
			e.setRate(json.getInt("rate"));
			e.setTime(json.getString("time"));
			return e;
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e("handle_eb_data",e.getMessage());
			return null;
		}
		
	}
	
	//
	public ArrayList<EvaluationBean> handle_eb_list(String data){
		try {
			ArrayList<EvaluationBean> eb_list = new ArrayList<EvaluationBean>();
			JSONObject json = new JSONObject(data);
			JSONArray json_list = json.getJSONArray("evaluation");
			
			for (int i = 0; i < json_list.length(); i++) {
				EvaluationBean eb=handle_eb_data(json_list.getString(i));
				eb_list.add(eb);
			}
			return eb_list;

		} catch (JSONException e) {
			Log.e("handle_eb_list", e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	
	public ActivityBean handle_ac_data(String data){
		JSONObject json;
		try {
			json = new JSONObject(data);
			ActivityBean ab=new ActivityBean();
			ab.setId(json.getString("id"));
			try{
			ab.setHsb(handle_hs_data(json.getString("hotspot")));
			}
			catch (JSONException e) {
			}
			ab.setIntroduction(json.getString("introduction"));
			ab.setPerson(json.getString("person"));
			ab.setPic1(this.media_perfix + json.getString("pic1"));
			ab.setPic2(this.media_perfix +json.getString("pic2"));
			ab.setPic3(this.media_perfix +json.getString("pic3"));
			ab.setPrice(json.getString("price"));
			ab.setSubject(json.getString("subject"));
			ab.setTelephone(json.getString("telephone"));
			ab.setTitle(json.getString("title"));
			ab.setType(json.getString("type"));
			ab.setWebsite(json.getString("website"));
			ab.setEnglish(json.getString("englishname"));
			ab.setTime(json.getString("time"));
			return ab;
		} catch (JSONException e) {
			Log.e("handle_ac_data",e.getMessage());
			e.printStackTrace();
			return null;
		}
		
	}
	
	public ArrayList handle_ac_list(String data) {
		try {
			ArrayList<ActivityBean> ab_list = new ArrayList<ActivityBean>();
			JSONObject json = new JSONObject(data);
			JSONArray json_list = json.getJSONArray("activity");
			for (int i = 0; i < json_list.length(); i++) {
				ActivityBean ab=handle_ac_data(json_list.getString(i));
				ab_list.add(ab);
			}
			return ab_list;

		} catch (JSONException e) {
			Log.e("handle_ac_list", e.getMessage());
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
