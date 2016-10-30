package com.todomanager.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Form input data for registration form with boundaries.
 * 
 * @author Ayhan Dardagan
 * 
 */
public class RegisterForm {

	/**
	 * User name min char size.
	 */
	public static final int USER_NAME_MIN = 2;

	/**
	 * User name max char size.
	 */
	public static final int USER_NAME_MAX = 30;

	/**
	 * User email min char size.
	 */
	public static final int EMAIL_MIN = 5;

	/**
	 * User email max char size.
	 */
	public static final int EMAIL_MAX = 50;

	/**
	 * User password min char size.
	 */
	public static final int PASSWORD_MIN = 4;

	/**
	 * User password max char size.
	 */
	public static final int PASSWORD_MAX = 14;

	/**
	 * User name.
	 */
	@NotNull
	@Size(min = USER_NAME_MIN, max = USER_NAME_MAX, message = "User name size "
			+ "should be in the range " + USER_NAME_MIN + " to "
			+ USER_NAME_MAX)
	private String userName;

	/**
	 * Email address.
	 */
	@NotNull
	@Size(min = EMAIL_MIN, max = EMAIL_MAX, message = "Email size should be in "
			+ "the range " + EMAIL_MIN + " to " + EMAIL_MAX)
	private String email;

	/**
	 * Password.
	 * 
	 * Bscrypt used to hash the password, it allows the user to enter a 56 byte
	 * password. Worst case at UTF-8 is 4 byte. We say 56 div 4 bytes
	 * simplified.
	 */
	@NotNull
	@Size(min = PASSWORD_MIN, max = PASSWORD_MAX, message = "Password size "
			+ "should be in the range " + PASSWORD_MIN + " to " + PASSWORD_MAX)
	private String password;

	/**
	 * Successful message flag.
	 */
	private boolean showSuccessfulMsg = false;

	/**
	 * Unsuccesful message flag.
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
	 * Ser user name.
	 * 
	 * @param username
	 *            User name
	 */
	public void setUserName(final String username) {
		this.userName = username;
	}

	/**
	 * Get email address.
	 * 
	 * @return Email address
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set email address.
	 * 
	 * @param email
	 *            Email address
	 */
	public void setEmail(final String email) {
		this.email = email;
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
	 * Get show successful message flag.
	 * 
	 * @return Successful message flag
	 */
	public boolean isShowSuccessfulMsg() {
		return showSuccessfulMsg;
	}

	/**
	 * Set show successful message flag.
	 * 
	 * @param showSuccessfulMsg
	 *            Successful message flag
	 */
	public void setShowSuccessfulMsg(final boolean showSuccessfulMsg) {
		this.showSuccessfulMsg = showSuccessfulMsg;
	}

	/**
	 * Get show unsuccessful message flag.
	 * 
	 * @return Unuccessful message flag
	 */
	public boolean isShowUnsuccessfulMsg() {
		return showUnsuccessfulMsg;
	}

	/**
	 * Set show unsuccessful message flag.
	 * 
	 * @param showUnsuccessfulMsg
	 *            Unsuccessful message flag
	 */
	public void setShowUnsuccessfulMsg(final boolean showUnsuccessfulMsg) {
		this.showUnsuccessfulMsg = showUnsuccessfulMsg;
	}
}