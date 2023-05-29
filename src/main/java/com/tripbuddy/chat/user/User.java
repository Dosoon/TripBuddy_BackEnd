package com.tripbuddy.chat.user;

import org.springframework.web.socket.WebSocketSession;

public class User {

	private WebSocketSession session;
	private int userIdx;
	private String username;
	private int planNum;
	private int emojiIdx;

	public WebSocketSession getSession() {
		return session;
	}

	public void setSession(WebSocketSession session) {
		this.session = session;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public User(WebSocketSession session, int userIdx, String username, int planNum, int emojiIdx) {
		super();
		this.userIdx = userIdx;
		this.session = session;
		this.username = username;
		this.setPlanNum(planNum);
		this.emojiIdx = emojiIdx;
	}

	public int getPlanNum() {
		return planNum;
	}

	public void setPlanNum(int planNum) {
		this.planNum = planNum;
	}

	public int getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}

	public int getEmojiIdx() {
		return emojiIdx;
	}

	public void setEmojiIdx(int emojiIdx) {
		this.emojiIdx = emojiIdx;
	}
}
