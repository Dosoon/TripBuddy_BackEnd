package com.tripbuddy.attraction.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AreaDto {
	private int areaCode;
	private String areaName;
	
	
	public AreaDto(int areaCode, String areaName) {
		super();
		this.areaCode = areaCode;
		this.areaName = areaName;
	}


	public int getAreaCode() {
		return areaCode;
	}


	public void setAreaCode(int areaCode) {
		this.areaCode = areaCode;
	}


	public String getAreaName() {
		return areaName;
	}


	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	
	
	
	
}
