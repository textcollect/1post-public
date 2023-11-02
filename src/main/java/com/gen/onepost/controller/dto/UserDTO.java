package com.gen.onepost.controller.dto;

public class UserDTO {
	private Long user_id;
	private String username;

	public UserDTO(long user_id, String username) {
		this.user_id = user_id;
		this.username = username;
	}

	// Getters & Setters
	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}