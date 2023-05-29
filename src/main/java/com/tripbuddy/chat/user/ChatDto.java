package com.tripbuddy.chat.user;

public class ChatDto {

	private String type;
	private int userIdx;
	private String username;
	private String content;
	private String timestamp;
	private int emojiIdx;
	private int planNum;
	private int idx;

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
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

	@Override
	public String toString() {
		return "ChatDto [type=" + type + ", userIdx=" + userIdx + ", username=" + username + ", content=" + content
				+ ", timestamp=" + timestamp + ", emojiIdx=" + emojiIdx + ", planNum=" + planNum + ", idx=" + idx + "]";
	}

	
	
}
