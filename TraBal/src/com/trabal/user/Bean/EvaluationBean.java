package com.trabal.user.Bean;

import java.io.Serializable;

import com.trabal.hotspot.Bean.HotSpotBean;

public class EvaluationBean implements Serializable{

	private int rate;
	private String mood;
	private String pic1;
	private String pic2;
	private String pic3;
	private double price;
	private HotSpotBean hs;
	private UserBean user;
	private String time;
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
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
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
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
