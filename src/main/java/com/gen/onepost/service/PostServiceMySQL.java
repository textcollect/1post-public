package com.gen.onepost.service;

import com.gen.onepost.repository.PostRepository;
import com.gen.onepost.repository.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceMySQL implements PostService{

	// This PostServiceMySQL class has to depend on another class object to perform
	//CRUD actions (e.g. save, delete, all, findItemById)
	//dependent object class is the CRUDRepository class that is provided by Spring boot

	//to perform dependency injection -> access the CRUDRepository class through the
	// PostRepository interface that we have created
	private final PostRepository postRepository;

	public PostServiceMySQL(@Autowired PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@Override
	public Post save(Post post) {
		// Since we have done the dependency injection of the postRepository, therefore
		// now we can call any methods from the CRUDRepository Class
		return this.postRepository.save(post);
	}

	@Override
	public void delete(int postId) {
		this.postRepository.deleteById(postId); // dependency object(s) - CRUDRepository Class, JPARepository
	}

	@Override
	public List<Post> all() {
		// @Query - Query class provided by Springboot: SELECT * FROM Item
		// Repository class provided by Springboot: we do not need to write any query statement
		List<Post> result = new ArrayList<>();
		postRepository.findAll().forEach(result::add);
		return result;
	}

	@Override
	public Post findById(int postId) {
		// Optional is List that accept either a NULL(empty) or a Post
		Optional<Post> post = postRepository.findById(postId);
		Post postResponse = post.get();
		return postResponse;
	}
}
