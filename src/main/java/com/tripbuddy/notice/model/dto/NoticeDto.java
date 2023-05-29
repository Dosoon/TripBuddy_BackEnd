package com.tripbuddy.notice.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoticeDto {

	private int noticeId;
	private int userId;
	private String userName;
	private String subject;
	private String content;
	private String registerTime;

	public NoticeDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NoticeDto(int noticeId, int userId, String userName, String subject, String content, String registerTime) {
		super();
		this.noticeId = noticeId;
		this.userId = userId;
		this.userName = userName;
		this.subject = subject;
		this.content = content;
		this.registerTime = registerTime;
	}

	public int getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

}
