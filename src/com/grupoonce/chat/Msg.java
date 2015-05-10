package com.grupoonce.chat;

public class Msg {
	private String email, message;
	private boolean local;
	private String attach;
	private String cle;
	private String teleAttach;
	private String time;
	private String date;

	public Msg(String cle, String email, String message, String attach,
			boolean local, String teleAttach, String time, String date) {
		this.email = email;
		this.message = message;
		this.local = local;
		this.attach = attach;
		this.cle = cle;
		this.teleAttach = teleAttach;
		this.time = time;
		this.date = date;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean getLocal() {
		return local;
	}

	public void setLocal(boolean local) {
		this.local = local;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getCle() {
		return cle;
	}

	public void setCle(String cle) {
		this.cle = cle;
	}

	public void setTeleAttach(String teleAttach) {
		this.teleAttach = teleAttach;
	}

	public String getTeleAttach() {
		return teleAttach;
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

}