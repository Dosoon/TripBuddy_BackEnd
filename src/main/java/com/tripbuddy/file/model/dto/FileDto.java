package com.tripbuddy.file.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileDto {

	private String originFileName;
	private String saveFileName;
	private String saveFolder;

	public FileDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FileDto(String originFileName, String saveFileName, String saveFolder) {
		super();
		this.originFileName = originFileName;
		this.saveFileName = saveFileName;
		this.saveFolder = saveFolder;
	}

	public String getOriginFileName() {
		return originFileName;
	}

	public void setOriginFileName(String originFileName) {
		this.originFileName = originFileName;
	}

	public String getSaveFileName() {
		return saveFileName;
	}

	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}

	public String getSaveFolder() {
		return saveFolder;
	}

	public void setSaveFolder(String saveFolder) {
		this.saveFolder = saveFolder;
	}

}
