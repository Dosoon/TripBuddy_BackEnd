package com.tripbuddy.plan.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tripbuddy.attraction.model.dto.AttractionDto;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDto {

	private int contentId;
	private int planId;
	private int order;
	private int day;
	private AttractionDto attractionDto;

	public CourseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CourseDto(int contentId, int planId, int order, int day, AttractionDto attractionDto) {
		super();
		this.contentId = contentId;
		this.planId = planId;
		this.order = order;
		this.day = day;
		this.attractionDto = attractionDto;
	}

	public int getContentId() {
		return contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
	}

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public AttractionDto getAttractionDto() {
		return attractionDto;
	}

	public void setAttractionDto(AttractionDto attractionDto) {
		this.attractionDto = attractionDto;
	}

}
