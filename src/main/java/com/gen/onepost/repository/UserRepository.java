package com.gen.onepost.repository;

import com.gen.onepost.repository.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
	Optional<User> findByUsername(String username);
}
