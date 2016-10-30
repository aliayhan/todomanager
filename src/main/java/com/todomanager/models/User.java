package com.todomanager.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Entity class "User" which represents an row entry of db table "users".
 * 
 * @author Ayhan Dardagan
 *
 */
@Entity
@Table(name = "users")
public class User {

	/**
	 * Primary key, user ID.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/**
	 * User name.
	 */
	@NotNull
	private String userName;

	/**
	 * Email address.
	 */
	@NotNull
	private String email;

	/**
	 * Hashed password.
	 */
	@NotNull
	private String pwhash;

	/**
	 * Present todo messages.
	 */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, //
			fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private List<TodoEntry> todoEntries;

	// Constructors

	/**
	 * Default constructor.
	 */
	public User() {
		this.todoEntries = new ArrayList<TodoEntry>();
	}

	/**
	 * Constructor.
	 * 
	 * @param userName
	 *            User name
	 * @param email
	 *            User name mail address
	 * @param pwhash
	 *            Users hashed password
	 */
	public User(final String userName, final String email,
			final String pwhash) {
		this.userName = userName;
		this.email = email;
		this.pwhash = pwhash;
		this.todoEntries = new ArrayList<TodoEntry>();
	}

	// Getters and setters

	/**
	 * Get user ID.
	 * 
	 * @return User ID
	 */
	public long getId() {
		return id;
	}

	/**
	 * Set user ID.
	 * 
	 * @param id
	 *            User ID
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * Set user ID.
	 * 
	 * @return User ID
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Set user name.
	 * 
	 * @param userName
	 *            User name
	 */
	public void setUserName(final String userName) {
		this.userName = userName;
	}

	/**
	 * Get email address.
	 * 
	 * @return Email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set email address.
	 * 
	 * @param email
	 *            Email address.
	 */
	public void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * Get hashed password.
	 * 
	 * @return Hashed password.
	 */
	public String getPwhash() {
		return pwhash;
	}

	/**
	 * Set hashed password.
	 * 
	 * @param pwhash
	 *            Hashed password
	 */
	public void setPwhash(final String pwhash) {
		this.pwhash = pwhash;
	}

	/**
	 * Get present todo messages.
	 * 
	 * @return Todo message list
	 */
	public List<TodoEntry> getTodoEntries() {
		return todoEntries;
	}
}
