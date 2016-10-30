package com.todomanager.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.todomanager.models.TodoEntry;
import com.todomanager.models.User;
import com.todomanager.models.UserRepository;

/**
 * Testing {@link TodoManagerServiceImpl}.
 * 
 * @author Ayhan Dardagan
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TodoManagerServiceImplTest {

	/**
	 * CRUD interface for user entity.
	 */
	@MockBean
	private UserRepository userRepository;

	/**
	 * Service interface providing todo management operations.
	 */
	@Autowired
	private TodoManagerService todoManagerService;

	/**
	 * Test adding. getting and deleting messages altogether.
	 */
	@Test
	public void addGetDeleteTodoMessagesTest() {
		final String testUserName = "_TestUser1_";
		final String testUserTodoMsg = "Message 101";
		final long testUserTodoMsgID = 101;
		User testUser = new User(testUserName, "a@a.com", "1234");

		// Prepare user capturing with todo message while calling addMessage
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

		// Prepare call on findByUserName by addNewTodo
		List<User> userListByAdd = new ArrayList<User>();
		userListByAdd.add(testUser);
		given(userRepository.findByUserName(testUserName))
				.willReturn(userListByAdd);

		// Call addNewTodo
		assertThat(todoManagerService.addNewTodo(testUserName, testUserTodoMsg))
				.isTrue();

		verify(userRepository).save(userCaptor.capture());

		// Prepare call on findByUserName by getAllTodos and deleteTodo
		reset(userRepository);
		List<User> userListByGetAll = new ArrayList<User>();
		// Add captured user with todo message added to it
		User userByGetAll = userCaptor.getAllValues().get(0);
		// Provide message id
		userByGetAll.getTodoEntries().get(0).setId(testUserTodoMsgID);
		userListByGetAll.add(userByGetAll);
		given(userRepository.findByUserName(testUserName))
				.willReturn(userListByGetAll);

		// Call getAllMessages
		List<TodoEntry> msgs = todoManagerService.getAllTodos(testUserName);

		assertThat(msgs).hasSize(1).extracting("todoText")
				.contains(testUserTodoMsg);

		// Call deleteTodo
		assertThat(
				todoManagerService.deleteTodo(testUserName, testUserTodoMsgID))
						.isTrue();
	}

	/**
	 * Calling add with invalid values.
	 */
	@Test
	public void addNewTodoWithInvalidValuesTest() {

		assertThat(todoManagerService.addNewTodo("_TestUser1_", null))
				.isFalse();
		assertThat(todoManagerService.addNewTodo(null, "Message 1")).isFalse();
		assertThat(todoManagerService.addNewTodo(null, null)).isFalse();
	}

	/**
	 * Calling get all messages with invalid values.
	 */
	@Test
	public void getAllMessagesWithInvalidValuesTest() {

		assertThat(todoManagerService.getAllTodos(null)).isNull();
	}

	/**
	 * Calling delete with invalid values.
	 */
	@Test
	public void deleteTodoWithInvalidValuesTest() {

		assertThat(todoManagerService.deleteTodo("_TestUser1_", -1)).isFalse();
		assertThat(todoManagerService.deleteTodo(null, 1)).isFalse();
		assertThat(todoManagerService.deleteTodo(null, -1)).isFalse();
	}
}
