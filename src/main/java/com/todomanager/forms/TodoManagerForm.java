package com.todomanager.forms;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.todomanager.models.TodoEntry;

/**
 * Form input data for todo management.
 * 
 * @author Ayhan Dardagan
 *
 */
public class TodoManagerForm {

	/**
	 * Todo message char min size.
	 */
	public static final int TODO_MIN = 1;

	/**
	 * Todo message char max size.
	 */
	public static final int TODO_MAX = 100;

	/**
	 * User name.
	 */
	private String userName;

	/**
	 * New todo text message.
	 */
	@NotNull
	@Size(min = TODO_MIN, max = TODO_MAX, message = "Todo message size should "
			+ "be in the range " + TODO_MIN + " to " + TODO_MAX)
	private String newTodo;

	/**
	 * List of present todo messages.
	 */
	private List<TodoEntry> todoEntries;

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
	 * @param userName
	 *            User name
	 */
	public void setUserName(final String userName) {
		this.userName = userName;
	}

	/**
	 * Get new todo message.
	 * 
	 * @return Todo message
	 */
	public String getNewTodo() {
		return newTodo;
	}

	/**
	 * Set new todo message.
	 * 
	 * @param newTodo
	 *            Todo message
	 */
	public void setNewTodo(final String newTodo) {
		this.newTodo = newTodo;
	}

	/**
	 * Get present todo messages.
	 * 
	 * @return Todo message list
	 */
	public List<TodoEntry> getTodoEntries() {
		return todoEntries;
	}

	/**
	 * Set present todo messages.
	 * 
	 * @param todoEntries
	 *            Todo message list
	 */
	public void setTodoEntries(final List<TodoEntry> todoEntries) {
		this.todoEntries = todoEntries;
	}

	/**
	 * Get show unsuccessful message flag.
	 * 
	 * @return Unsuccessful message flag
	 */
	public boolean isShowUnsuccessfulMsg() {
		return showUnsuccessfulMsg;
	}

	/**
	 * Set unsuccessful message flag.
	 * 
	 * @param showUnsuccessfulMsg
	 *            Unsuccessful message flag
	 */
	public void setShowUnsuccessfulMsg(final boolean showUnsuccessfulMsg) {
		this.showUnsuccessfulMsg = showUnsuccessfulMsg;
	}
}
