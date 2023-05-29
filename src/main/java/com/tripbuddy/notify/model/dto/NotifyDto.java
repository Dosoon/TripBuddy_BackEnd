package com.tripbuddy.notify.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotifyDto {

	private int notifyId;
	private int userId;
	private String type;
	private boolean read;
	private int senderId;
	private int targetId;
	private String notifyMsg;

	public NotifyDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NotifyDto(int notifyId, int userId, String type, boolean read, int senderId, int targetId,
			String notifyMsg) {
		super();
		this.notifyId = notifyId;
		this.userId = userId;
		this.type = type;
		this.read = read;
		this.senderId = senderId;
		this.targetId = targetId;
		this.notifyMsg = notifyMsg;
	}

	public int getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(int notifyId) {
		this.notifyId = notifyId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public int getTargetId() {
		return targetId;
	}

	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}

	public String getNotifyMsg() {
		return notifyMsg;
	}

	public void setNotifyMsg(String notifyMsg) {
		this.notifyMsg = notifyMsg;
	}

}
