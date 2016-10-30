package com.todomanager.controllers;

import static org.slf4j.LoggerFactory.getLogger;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.todomanager.forms.RegisterForm;
import com.todomanager.services.UserService;

/**
 * Controller providing request handlers for navigation and user registration.
 * 
 * @author Ayhan Dardagan
 *
 */
@Controller
public class RegisterController {

	/**
	 * Default logger.
	 */
	private static final Logger LOG = getLogger(RegisterController.class);

	/**
	 * Provides user services like authentication.
	 */
	@Autowired
	private UserService userService;

	// Request mappers

	/**
	 * Request leads to registration page.
	 * 
	 * @param registerForm
	 *            Register data form
	 * @return Registration page
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String navigateToRegister(final RegisterForm registerForm) {
		return "register";
	}

	/**
	 * Request registers user.
	 * 
	 * @param registerForm
	 *            Register data form
	 * @param bindingResult
	 *            Binding result of register data form
	 * @return Register page
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUser(@Valid final RegisterForm registerForm,
			final BindingResult bindingResult) {

		// Check for any input errors
		LOG.debug("Registering user...");
		if (bindingResult.hasErrors()) {
			LOG.debug("User input has errors.");
			return "register";
		}

		// Show unsuccess message and empty forms
		if (!userService.registerUser(registerForm.getUserName(),
				registerForm.getEmail(), registerForm.getPassword())) {
			LOG.debug("User registration failed!");
			registerForm.setShowUnsuccessfulMsg(true);
			initForm(registerForm);
			return "register";
		}

		// Show success message and empty forms
		registerForm.setShowSuccessfulMsg(true);
		initForm(registerForm);
		return "register";
	}

	/**
	 * Initialize login form. Empty value is saved.
	 * 
	 * @param registerForm
	 *            Register data form
	 */
	private void initForm(final RegisterForm registerForm) {
		registerForm.setUserName("");
		registerForm.setEmail("");
		registerForm.setPassword("");
	}
}
