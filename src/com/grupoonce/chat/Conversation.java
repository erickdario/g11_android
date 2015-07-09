package com.grupoonce.chat;

/**
 * Class to hold all the necessary information to manage the correct behavior of
 * a conversation within the application and with Firebase
 * 
 * @author erickdario
 *
 */
public class Conversation {

	private String companysName;
	private String userName;
	private String lastDateMsg;
	private Boolean read;

	public Conversation(String companysName, String userName,
			String lastDateMsg, Boolean read) {
		this.companysName = companysName;
		this.setUserName(userName);
		this.setLastDateMsg(lastDateMsg);
		this.setRead(read);
	}

	/**
	 * @return the companysName
	 */
	public String getCompanysName() {
		return companysName;
	}

	/**
	 * @param companysName
	 *            the companysName to set
	 */
	public void setCompanysName(String companysName) {
		this.companysName = companysName;
	}

	/**
	 * @return the lastDateMsg
	 */
	public String getLastDateMsg() {
		return lastDateMsg;
	}

	/**
	 * @param lastDateMsg
	 *            the lastDateMsg to set
	 */
	public void setLastDateMsg(String lastDateMsg) {
		this.lastDateMsg = lastDateMsg;
	}

	/**
	 * @return the read
	 */
	public Boolean getRead() {
		return read;
	}

	/**
	 * @param read
	 *            the read to set
	 */
	public void setRead(Boolean read) {
		this.read = read;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
