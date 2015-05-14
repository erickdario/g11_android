package com.grupoonce.chat;

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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSender() {
		return sender;
	}

	public void setLocal(String sender) {
		this.sender = sender;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTime() {
		return time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public String getRead() {
		return read;
	}

	public void setRead(String read) {
		this.read = read;
	}

}