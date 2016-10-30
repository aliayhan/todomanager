package com.todomanager.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
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

import com.todomanager.forms.TodoManagerForm;
import com.todomanager.services.TodoManagerService;

/**
 * Testing {@link TodoManagerController}.
 * 
 * @author Ayhan Dardagan
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(TodoManagerController.class)
public class TodoManagerControllerTest {

	/**
	 * Spring MockMvc.
	 */
	@Autowired
	private MockMvc mvc;

	/**
	 * Provides todo mangement services like adding, listing and deleting todo
	 * texts.
	 */
	@MockBean
	private TodoManagerService todoManagerService;

	/**
	 * Request GET /todomanager path wihtout login test.
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void requestGetForTodomanagerPathWithoutLoginTest()
			throws Exception {

		mvc.perform(get("/todomanager")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/login"));
	}

	/**
	 * Request GET /todomanager path test.
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void requestGetForTodomanagerPathTest() throws Exception {

		mvc.perform(get("/todomanager").sessionAttr(LoginController.USER_NAME,
				"_TestUser1_")).andExpect(status().isOk())
				.andExpect(view().name("todomanager"));
	}

	/**
	 * Request GET /logout path test.
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void requestGetForLogoutPathTest() throws Exception {

		mvc.perform(get("/logout")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/login"));
	}

	/**
	 * Request POST /todomanager path test, successful adding of a todo message.
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void requestPostForTodomanagerPathAddTodoSuccessTest()
			throws Exception {

		final String testUserName = "_TestUser1_";
		final String testTodoMsg = "Message 1";

		given(todoManagerService.addNewTodo(testUserName, testTodoMsg))
				.willReturn(true);

		TodoManagerForm todoManagerForm = new TodoManagerForm();
		todoManagerForm.setUserName(testUserName);
		todoManagerForm.setNewTodo(testTodoMsg);
		mvc.perform(post("/todomanager")
				.sessionAttr(LoginController.USER_NAME, testUserName)
				.flashAttr("todoManagerForm", todoManagerForm))
				.andExpect(status().isOk())
				.andExpect(view().name("todomanager"))
				.andExpect(request().attribute("todoManagerForm",
						hasProperty("newTodo", equalTo(""))));
	}

	/**
	 * Request POST /todomanager path test, unsuccessful adding of a todo
	 * message.
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void requestPostForTodomanagerPathAddTodoUnsuccessTest()
			throws Exception {

		final String testUserName = "_TestUser1_";
		final String testTodoMsg = "Message 1";

		TodoManagerForm todoManagerForm = new TodoManagerForm();
		todoManagerForm.setUserName(testUserName);
		todoManagerForm.setNewTodo(testTodoMsg);
		mvc.perform(post("/todomanager")
				.sessionAttr(LoginController.USER_NAME, testUserName)
				.flashAttr("todoManagerForm", todoManagerForm))
				.andExpect(status().isOk())
				.andExpect(view().name("todomanager"))
				.andExpect(request().attribute("todoManagerForm",
						hasProperty("newTodo", equalTo(testTodoMsg))))
				.andExpect(request().attribute("todoManagerForm",
						hasProperty("showUnsuccessfulMsg", equalTo(true))));
	}

	/**
	 * Request GET /delete/item/{id} path test, successful deleting of a todo
	 * message.
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void requestPostForDeleteTodoPathSuccessTest() throws Exception {

		final String testUserName = "_TestUser1_";
		final long testTodoMsgId = 101;

		given(todoManagerService.deleteTodo(testUserName, testTodoMsgId))
				.willReturn(true);

		TodoManagerForm todoManagerForm = new TodoManagerForm();
		todoManagerForm.setUserName(testUserName);
		mvc.perform(get("/delete/item/" + testTodoMsgId)
				.sessionAttr(LoginController.USER_NAME, testUserName)
				.flashAttr("todoManagerForm", todoManagerForm))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/todomanager"));
	}

	/**
	 * Request GET /delete/item/{id} path test, unsuccessful deleting of a todo
	 * message.
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void requestPostForDeleteTodoPathUnsuccessTest() throws Exception {

		final String testUserName = "_TestUser1_";
		final long testTodoMsgId = 101;

		TodoManagerForm todoManagerForm = new TodoManagerForm();
		todoManagerForm.setUserName(testUserName);
		mvc.perform(get("/delete/item/" + testTodoMsgId)
				.sessionAttr(LoginController.USER_NAME, testUserName)
				.flashAttr("todoManagerForm", todoManagerForm))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/todomanager"));
	}

	/**
	 * Request POST /todomanager path test, adding todo with invalid data test.
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void requestPostForTodomanagerPathInvalidDataTest()
			throws Exception {
		final String testUserName = "_TestUser1_";
		String testTodoMsg;
		TodoManagerForm todoManagerForm = new TodoManagerForm();

		// Null value at todo message
		todoManagerForm.setUserName(testUserName);
		todoManagerForm.setNewTodo(null);
		mvc.perform(post("/todomanager")
				.sessionAttr(LoginController.USER_NAME, testUserName)
				.flashAttr("todoManagerForm", todoManagerForm))
				.andExpect(status().isOk())
				.andExpect(view().name("todomanager"))
				.andExpect(model().attributeHasFieldErrorCode("todoManagerForm",
						"newTodo", "NotNull"));

		// Lower min char size at todo message
		todoManagerForm.setUserName(testUserName);
		todoManagerForm.setNewTodo(
				StringUtils.repeat("a", TodoManagerForm.TODO_MIN - 1));
		mvc.perform(post("/todomanager")
				.sessionAttr(LoginController.USER_NAME, testUserName)
				.flashAttr("todoManagerForm", todoManagerForm))
				.andExpect(status().isOk())
				.andExpect(view().name("todomanager"))
				.andExpect(model().attributeHasFieldErrorCode("todoManagerForm",
						"newTodo", "Size"));

		// Higher max char size at todo message
		todoManagerForm.setUserName(testUserName);
		todoManagerForm.setNewTodo(
				StringUtils.repeat("a", TodoManagerForm.TODO_MAX + 1));
		mvc.perform(post("/todomanager")
				.sessionAttr(LoginController.USER_NAME, testUserName)
				.flashAttr("todoManagerForm", todoManagerForm))
				.andExpect(status().isOk())
				.andExpect(view().name("todomanager"))
				.andExpect(model().attributeHasFieldErrorCode("todoManagerForm",
						"newTodo", "Size"));

		// At min char size at todo message
		testTodoMsg = StringUtils.repeat("a", TodoManagerForm.TODO_MIN);
		todoManagerForm.setUserName(testUserName);
		todoManagerForm.setNewTodo(testTodoMsg);
		given(todoManagerService.addNewTodo(testUserName, testTodoMsg))
				.willReturn(true);
		mvc.perform(post("/todomanager")
				.sessionAttr(LoginController.USER_NAME, testUserName)
				.flashAttr("todoManagerForm", todoManagerForm))
				.andExpect(status().isOk())
				.andExpect(view().name("todomanager"))
				.andExpect(request().attribute("todoManagerForm",
						hasProperty("newTodo", equalTo(""))));

		// At max char size at todo message
		testTodoMsg = StringUtils.repeat("a", TodoManagerForm.TODO_MAX);
		todoManagerForm.setUserName(testUserName);
		todoManagerForm.setNewTodo(testTodoMsg);
		reset(todoManagerService);
		given(todoManagerService.addNewTodo(testUserName, testTodoMsg))
				.willReturn(true);
		mvc.perform(post("/todomanager")
				.sessionAttr(LoginController.USER_NAME, testUserName)
				.flashAttr("todoManagerForm", todoManagerForm))
				.andExpect(status().isOk())
				.andExpect(view().name("todomanager"))
				.andExpect(request().attribute("todoManagerForm",
						hasProperty("newTodo", equalTo(""))));
	}
}
