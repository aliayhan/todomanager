package com.todomanager.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.todomanager.forms.LoginForm;
import com.todomanager.services.UserService;

/**
 * Testing {@link LoginController}.
 * 
 * @author Ayhan Dardagan
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(LoginController.class)
public class LoginControllerTest {

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
	 * Request GET / path test.
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void requestGetForRootPathTest() throws Exception {

		mvc.perform(get("/")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/login"));
	}

	/**
	 * Request GET /login path test.
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void requestGetForLoginPathTest() throws Exception {

		mvc.perform(get("/login")).andExpect(status().isOk())
				.andExpect(view().name("login"));
	}

	/**
	 * Request POST /login path test, succesful login.
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void requestPostForLoginPathSuccessTest() throws Exception {
		final String testUserName = "Ayhan";
		final String testUserPassword = "1234";

		given(userService.authenticate(testUserName, testUserPassword))
				.willReturn(true);

		LoginForm loginForm = new LoginForm();
		loginForm.setUserName(testUserName);
		loginForm.setPassword(testUserPassword);
		mvc.perform(post("/login").flashAttr("loginForm", loginForm))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/todomanager"));
	}

	/**
	 * Request POST /login path test, unsuccesful login.
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void requestPostForLoginPathUnsuccessTest() throws Exception {
		final String testUserName = "Ayhan";
		final String testUserPassword = "1234";

		LoginForm loginForm = new LoginForm();
		loginForm.setUserName(testUserName);
		loginForm.setPassword(testUserPassword);
		mvc.perform(post("/login").flashAttr("loginForm", loginForm))
				.andExpect(status().isOk()).andExpect(view().name("login"));
	}

	/**
	 * Request POST /login path test, invalid data test.
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void requestPostForLoginPathInvalidDataTest() throws Exception {

		LoginForm loginForm = new LoginForm();
		loginForm.setUserName(null);
		loginForm.setPassword(null);
		mvc.perform(post("/login").flashAttr("loginForm", loginForm))
				.andExpect(status().isOk()).andExpect(view().name("login"))
				.andExpect(model().attributeHasFieldErrorCode("loginForm",
						"userName", "NotNull"))
				.andExpect(model().attributeHasFieldErrorCode("loginForm",
						"password", "NotNull"));

	}
}
