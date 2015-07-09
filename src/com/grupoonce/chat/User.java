package com.grupoonce.chat;

/**
 * Class to hold all the necessary information to manage the correct behavior of
 * a User within the application and with Firebase
 * 
 * @author erickdario
 *
 */
public class User {
	private String city;
	private String password;
	private String userName;
	private String companysName;

	public User(String userName, String city, String companysName,
			String password) {
		this.userName = userName;
		this.city = city;
		this.companysName = companysName;
		this.password = password;
	}

	/**
	 * @return the city this user belongs to
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the company's name
	 */
	public String getCompanysName() {
		return companysName;
	}

	/**
	 * @return the password for this account
	 */
	public String getPassword() {
		return password;
	}
}
