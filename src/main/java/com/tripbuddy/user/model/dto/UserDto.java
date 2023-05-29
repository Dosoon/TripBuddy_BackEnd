package com.tripbuddy.user.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tripbuddy.file.model.dto.FileDto;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

	private int userId;
	private String id;
	private String password;
	private String name;
	private String tel;
	private String profileImg;
	private String email;
	private boolean isAdmin;
	private String lastAccess;

	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UserDto(int userId, String id, String password, String name, String tel, String profileImg, String email,
			boolean isAdmin, String lastAccess) {
		super();
		this.userId = userId;
		this.id = id;
		this.password = password;
		this.name = name;
		this.tel = tel;
		this.profileImg = profileImg;
		this.email = email;
		this.isAdmin = isAdmin;
		this.lastAccess = lastAccess;
	}



	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(String lastAccess) {
		this.lastAccess = lastAccess;
	}

	@Override
	public String toString() {
		return "UserDto [userId=" + userId + ", id=" + id + ", password=" + password + ", name=" + name + ", tel=" + tel
				+ ", profileImg=" + profileImg + ", email=" + email + ", isAdmin=" + isAdmin + ", lastAccess="
				+ lastAccess + "]";
	}
	
	

}
