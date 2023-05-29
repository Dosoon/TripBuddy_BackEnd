package com.tripbuddy.wish.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tripbuddy.attraction.model.dto.AttractionDto;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WishDto {
	
	private int contentId;
	private int userId;

	public WishDto() {
		super();
	}

	public WishDto(int contentId, int userId) {
		super();
		this.contentId = contentId;
		this.userId = userId;
	}

	public int getContentId() {
		return contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
