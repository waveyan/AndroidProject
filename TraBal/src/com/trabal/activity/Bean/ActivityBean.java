package com.trabal.activity.Bean;

import java.io.Serializable;
import java.util.ArrayList;

import com.trabal.hotspot.Bean.HotSpotBean;
import com.trabal.user.Bean.UserBean;

public class ActivityBean implements Serializable{
	private String id;
	private String title;
	private String subject;
	private String type;
	private String introduction;
	private String person;
	private String telephone;
	private String website;
	private String pic1;
	private String pic2;
	private String pic3;
	private String pic4;
	private String price;
	private String english;
	private String time;
	private HotSpotBean hsb;
	private UserBean host_user;
	private ArrayList<UserBean> who_want_to_go;
	private int isfavour;
	
	
	public int getIsfavour() {
		return isfavour;
	}
	public void setIsfavour(int isfavour) {
		this.isfavour = isfavour;
	}
	public ActivityBean() {
		hsb=new HotSpotBean();
		host_user=new UserBean();
		who_want_to_go=new ArrayList<UserBean>();
	}
	public ArrayList<UserBean> getWho_want_to_go() {
		return who_want_to_go;
	}
	public void setWho_want_to_go(ArrayList<UserBean> who_want_to_go) {
		this.who_want_to_go = who_want_to_go;
	}
	public UserBean getHost_user() {
		return host_user;
	}
	public void setHost_user(UserBean host_user) {
		this.host_user = host_user;
	}
	public String getPic4() {
		return pic4;
	}
	public void setPic4(String pic4) {
		this.pic4 = pic4;
	}
	public String getEnglish() {
		return english;
	}
	public void setEnglish(String english) {
		this.english = english;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
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
	public HotSpotBean getHsb() {
		return hsb;
	}
	public void setHsb(HotSpotBean hsb) {
		this.hsb = hsb;
	}

}
