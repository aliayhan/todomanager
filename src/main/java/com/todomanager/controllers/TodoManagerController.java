package com.todomanager.controllers;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.todomanager.forms.TodoManagerForm;
import com.todomanager.models.TodoEntry;
import com.todomanager.services.TodoManagerService;

/**
 * Controller providing request handlers for adding/deleting todos and logout.
 * 
 * @author Ayhan Dardagan
 *
 */
@Controller
public class TodoManagerController {

	/**
	 * Default logger.
	 */
	private static final Logger LOG = getLogger(TodoManagerController.class);

	/**
	 * Provides todo mangement services like adding, listing and deleting todo
	 * texts.
	 */
	@Autowired
	private TodoManagerService todoManagerService;

	// Request mappers

	/**
	 * Request leads to manager page.
	 * 
	 * @param session
	 *            HTTP session to get the user name from
	 * @param todoManagerForm
	 *            Todo manager form data
	 * @return Todo manager page
	 */
	@RequestMapping(value = "/todomanager", method = RequestMethod.GET)
	public String todomanager(final HttpSession session,
			final TodoManagerForm todoManagerForm) {

		// If not logged in redirect to login else get user name.
		// TODO: When you use SpringSecurity this is not needed.
		if (session.getAttribute(LoginController.USER_NAME) == null) {
			return "redirect:/login";
		}
		todoManagerForm.setUserName(
				(String) session.getAttribute(LoginController.USER_NAME));
		// Get all messages of user and save into todo manager form
		List<TodoEntry> userTodoMessages = todoManagerService
				.getAllTodos(todoManagerForm.getUserName());
		if (userTodoMessages == null) {
			LOG.debug("Getting messages for user failed!");
			todoManagerForm.setShowUnsuccessfulMsg(true);
			return "todomanager";
		}
		todoManagerForm.setTodoEntries(userTodoMessages);

		return "todomanager";
	}

	/**
	 * Request leads to manager page.
	 * 
	 * @param session
	 *            HTTP session to get the user name from
	 * @param todoManagerForm
	 *            Todo manager form data
	 * @param bindingResult
	 *            Binding result of login data form
	 * @return Todo manager page
	 */
	@RequestMapping(value = "/todomanager", method = RequestMethod.POST)
	public String addNewTodo(final HttpSession session,
			@Valid final TodoManagerForm todoManagerForm,
			final BindingResult bindingResult) {

		// Get user name
		// TODO: Same as above todo. When you use SpringSecurity this is not
		// needed.
		if (session.getAttribute(LoginController.USER_NAME) == null) {
			return "redirect:/login";
		}
		todoManagerForm.setUserName(
				(String) session.getAttribute(LoginController.USER_NAME));
		// Get all messages of user and save into todo manager form
		List<TodoEntry> userTodoMessages = todoManagerService
				.getAllTodos(todoManagerForm.getUserName());
		if (userTodoMessages == null) {
			LOG.debug("Getting messages for user failed!");
			todoManagerForm.setShowUnsuccessfulMsg(true);
			return "todomanager";
		}
		todoManagerForm.setTodoEntries(userTodoMessages);

		// Check for any input errors
		LOG.debug("Login user...");
		if (bindingResult.hasErrors()) {
			LOG.debug("Todo message has errors.");
			return "todomanager";
		}

		// Add new todo message and empty form
		if (!todoManagerService.addNewTodo(todoManagerForm.getUserName(),
				todoManagerForm.getNewTodo())) {
			LOG.debug("Adding new todo failed!");
			todoManagerForm.setShowUnsuccessfulMsg(true);
			return "todomanager";
		}

		todoManagerForm.setNewTodo("");
		return "todomanager";
	}

	/**
	 * Delete specific todo text.
	 * 
	 * @param session
	 *            HTTP session to get the user name from
	 * @param id
	 *            Todo text id
	 * @param todoManagerForm
	 *            Todo manager form data
	 * @return todomanager
	 */
	@RequestMapping(value = "/delete/item/{id}", method = RequestMethod.GET)
	public String deleteUser(final HttpSession session,
			@PathVariable final Long id,
			final TodoManagerForm todoManagerForm) {

		// Get user name
		// TODO: Same as above todo. When you use SpringSecurity this is not
		// needed.
		if (session.getAttribute(LoginController.USER_NAME) == null) {
			return "redirect:/login";
		}
		todoManagerForm.setUserName(
				(String) session.getAttribute(LoginController.USER_NAME));
		// Add new todo message and empty form
		if (!todoManagerService.deleteTodo(todoManagerForm.getUserName(), id)) {
			LOG.debug("Deleting specific todo failed!");
			todoManagerForm.setShowUnsuccessfulMsg(true);
			return "redirect:/todomanager";
		}
		// Get all messages of user and save into todo manager form
		List<TodoEntry> userTodoMessages = todoManagerService
				.getAllTodos(todoManagerForm.getUserName());
		if (userTodoMessages == null) {
			LOG.debug("Getting messages for user failed!");
			todoManagerForm.setShowUnsuccessfulMsg(true);
			return "redirect:/todomanager";
		}
		todoManagerForm.setTodoEntries(userTodoMessages);

		return "redirect:/todomanager";
	}

	/**
	 * Logout and return to login.
	 * 
	 * @param session
	 *            Current HTTP session to invalidate
	 * @return Login page
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(final HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

}
