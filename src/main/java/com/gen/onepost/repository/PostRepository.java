package com.gen.onepost.repository;

// This will be auto-implemented by Spring
// CRUD refers Create, Read, Update, Delete

import com.gen.onepost.repository.entity.Post;
import org.springframework.data.repository.CrudRepository;

// This interface will extend from the CRUDRepository provided by Springboot Framework
// then we can access all the methods from the CRUDRepository class
public interface PostRepository extends CrudRepository<Post, Integer> {
}
