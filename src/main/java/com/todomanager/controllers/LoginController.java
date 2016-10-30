package com.todomanager.controllers;

import static org.slf4j.LoggerFactory.getLogger;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.todomanager.forms.LoginForm;
import com.todomanager.services.UserService;

/**
 * Controller providing request handlers for navigation and user login.
 * 
 * @author Ayhan Dardagan
 *
 */
@Controller
public class LoginController {

	/**
	 * Used key to store user name information in session.
	 */
	public static final String USER_NAME = "USER_NAME";

	/**
	 * Default logger.
	 */
	private static final Logger LOG = getLogger(LoginController.class);

	/**
	 * Provides user services like authentication.
	 */
	@Autowired
	private UserService userService;

	// Request mappers

	/**
	 * Request leads to login page.
	 * 
	 * @param loginForm
	 *            Login data form
	 * @return Login page
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String root(final LoginForm loginForm) {
		return "redirect:/login";
	}

	/**
	 * Request leads to login page.
	 * 
	 * @param loginForm
	 *            Login data form
	 * @return Login page
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(final LoginForm loginForm) {
		return "login";
	}

	/**
	 * Login user.
	 * 
	 * @param session
	 *            Current HTTP session to store user name in
	 * @param loginForm
	 *            Login data form
	 * @param bindingResult
	 *            Binding result of login data form
	 * @return Todo manager page on success
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String registerUser(final HttpSession session,
			@Valid final LoginForm loginForm,
			final BindingResult bindingResult) {

		// Check for any input errors
		LOG.debug("Login user...");
		if (bindingResult.hasErrors()) {
			LOG.debug("User input has errors.");
			return "login";
		}

		// Show unsuccess message and empty forms
		if (!userService.authenticate(loginForm.getUserName(),
				loginForm.getPassword())) {
			LOG.debug("User registration failed!");
			loginForm.setShowUnsuccessfulMsg(true);
			initForm(loginForm);
			return "login";
		}

		// Store user name in session
		session.setAttribute(USER_NAME, loginForm.getUserName());

		return "redirect:/todomanager";
	}

	/**
	 * Initialize login form. Empty value is saved.
	 * 
	 * @param loginForm
	 *            Login data form
	 */
	private void initForm(final LoginForm loginForm) {
		loginForm.setUserName("");
		loginForm.setPassword("");
	}
}
