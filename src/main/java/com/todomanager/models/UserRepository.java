package com.todomanager.models;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * Extends CRUD interfaces by custom find methods
 * 
 * TODO: JpaRepository can be used here to support pagination and sorting.
 * 
 * @author Ayhan Dardagan
 *
 */
public interface UserRepository extends CrudRepository<User, Long> {

	/**
	 * Finds user by name.
	 * 
	 * @param userName
	 *            User name
	 * @return User list
	 */
	List<User> findByUserName(String userName);
}