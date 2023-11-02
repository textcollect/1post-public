package com.gen.onepost.service;

import com.gen.onepost.repository.ReplyRepository;
import com.gen.onepost.repository.entity.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReplyServiceMySQL implements ReplyService{
	// This ReplyServiceMySQL class has to depend on another class object to perform
	//CRUD actions (e.g. save, delete, all, findItemById)
	//dependent object class is the CRUDRepository class that is provided by Spring boot

	//to perform dependency injection -> access the CRUDRepository class through the
	// ReplyRepository interface that we have created
	private final ReplyRepository replyRepository;

	public ReplyServiceMySQL(@Autowired ReplyRepository replyRepository) {
		this.replyRepository = replyRepository;
	}

	@Override
	public Reply save(Reply reply) {
		// Since we have done the dependency injection of the postRepository, therefore
		// now we can call any methods from the CRUDRepository Class
		return this.replyRepository.save(reply);
	}

	@Override
	public void delete(int replyId) {
		this.replyRepository.deleteById(replyId);
	}

	@Override
	public List<Reply> all() {
		// @Query - Query class provided by Springboot: SELECT * FROM Item
		// Repository class provided by Springboot: we do not need to write any query statement
		List<Reply> result = new ArrayList<>();
		this.replyRepository.findAll().forEach(result::add);
		return result;
	}

	@Override
	public List<Reply> findAllRepliesForPost(int postId) {
//		List<Reply> result = new ArrayList<>();
//		this.replyRepository.findAllByPostId(postId).forEach(result::add);
//		return result;

		return replyRepository.findAllByPostId(postId);
	}

	@Override
	public Reply findById(int replyId) {
		// Optional is List that accept either a NULL(empty) or a Post
		Optional<Reply> reply = this.replyRepository.findById(replyId);
		Reply replyResponse = reply.get();
		return replyResponse;
	}
}
