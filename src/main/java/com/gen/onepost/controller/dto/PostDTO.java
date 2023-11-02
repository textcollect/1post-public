package com.gen.onepost.controller.dto;

import java.time.LocalDateTime;

// The PostDTO Class will interface with the Client (Browser) for any HTTP request
//(e.g. GET, POST, PUT, DELETE methods) and is wired to the PostService Class to perform
//getItem, addItem, findItem, updateItem and deleteItem.
public class PostDTO {
	// variable names same as table column names
	// id is excluded as it is auto-generated
	private String postTitle;
	private String postDescribe;
	private String postFull;
	private LocalDateTime postTime;
	private int user_id;

	// Constructor
	public PostDTO(String postTitle, String postDescribe, String postFull, LocalDateTime postTime, int user_id) {
		this.postTitle = postTitle;
		this.postDescribe = postDescribe;
		this.postFull = postFull;
		this.postTime = postTime;
		this.user_id = user_id;
	}

	// Standard Getter & Setter methods
	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getPostDescribe() {
		return postDescribe;
	}

	public void setPostDescribe(String postDescribe) {
		this.postDescribe = postDescribe;
	}

	public String getPostFull() {
		return postFull;
	}

	public void setPostFull(String postFull) {
		this.postFull = postFull;
	}

	public LocalDateTime getPostTime() {
		return postTime;
	}

	public void setPostTime(LocalDateTime postTime) {
		this.postTime = postTime;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
}
