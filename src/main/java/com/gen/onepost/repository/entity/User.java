package com.gen.onepost.repository.entity;

import com.gen.onepost.controller.dto.UserRegisterDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "email")
	private String email;

	@Column(name = "role")
	private String role;

	@Column(name = "enabled")
	private boolean enabled;

	// Need to add columnDefinition and '@CreationTimestamp' in order for the sql to set the time
	@Column(name = "creation", columnDefinition = "DATETIME")
	@CreationTimestamp
	private LocalDateTime creation;

	protected User() {}

	public User(UserRegisterDTO userRegisterDTO) {
		this.username = userRegisterDTO.getUsername();
		this.password = userRegisterDTO.getPassword();
		this.email = userRegisterDTO.getEmail();
		this.role = userRegisterDTO.getRole();
		this.enabled = userRegisterDTO.isEnabled();
		this.creation = userRegisterDTO.getCreation();
	}


	// Getters & Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
