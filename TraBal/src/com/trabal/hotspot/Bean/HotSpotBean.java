package com.trabal.hotspot.Bean;

import java.io.Serializable;
import java.util.ArrayList;

import com.trabal.activity.Bean.ActivityBean;
import com.trabal.user.Bean.EvaluationBean;

public class HotSpotBean implements Serializable {
	private String id;
	private String name;
	private String englishName;
	private String word;
	private String worktime;
	private String price;
	private String pic1;
	private String pic3;
	private String pic2;
	private String pic2_text;
	private String pic3_text;
	private String pic1_text;
	private String content;
	private String address;
	private int like;
	private String type;
	private int arrvied;
	private String url;
	private String telephone;
	private int isfavour;
	private ArrayList<EvaluationBean> ebs; 
	private ArrayList<ActivityBean> abs;
	private DistrictBean db;
	private String longitude;
	private String latitude;
	
	public HotSpotBean() {
		ebs=new ArrayList<EvaluationBean>();
		abs=new ArrayList<ActivityBean>();
	}
	
	
	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	public ArrayList<EvaluationBean> getEbs() {
		return ebs;
	}
	public void setEbs(ArrayList<EvaluationBean> ebs) {
		this.ebs = ebs;
	}
	public ArrayList<ActivityBean> getAbs() {
		return abs;
	}
	public void setAbs(ArrayList<ActivityBean> abs) {
		this.abs = abs;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getWorktime() {
		return worktime;
	}
	public void setWorktime(String worktime) {
		this.worktime = worktime;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	public DistrictBean getDb() {
		return db;
	}
	public void setDb(DistrictBean db) {
		this.db = db;
	}
	public String getPic1() {
		return pic1;
	}
	public void setPic1(String pic1) {
		this.pic1 = pic1;
	}
	
	public String getPic3() {
		return pic3;
	}
	public void setPic3(String pic3) {
		this.pic3 = pic3;
	}
	public String getPic2() {
		return pic2;
	}
	public void setPic2(String pic2) {
		this.pic2 = pic2;
	}
	public String getPic2_text() {
		return pic2_text;
	}
	public void setPic2_text(String pic2_text) {
		this.pic2_text = pic2_text;
	}
	public String getPic3_text() {
		return pic3_text;
	}
	public void setPic3_text(String pic3_text) {
		this.pic3_text = pic3_text;
	}
	public String getPic1_text() {
		return pic1_text;
	}
	public void setPic1_text(String pic1_text) {
		this.pic1_text = pic1_text;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getLike() {
		return like;
	}
	public void setLike(int like) {
		this.like = like;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getArrvied() {
		return arrvied;
	}
	public void setArrvied(int arrvied) {
		this.arrvied = arrvied;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public int getIsfavour() {
		return isfavour;
	}
	public void setIsfavour(int isfavour) {
		this.isfavour = isfavour;
	}
	

}
