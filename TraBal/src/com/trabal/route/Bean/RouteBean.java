package com.trabal.route.Bean;

import java.util.ArrayList;

import com.trabal.hotspot.Bean.HotSpotBean;

public class RouteBean {
	
	private String title;
	private String time;
	private String introduce;
	//创建路线的用户账号
	private String user;
	private int id;
	private ArrayList<HotSpotBean> hsb_list;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ArrayList<HotSpotBean> getHsb_list() {
		return hsb_list;
	}
	public void setHsb_list(ArrayList<HotSpotBean> hsb_list) {
		this.hsb_list = hsb_list;
	}
	public RouteBean() {
		hsb_list=new ArrayList<HotSpotBean>();
	}
	
	

}
