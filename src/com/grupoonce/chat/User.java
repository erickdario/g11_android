package com.grupoonce.chat;

public class User {
	private String city;
	private String userName;
	private String companysName;

	public User() {
	}

	public User(String userName, String city, String companysName) {
		this.userName = userName;
		this.city = city;
		this.companysName = companysName;
	}

	public String getCity() {
		return city;
	}

	public String getUserName() {
		return userName;
	}

	public String getCompanysName() {
		return companysName;
	}
}
