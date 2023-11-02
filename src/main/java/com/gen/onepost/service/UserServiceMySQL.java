package com.gen.onepost.service;

import com.gen.onepost.repository.UserRepository;
import com.gen.onepost.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceMySQL implements UserService{
	private final UserRepository userRepository;
	public UserServiceMySQL(@Autowired UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User save(User user) {
		return this.userRepository.save(user);
	}

	@Override
	public void delete(int userId) {
		this.userRepository.deleteById(userId);
	}

	@Override
	public List<User> all() {
		List<User> result = new ArrayList<>();
		userRepository.findAll().forEach(result::add);
		return result;
	}

	@Override
	public User findById(int userId) {
		Optional<User> user = userRepository.findById(userId);
		User userResponse = user.get();
		return userResponse;
	}

	@Override
	public User findByUsername(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		User userResponse = user.get();
		return userResponse;
	}

}
