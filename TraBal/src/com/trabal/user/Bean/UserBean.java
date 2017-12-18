package com.trabal.user.Bean;

import java.io.Serializable;

public class UserBean implements Serializable{
	private String telephone;
	private String password;
	private String access_token;
	
	public UserBean(String telephone, String password) {
		super();
		this.telephone = telephone;
		this.password = password;
	}
	
	public UserBean() {
		super();
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	

}
