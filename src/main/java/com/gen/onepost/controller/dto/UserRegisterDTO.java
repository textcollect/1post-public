package com.gen.onepost.controller.dto;

import java.time.LocalDateTime;

public class UserRegisterDTO {
	private String username;
	private String email;
	private String password;
	private String role;
	private boolean enabled;
	private LocalDateTime creation;

	public UserRegisterDTO(String email, String username, String password, String role, boolean enabled, LocalDateTime creation) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.role = role;
		this.enabled = enabled;
		this.creation = creation;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public LocalDateTime getCreation() {
		return creation;
	}

	public void setCreation(LocalDateTime creation) {
		this.creation = creation;
	}
}
