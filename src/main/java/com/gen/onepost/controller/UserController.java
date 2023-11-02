package com.gen.onepost.controller;


import com.gen.onepost.controller.dto.UserDTO;
import com.gen.onepost.controller.dto.UserRegisterDTO;
import com.gen.onepost.repository.entity.User;
import com.gen.onepost.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserDetailsService userDetailsService;

	private final UserService userService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public UserController(@Autowired UserService userService) {
		this.userService = userService;
	}

	@CrossOrigin
	@GetMapping("/users/{username}")
	public User getUserByUsername(@PathVariable String username) {
		return userService.findByUsername(username);
	}

	@CrossOrigin
	@GetMapping("/all")
	public Iterable<User> getUsers() {
		return userService.all();
	}

	@CrossOrigin
	@GetMapping("/{userId}")
	public User findUserById(@PathVariable Integer userId) {
		return userService.findById(userId);
	}

	@CrossOrigin
	@PostMapping("/add")
	public void save(@RequestParam(name = "email", required = true) String email,
					 @RequestParam(name = "username", required = true) String username,
					 @RequestParam(name = "password", required = true) String password,
//					 @RequestParam(name = "role", required = false) String role, // Not needed anymore. See below
					 @RequestParam(name = "enabled", required = false) boolean enabled,
					 @RequestParam(name = "creation", required = false) LocalDateTime creation) throws IOException {

		// encrypt the password sent from frontend to stare the hashed password into db instead
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = password;
		String encodedPassword = encoder.encode(rawPassword);

//		30minutes timeout before adding another user
		LocalDateTime newUserTime = now();
		LocalDateTime prevUserTime = jdbcTemplate.queryForObject("SELECT MAX(creation) FROM Users", LocalDateTime.class);

		if (prevUserTime.plusMinutes(30).isBefore(newUserTime)) {
			String role = "ROLE_USER"; // by default, always save new users with "USER" role to prevent misuse at frontend
			UserRegisterDTO userRegisterDTO = new UserRegisterDTO(email, username, encodedPassword, role, enabled, creation);
			userService.save(new User(userRegisterDTO));

		} else {
			throw new IOException("The account is trying to add another user before 30mins since the last one.");
		}
	}


	@CrossOrigin
	@DeleteMapping("/del/{userId}")
	public void delete(@PathVariable Integer userId) {
		userService.delete(userId);
	}

	// Get a list of all users with user_id and username
	@CrossOrigin
	@GetMapping("/allusers")
	public List<UserDTO> getAllUsersBasic() {
		List<UserDTO> userDTOList = new ArrayList<>();
		List<User> userList = userService.all();

		// Loop through all users and add only user_id and username to the UserDTO list
		for (User user : userList) {
			userDTOList.add(new UserDTO(user.getId(), user.getUsername()));
		}
		return userDTOList;
	}
}
