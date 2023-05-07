package com.fitplanner.payload;

import java.time.LocalDateTime;

public class Message {
	private String returnMessage;
	private int returnCode;
	private LocalDateTime date;

	public Message() {
		this.date = LocalDateTime.now();
	}

	public Message(String returnMessage, int returnCode) {
		super();
		this.returnMessage = returnMessage;
		this.returnCode = returnCode;
		this.date = LocalDateTime.now();
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
