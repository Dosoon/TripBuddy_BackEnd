package com.tripbuddy.chat.user;

public class NoticeDto {

	private String type;
	private int result;
	private int idx;

	public NoticeDto(String type, int result, int idx, String msg) {
		super();
		this.type = type;
		this.result = result;
		this.idx = idx;
		this.msg = msg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	private String msg;
}
