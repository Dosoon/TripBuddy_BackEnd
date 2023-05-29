package com.tripbuddy.plan.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tripbuddy.user.model.dto.UserDto;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlanDto {

	private int planId;
	private String title;
	private String startDate;
	private String endDate;
	private String lastModified;
	private String thumbnail;
	private List<CourseDto> courses;
	private List<UserDto> members;

	public PlanDto() {
		super();
	}

	public PlanDto(int planId, String title, String startDate, String endDate, String lastModified,
			List<CourseDto> courses, String thumbnail, List<UserDto> members) {
		super();
		this.planId = planId;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.lastModified = lastModified;
		this.courses = courses;
		this.thumbnail = thumbnail;
		this.members = members;
	}

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public List<CourseDto> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseDto> courses) {
		this.courses = courses;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public List<UserDto> getMembers() {
		return members;
	}

	public void setMembers(List<UserDto> members) {
		this.members = members;
	}

}
