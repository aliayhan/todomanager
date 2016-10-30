package com.todomanager.services;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todomanager.models.TodoEntry;
import com.todomanager.models.User;
import com.todomanager.models.UserRepository;

/**
 * Service interface implementation for todo management operations.
 * 
 * @author Ayhan Dardagan
 *
 */
@Service
public class TodoManagerServiceImpl implements TodoManagerService {

	/**
	 * Default logger.
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(TodoManagerServiceImpl.class);

	/**
	 * CRUD operations on user and user todo messages.
	 */
	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean addNewTodo(final String userName, final String todoText) {

		if (StringUtils.isBlank(userName) || StringUtils.isBlank(todoText)) {
			LOG.debug("User name or todo message to add is empty.");
			return false;
		}

		try {
			// Find user and add new todo message
			List<User> users = userRepository.findByUserName(userName);
			if (users.size() == 0) {
				LOG.debug("No user found to add a new todo message to.");
				return false;
			} else if (users.size() > 1) {
				LOG.error("Too many users found to add a new todo message to "
						+ "- shouldnt happen because user name is unique.");
				return false;
			}

			users.get(0).getTodoEntries().add(new TodoEntry(todoText));
			userRepository.save(users.get(0));

			return true;
		} catch (Exception e) {
			LOG.error("Error when adding todo message!", e);
			return false;
		}
	}

	@Override
	public List<TodoEntry> getAllTodos(final String userName) {

		if (StringUtils.isBlank(userName)) {
			LOG.debug("User name is empty.");
			return null;
		}

		try {
			// Find user and get todo entries
			List<User> users = userRepository.findByUserName(userName);
			if (users.size() == 0) {
				LOG.debug("No user found to add a new todo message to.");
				return null;
			} else if (users.size() > 1) {
				LOG.error("Too many users found to add a new todo message to "
						+ "- should never happen because user name is unique.");
				return null;
			}

			return users.get(0).getTodoEntries();
		} catch (Exception e) {
			LOG.error("Error when getting user todo messages!", e);
			return null;
		}
	}

	@Override
	public boolean deleteTodo(final String userName, final long todoId) {

		if (StringUtils.isBlank(userName) || (todoId < 0)) {
			LOG.debug("User name is empty or todoId < 0.");
			return false;
		}

		try {
			// Find user and get todo entries
			List<User> users = userRepository.findByUserName(userName);
			if (users.size() == 0) {
				LOG.debug("No user found to add a new todo message to.");
				return false;
			} else if (users.size() > 1) {
				LOG.error("Too many users found to add a new todo message to "
						+ "- should never happen because user name is unique.");
				return false;
			}

			List<TodoEntry> todoEntries = users.get(0).getTodoEntries();
			if (todoEntries.size() == 0) {
				LOG.debug("No todo messages to delete.");
				return false;
			}

			// Find message to delete
			int idToDelete = -1;
			for (int i = 0; i < todoEntries.size(); i++) {
				if (todoEntries.get(i).getId() == todoId) {
					idToDelete = i;
					break;
				}
			}
			if (idToDelete == -1) {
				LOG.error("Message to delete not found.");
				return false;
			}

			// Delete message
			todoEntries.remove(idToDelete);
			userRepository.save(users.get(0));

			return true;
		} catch (Exception e) {
			LOG.error("Error when deleting todo message!", e);
			return false;
		}
	}
}
