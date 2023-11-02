package com.gen.onepost.controller;

import com.gen.onepost.controller.dto.ReplyDTO;
import com.gen.onepost.repository.entity.Reply;
import com.gen.onepost.service.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

// Request mapping is to provide a URL route for frontend to call
//controller for /item, /user, /customer, /post
@RestController
@RequestMapping("/reply") //provides a URL for frontend to call the API endpoint
public class ReplyController {
	@Value("${image.folder}")
	private String imageFolder;

	private final ReplyService replyService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// dependency injection to access ReplyServiceMySQL methods
	// ReplyService is the interface for ReplyServiceMySQL
	public ReplyController(@Autowired ReplyService replyService) {
		this.replyService = replyService;
	}

	// API endpoint to return all products to the front-end
	// frontend will issue a GET http request
	@CrossOrigin
	@GetMapping("/allreply")
	public Iterable<Reply> getReplies() {
		// return in the Controller represents a response to the Client
		return replyService.all();
	}

	@CrossOrigin
	@GetMapping("/thisPostReply/{postId}")
	public Iterable<Reply> getPostReplies(@PathVariable Integer postId) {
		return replyService.findAllRepliesForPost(postId);
	}

	// The id value will be sent from the front-end through the API URL parameter
	@CrossOrigin
	@GetMapping("/{replyId}")
	public Reply findReplyById(@PathVariable Integer replyId) {
		return this.replyService.findById(replyId);
	}

	@CrossOrigin
	@DeleteMapping("/del/{replyId}")
	public void delete(@PathVariable Integer replyId) {
		this.replyService.delete(replyId);
	}

	// Add & save new reply using a ReplyService method
	@CrossOrigin
	@PostMapping("/add")
	public void save(@RequestParam(name = "replyText", required = true) String replyText,
					 @RequestParam(name = "replyTime", required = false) LocalDateTime replyTime,
//					 @RequestParam(name = "user_id", required = false) int user_id,
					 @RequestParam(name = "idPost", required = true) int idPost,
					 HttpServletRequest request) throws IOException {

		// Retrieve the currently logged-in user's username
		String username = request.getUserPrincipal().getName();

		// Retrieve the user_id from the database using the username
		Integer userId = jdbcTemplate.queryForObject("SELECT user_id FROM Users WHERE username = ?", new Object[]{username}, Integer.class);
		int userId2 = userId;
		/*
		In this line of code, `new Object[]{username}` is a vararg parameter that is passed to the `queryForObject` method as a parameterized SQL query. It specifies the value to be bound to the `?` placeholder in the query.
		In this case, the query is `"SELECT user_id FROM Users WHERE username = ?"`, and the value to be bound to the `?` is the `username` variable. So the query will retrieve the `user_id` from the `Users` table where the `username` column matches the value of the `username` variable.
		`Integer.class` specifies the expected type of the result, which is an integer value for the `user_id`. This tells the `queryForObject` method to return an object of type `Integer`.
		*/

		LocalDateTime newReplyTime = now() ;
		LocalDateTime prevReplyTime = jdbcTemplate.queryForObject("SELECT MAX(replyTime) FROM Reply WHERE user_id=?", new Object[]{userId2}, LocalDateTime.class);
		if (prevReplyTime == null) {
			// Create the ReplyDTO and save the Reply
			ReplyDTO replyDTO = new ReplyDTO(replyText, replyTime, userId2, idPost);
			replyService.save(new Reply(replyDTO));

		} else if (prevReplyTime.plusDays(1).isBefore(newReplyTime)) {
			ReplyDTO replyDTO = new ReplyDTO(replyText, replyTime, userId2, idPost);
			replyService.save(new Reply(replyDTO));

		} else {
			throw new IOException("Error adding new reply. It has not been more than 24 hours since last reply.");
		}

	}
}
