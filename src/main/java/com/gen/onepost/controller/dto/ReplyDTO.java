package com.gen.onepost.controller.dto;

import java.time.LocalDateTime;

// The ReplyDTO Class will interface with the Client (Browser) for any HTTP request
//(e.g. GET, POST, PUT, DELETE methods) and is wired to the ReplyService Class to perform
//getItem, addItem, findItem, updateItem and deleteItem.
public class ReplyDTO {
	// variable names same as table column names
	// id is excluded as it is auto-generated
	private String replyText;
	private LocalDateTime replyTime;
	private int user_id;
	private int idPost;

	// Constructor
	public ReplyDTO(String replyText, LocalDateTime replyTime, int user_id, int idPost) {
		this.replyText = replyText;
		this.replyTime = replyTime;
		this.user_id = user_id;
		this.idPost = idPost;
	}

	// Standard Getter & Setter methods

	public String getReplyText() {
		return replyText;
	}

	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}

	public LocalDateTime getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(LocalDateTime replyTime) {
		this.replyTime = replyTime;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getIdPost() {
		return idPost;
	}

	public void setIdPost(int idPost) {
		this.idPost = idPost;
	}
}
