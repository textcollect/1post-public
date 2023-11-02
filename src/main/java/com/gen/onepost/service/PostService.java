package com.gen.onepost.service;

import com.gen.onepost.repository.entity.Post;

import java.util.List;

public interface PostService {
	//save method is for 2 purposes - Create new post & Update existing post
	Post save(Post post);

	//Delete item from database - based on postId
	void delete(int postId);

	//Read all item from database
	List<Post> all();

	//Read an item from database - based on postId
	Post findById(int postId);
}
