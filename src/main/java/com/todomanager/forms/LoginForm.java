package com.todomanager.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Form input data for registration form with boundaries.
 * 
 * @author Ayhan Dardagan
 * 
 */
public class LoginForm {

	/**
	 * User name.
	 */
	@NotNull
	@Size(min = 1, message = "Please provide an user name")
	private String userName;

	/**
	 * Password.
	 */
	@NotNull
	@Size(min = 1, message = "Please provide a password")
	private String password;

	/**
	 * Show unsuccessful message flag.
	 */
	private boolean showUnsuccessfulMsg = false;

	// Getters and setters

	/**
	 * Get user name.
	 * 
	 * @return User name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Set user name.
	 * 
	 * @param username
	 *            User name
	 */
	public void setUserName(final String username) {
		this.userName = username;
	}

	/**
	 * Get password.
	 * 
	 * @return Password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set password.
	 * 
	 * @param password
	 *            Password
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * Get show unsuccessul message flag.
	 * 
	 * @return Unsuccessul message flag
	 */
	public boolean isShowUnsuccessfulMsg() {
		return showUnsuccessfulMsg;
	}

	/**
	 * Set show unsuccessul message flag.
	 * 
	 * @param showUnsuccessfulMsg
	 *            Unsuccessul message flag
	 */
	public void setShowUnsuccessfulMsg(final boolean showUnsuccessfulMsg) {
		this.showUnsuccessfulMsg = showUnsuccessfulMsg;
	}
}