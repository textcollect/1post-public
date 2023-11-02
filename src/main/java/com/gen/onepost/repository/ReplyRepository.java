package com.gen.onepost.repository;

// This will be auto-implemented by Spring
// CRUD refers Create, Read, Update, Delete

import com.gen.onepost.repository.entity.Reply;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

// This interface will extend from the CRUDRepository provided by Springboot Framework
// then we can access all the methods from the CRUDRepository class
public interface ReplyRepository extends CrudRepository<Reply, Integer> {
	/*
	This method uses the @Query annotation to specify a custom SQL query that
	selects all `Reply` objects with the given `idPost`.
	The `:idPost` placeholder in the query is replaced with the value passed
	as the `idPost` parameter of the method.
	 */
	@Query("SELECT r FROM Reply r WHERE r.idPost = :idPost")
	List<Reply> findAllByPostId(@Param("idPost") int postId);

}
