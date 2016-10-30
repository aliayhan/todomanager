package com.todomanager.services;

/**
 * Service interface for user operations like authentication and registration.
 * 
 * @author Ayhan Dardagan
 *
 */
public interface UserService {

	/**
	 * Authenticates user.
	 * 
	 * @param userName
	 *            User name
	 * @param password
	 *            Password
	 * @return Success or not
	 */
	boolean authenticate(String userName, String password);

	/**
	 * Registers user.
	 * 
	 * @param userName
	 *            User name
	 * @param email
	 *            Email address
	 * @param password
	 *            Password
	 * @return Success or not
	 */
	boolean registerUser(String userName, String email, String password);
}