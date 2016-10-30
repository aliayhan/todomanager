package com.todomanager.services;

import java.util.List;

import com.todomanager.models.TodoEntry;

/**
 * Service interface for todo management operations.
 * 
 * @author Ayhan Dardagan
 *
 */
public interface TodoManagerService {

	/**
	 * Adding new todo to user.
	 * 
	 * @param userName
	 *            User name
	 * @param todoText
	 *            New todo message to add
	 * @return Success or not
	 */
	boolean addNewTodo(String userName, String todoText);

	/**
	 * Get all messages for a user.
	 * 
	 * @param userName
	 *            User name
	 * @return All todo messages of user
	 */
	List<TodoEntry> getAllTodos(String userName);

	/**
	 * Deleting a selected todo message.
	 * 
	 * @param userName
	 *            User name
	 * @param todoId
	 *            Internal primary key id of todo to delete
	 * @return Success or not
	 */
	boolean deleteTodo(String userName, long todoId);
}
