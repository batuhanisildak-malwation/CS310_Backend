package com.fitplanner.payload;

import java.time.LocalDateTime;

public class Data<T> {
	private String returnMessage;
	private int returnCode;
	private LocalDateTime date;
	private T data;

	public Data() {
		// TODO Auto-generated constructor stub
	}

	public Data(String returnMessage, int returnCode, T data) {
		super();
		this.returnMessage = returnMessage;
		this.returnCode = returnCode;
		this.date = LocalDateTime.now();
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	public LocalDateTime getDate() {
		return date;
	}

}
