package com.trabal.user.Bean;

import java.io.Serializable;
import java.util.ArrayList;

import com.trabal.hotspot.Bean.HotSpotBean;

public class EvaluationBean implements Serializable{

	private String rate;
	private String mood;
	private String pic1;
	private String pic2;
	private String pic3;
	private String price;
	private HotSpotBean hs;
	private UserBean user;
	private String time;
	private ArrayList<UserBean> usr_like;
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ArrayList<UserBean> getUsr_like() {
		return usr_like;
	}
	public void setUsr_like(ArrayList<UserBean> usr_like) {
		this.usr_like = usr_like;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getMood() {
		return mood;
	}
	public void setMood(String mood) {
		this.mood = mood;
	}
	public String getPic1() {
		return pic1;
	}
	public void setPic1(String pic1) {
		this.pic1 = pic1;
	}
	public String getPic2() {
		return pic2;
	}
	public void setPic2(String pic2) {
		this.pic2 = pic2;
	}
	public String getPic3() {
		return pic3;
	}
	public void setPic3(String pic3) {
		this.pic3 = pic3;
	}
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public HotSpotBean getHs() {
		return hs;
	}
	public void setHs(HotSpotBean hs) {
		this.hs = hs;
	}
	public UserBean getUser() {
		return user;
	}
	public void setUser(UserBean user) {
		this.user = user;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
