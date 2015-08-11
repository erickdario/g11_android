package com.grupoonce.chat;

/**
 * Class to hold all the necessary information to manage the correct behavior of
 * a message within the application and with Firebase
 * 
 * @author erickdario
 *
 */
public class Msg {
	private String text;
	private String sender;
	private String time;
	private String date;
	private String read;

	public Msg(String text, String sender, String time, String date, String read) {
		this.text = text;
		this.sender = sender;
		this.time = time;
		this.date = date;
		this.read = read;
	}

	/**
	 * @return get the text that was sent with this message
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            text on this message
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the type of sender for this message, either adviser or client
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * @param sender
	 *            set the type of sender for this phone, based on the account
	 *            that has a session active
	 */
	public void setLocal(String sender) {
		this.sender = sender;
	}

	/**
	 * @param time
	 *            time of the day this message was sent, taken from the phone
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the time this message was sent
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @return the date on which this message was sent
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            date on which this message was sent
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return "false" if message hasn't been read by the receiver, otherwise
	 *         "true"
	 */
	public String getRead() {
		return read;
	}

	/**
	 * @param read
	 *            value for this message if it has been read
	 */
	public void setRead(String read) {
		this.read = read;
	}

}