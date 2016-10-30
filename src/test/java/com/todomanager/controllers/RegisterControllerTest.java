package com.todomanager.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.todomanager.forms.RegisterForm;
import com.todomanager.services.UserService;

/**
 * Testing {@link RegisterController}.
 * 
 * @author Ayhan Dardagan
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(RegisterController.class)
public class RegisterControllerTest {

	/**
	 * Spring MockMvc.
	 */
	@Autowired
	private MockMvc mvc;

	/**
	 * Provides user services like authentication.
	 */
	@MockBean
	private UserService userService;

	/**
	 * Request GET /register path test.
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void requestGetForRegisterPathTest() throws Exception {

		mvc.perform(get("/register")).andExpect(status().isOk())
				.andExpect(view().name("register"));
	}

	/**
	 * Request POST /register path test, succesful register.
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void requestPostForRegisterPathSuccessTest() throws Exception {
		final String testUserName = "Ayhan";
		final String testUserEmail = "a@a.com";
		final String testUserPassword = "1234";

		given(userService.registerUser(testUserName, testUserEmail,
				testUserPassword)).willReturn(true);

		RegisterForm registerForm = new RegisterForm();
		registerForm.setUserName(testUserName);
		registerForm.setEmail(testUserEmail);
		registerForm.setPassword(testUserPassword);
		registerForm.setShowSuccessfulMsg(false);
		mvc.perform(post("/register").flashAttr("registerForm", registerForm))
				.andExpect(status().isOk()).andExpect(view().name("register"))
				.andExpect(request().attribute("registerForm",
						hasProperty("showSuccessfulMsg", equalTo(true))));
	}

	/**
	 * Request POST /register path test, unsuccesful register.
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void requestPostForRegisterPathUnsuccessTest() throws Exception {
		final String testUserName = "Ayhan";
		final String testUserEmail = "a@a.com";
		final String testUserPassword = "1234";

		RegisterForm registerForm = new RegisterForm();
		registerForm.setUserName(testUserName);
		registerForm.setEmail(testUserEmail);
		registerForm.setPassword(testUserPassword);
		registerForm.setShowSuccessfulMsg(false);
		mvc.perform(post("/register").flashAttr("registerForm", registerForm))
				.andExpect(status().isOk()).andExpect(view().name("register"))
				.andExpect(request().attribute("registerForm",
						hasProperty("showUnsuccessfulMsg", equalTo(true))));
	}

	/**
	 * Request POST /register path test, invalid data test.
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void requestPostForRegisterPathInvalidDataTest() throws Exception {
		String testUserName = "Ayhan";
		String testUserEmail = "a@a.com";
		String testUserPassword = "1234";
		RegisterForm registerForm = new RegisterForm();
		given(userService.registerUser(testUserName, testUserEmail,
				testUserPassword)).willReturn(true);

		// Null
		registerForm.setUserName(null);
		registerForm.setEmail(null);
		registerForm.setPassword(null);
		mvc.perform(post("/register").flashAttr("registerForm", registerForm))
				.andExpect(status().isOk()).andExpect(view().name("register"))
				.andExpect(model().attributeHasFieldErrorCode("registerForm",
						"userName", "NotNull"))
				.andExpect(model().attributeHasFieldErrorCode("registerForm",
						"email", "NotNull"))
				.andExpect(model().attributeHasFieldErrorCode("registerForm",
						"password", "NotNull"));

		// Lower min boundary test
		testUserName = StringUtils.repeat("a", RegisterForm.USER_NAME_MIN - 1);
		testUserEmail = StringUtils.repeat("a", RegisterForm.EMAIL_MIN - 1);
		testUserPassword = StringUtils.repeat("a",
				RegisterForm.PASSWORD_MIN - 1);

		registerForm.setUserName(testUserName);
		registerForm.setEmail(testUserEmail);
		registerForm.setPassword(testUserPassword);
		registerForm.setShowSuccessfulMsg(false);
		registerForm.setShowUnsuccessfulMsg(false);
		reset(userService);
		given(userService.registerUser(testUserName, testUserEmail,
				testUserPassword)).willReturn(true);
		mvc.perform(post("/register").flashAttr("registerForm", registerForm))
				.andExpect(status().isOk()).andExpect(view().name("register"))
				.andExpect(model().attributeHasFieldErrorCode("registerForm",
						"userName", "Size"))
				.andExpect(model().attributeHasFieldErrorCode("registerForm",
						"email", "Size"))
				.andExpect(model().attributeHasFieldErrorCode("registerForm",
						"password", "Size"));

		// Higher max boundary test
		testUserName = StringUtils.repeat("a", RegisterForm.USER_NAME_MAX + 1);
		testUserEmail = StringUtils.repeat("a", RegisterForm.EMAIL_MAX + 1);
		testUserPassword = StringUtils.repeat("a",
				RegisterForm.PASSWORD_MAX + 1);

		registerForm.setUserName(testUserName);
		registerForm.setEmail(testUserEmail);
		registerForm.setPassword(testUserPassword);
		registerForm.setShowSuccessfulMsg(false);
		registerForm.setShowUnsuccessfulMsg(false);
		reset(userService);
		given(userService.registerUser(testUserName, testUserEmail,
				testUserPassword)).willReturn(true);
		mvc.perform(post("/register").flashAttr("registerForm", registerForm))
				.andExpect(status().isOk()).andExpect(view().name("register"))
				.andExpect(model().attributeHasFieldErrorCode("registerForm",
						"userName", "Size"))
				.andExpect(model().attributeHasFieldErrorCode("registerForm",
						"email", "Size"))
				.andExpect(model().attributeHasFieldErrorCode("registerForm",
						"password", "Size"));

		// At min boundary test
		testUserName = StringUtils.repeat("a", RegisterForm.USER_NAME_MIN);
		testUserEmail = StringUtils.repeat("a", RegisterForm.EMAIL_MIN);
		testUserPassword = StringUtils.repeat("a", RegisterForm.PASSWORD_MIN);

		registerForm.setUserName(testUserName);
		registerForm.setEmail(testUserEmail);
		registerForm.setPassword(testUserPassword);
		registerForm.setShowSuccessfulMsg(false);
		given(userService.registerUser(testUserName, testUserEmail,
				testUserPassword)).willReturn(true);
		mvc.perform(post("/register").flashAttr("registerForm", registerForm))
				.andExpect(status().isOk()).andExpect(view().name("register"))
				.andExpect(request().attribute("registerForm",
						hasProperty("showSuccessfulMsg", equalTo(true))));

		// At max boundary test
		testUserName = StringUtils.repeat("a", RegisterForm.USER_NAME_MAX);
		testUserEmail = StringUtils.repeat("a", RegisterForm.EMAIL_MAX);
		testUserPassword = StringUtils.repeat("a", RegisterForm.PASSWORD_MAX);

		registerForm.setUserName(testUserName);
		registerForm.setEmail(testUserEmail);
		registerForm.setPassword(testUserPassword);
		registerForm.setShowSuccessfulMsg(false);
		reset(userService);
		given(userService.registerUser(testUserName, testUserEmail,
				testUserPassword)).willReturn(true);
		mvc.perform(post("/register").flashAttr("registerForm", registerForm))
				.andExpect(status().isOk()).andExpect(view().name("register"))
				.andExpect(request().attribute("registerForm",
						hasProperty("showSuccessfulMsg", equalTo(true))));
	}
}