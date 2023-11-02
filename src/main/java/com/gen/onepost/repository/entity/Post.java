package com.gen.onepost.repository.entity;

import com.gen.onepost.controller.dto.PostDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

//Repository package is part of the Model Component (MVC)
//Post is the entity class to use to map against the table from the database
@Entity
public class Post {
	//Properties/attributes - will be mapped to the columns of the database table
	//Need to use the Wrapper class on primitive data types - need to pass the datatype
	// as an object to CRUDRepository Class (provided by SpringBoot framework)

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPost; //retrieve post by ID - has to be an object
	private String postTitle;
	private String postDescribe;
	private String postFull;

	// Need to add columnDefinition and '@CreationTimestamp' in order for the sql to set the time
	@Column(name = "postTime", columnDefinition = "DATETIME")
	@CreationTimestamp
	private LocalDateTime postTime;
	private Integer user_id;

	// Post class used to map w the Database table
	// Any CRUD operation, JPA will make use of this Post class to map
	// For Read or Delete operation, the JPA requires empty constructor to
	//populate all the records from the db
	protected Post() {}

	// DTO = Data Transfer Object
	// Create and Update operation, JPA requires the PostDTO object to
	//be sent via the controller
	public Post(PostDTO postDTO) {
		//Transfer the object (with the data) to Entity Class from PostDTO for mapping
		// with the database table columns and to be able to save the data in the columns
		this.postTitle = postDTO.getPostTitle();
		this.postDescribe = postDTO.getPostDescribe();
		this.postFull = postDTO.getPostFull();
		this.postTime = postDTO.getPostTime();
		this.user_id = postDTO.getUser_id();
	}

	// Getter & Setter methods
	public Integer getIdPost() {
		return idPost;
	}

	public void setIdPost(Integer idPost) {
		this.idPost = idPost;
	}

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

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
}
