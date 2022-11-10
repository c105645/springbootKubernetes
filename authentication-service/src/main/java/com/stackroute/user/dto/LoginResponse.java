package com.stackroute.user.dto;

public class LoginResponse {
	 private String username;
	 private Status status;
	 public enum Status {
		    Successful, unSuccessful
		  }
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	 
	 
}
