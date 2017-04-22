package com.backend.springboot.template.mongodb.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class User {

	@Id
	private String id;
	private String userName;
	private String userPassword;
	private boolean enabled;
	private String role;
	private Date dateCreation;
	
	public User() {
	}
	
	public User(String userName, String userPassword, boolean enabled,
				String role, Date dateCreation){
		this.userName = userName;
		this.userPassword = userPassword;
		this.enabled = enabled;
		this.role = role;
		this.dateCreation = dateCreation;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
