package com.trabal.hotspot.Bean;

import java.io.Serializable;
import java.util.ArrayList;

public class DistrictBean implements Serializable {
	private String name;
	private String englishName;
	private String introduction;
	private ArrayList<HotSpotBean> hsb;
	private String pic;
	private String longitude;
	private String latitude;
	
	
	
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
	public DistrictBean() {
		hsb=new ArrayList<HotSpotBean>();
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
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public ArrayList<HotSpotBean> getHsb() {
		return hsb;
	}
	public void setHsb(ArrayList<HotSpotBean> hsb) {
		this.hsb = hsb;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	
	

}
