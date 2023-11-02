package com.gen.onepost.controller;

import com.gen.onepost.controller.dto.PostDTO;
import com.gen.onepost.repository.entity.Post;
import com.gen.onepost.service.PostService;
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
@RequestMapping("/post") //provides a URL for frontend to call the API endpoint
public class PostController {
	@Value("${image.folder}")
	private String imageFolder;

	private final PostService postService;

	@Autowired
	private JdbcTemplate jdbcTemplate;


	// dependency injection to access PostServiceMySQL methods
	// PostService is the interface for PostServiceMySQL
	public PostController(@Autowired PostService postService) {
		this.postService = postService;
	}

	// API endpoint to return all products to the front-end
	// frontend will issue a GET http request
	@CrossOrigin
	@GetMapping("/allpost")
	public Iterable<Post> getPosts() {

		// return in the Controller represents a response to the Client
		return postService.all();
	}

	// The id value will be sent from the front-end through the API URL parameter
	@CrossOrigin
	@GetMapping("/{postId}")
	public Post findPostById(@PathVariable Integer postId) {
		return postService.findById(postId);
	}

	@CrossOrigin
	@DeleteMapping("/del/{postId}")
	public void delete(@PathVariable Integer postId) {
		postService.delete(postId);
	}

	// Add & save new post to using the PostService method
	@CrossOrigin
	@PostMapping("/add")
	public void save(@RequestParam(name = "postTitle", required = true) String postTitle,
					 @RequestParam(name = "postDescribe", required = true) String postDescribe,
					 @RequestParam(name = "postFull", required = true) String postFull,
					 @RequestParam(name = "postTime", required = false) LocalDateTime postTime,
//					 @RequestParam(name = "user_id", required = false) int user_id,
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
		LocalDateTime newPostTime = now() ;
		LocalDateTime prevPostTime = jdbcTemplate.queryForObject("SELECT MAX(postTime) FROM Post WHERE user_id=?", new Object[]{userId2}, LocalDateTime.class);
		if (prevPostTime == null) {
			// Create the PostDTO and save the Post
			PostDTO postDto = new PostDTO(postTitle, postDescribe, postFull, postTime, userId2);
			postService.save(new Post(postDto));

		} else if (prevPostTime.plusDays(1).isBefore(newPostTime)) {
			PostDTO postDto = new PostDTO(postTitle, postDescribe, postFull, postTime, userId2);
			postService.save(new Post(postDto));

		} else {
			throw new IOException("Error adding new post. It has not been more than 24 hours since last post.");
		}

	}
}
