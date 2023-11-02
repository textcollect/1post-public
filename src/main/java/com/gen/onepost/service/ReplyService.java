package com.gen.onepost.service;

import com.gen.onepost.repository.entity.Reply;

import java.util.List;

public interface ReplyService {
	//save method is for 2 purposes - Create new post & Update existing reply
	Reply save(Reply reply);

	//Delete item from database - based on replyId
	void delete(int replyId);

	//Read all item from database
	List<Reply> all();

	//Read all reply from database from postId
	List<Reply> findAllRepliesForPost(int postId);

	//Read an item from database - based on replyId
	Reply findById(int replyId);
}
