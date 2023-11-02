package com.gen.onepost.repository.entity;

import com.gen.onepost.controller.dto.ReplyDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

//Repository package is part of the Model Component (MVC)
//Post is the entity class to use to map against the table from the database
@Entity
public class Reply {
	//Properties/attributes - will be mapped to the columns of the database table
	//Need to use the Wrapper class on primitive data types - need to pass the datatype
	// as an object to CRUDRepository Class (provided by SpringBoot framework)

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idReply; //retrieve reply by ID - has to be an object
	private String replyText;

	// Need to add columnDefinition and '@CreationTimestamp' in order for the sql to set the time
	@Column(name = "replyTime", columnDefinition = "DATETIME")
	@CreationTimestamp
	private LocalDateTime replyTime;
	private Integer user_id;
	private Integer idPost;

	// Reply class used to map w the Database table
	// Any CRUD operation, JPA will make use of this Reply class to map
	// For Read or Delete operation, the JPA requires empty constructor to
	//populate all the records from the db
	protected Reply() {}

	// DTO = Data Transfer Object
	// Create and Update operation, JPA requires the ReplyDTO object to
	//be sent via the controller
	public Reply(ReplyDTO replyDTO) {
		//Transfer the object (with the data) to Entity Class from ReplyDTO for mapping
		// with the database table columns and to be able to save the data in the columns
		this.replyText = replyDTO.getReplyText();
		this.replyTime = replyDTO.getReplyTime();
		this.user_id = replyDTO.getUser_id();
		this.idPost = replyDTO.getIdPost();
	}

	// Getter & Setter methods
	public Integer getIdReply() {
		return idReply;
	}

	public void setIdReply(Integer idReply) {
		this.idReply = idReply;
	}

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

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getIdPost() {
		return idPost;
	}

	public void setIdPost(Integer idPost) {
		this.idPost = idPost;
	}
}
