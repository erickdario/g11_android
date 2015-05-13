package com.grupoonce.chat;

public class Conversation {

	private String companysName;
	private String lastDateMsg;
	
	public Conversation(String companysName, String lastDateMsg){
		this.companysName = companysName;
		this.setLastDateMsg(lastDateMsg);
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
	 * @param lastDateMsg the lastDateMsg to set
	 */
	public void setLastDateMsg(String lastDateMsg) {
		this.lastDateMsg = lastDateMsg;
	}

}
