package com.todomanager.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
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

import com.todomanager.models.User;
import com.todomanager.models.UserRepository;

/**
 * Testing {@link UserServiceImpl}.
 * 
 * @author Ayhan Dardagan
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

	/**
	 * CRUD interface for user entity.
	 */
	@MockBean
	private UserRepository userRepository;

	/**
	 * Service interface providing user authentication and registering.
	 */
	@Autowired
	private UserService userService;

	/**
	 * Test user registration and authentication altogether.
	 */
	@Test
	public void userRegistrationAuthenticationTest() {
		final String testUserName = "_TestUser1_";
		final String testUserPW = "1234";

		// Prepare user capturing. Important because the user password is hashed
		// while registration and authentication compares plain with hashed pw.
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

		// Call registerUser
		assertThat(
				userService.registerUser(testUserName, "a@a.com", testUserPW))
						.isTrue();

		verify(userRepository).save(userCaptor.capture());

		// Prepare call on findByUserName
		List<User> users = new ArrayList<User>();
		users.add(userCaptor.getAllValues().get(0));
		given(userRepository.findByUserName(testUserName)).willReturn(users);

		// Call authenticate
		assertThat(userService.authenticate(testUserName, testUserPW)).isTrue();
	}

	/**
	 * Calling registration with invalid values.
	 */
	@Test
	public void userAuthenticationWithInvalidValuesTest() {

		assertThat(userService.authenticate(null, null)).isFalse();
		assertThat(userService.authenticate("_TestUser1_", null)).isFalse();
		assertThat(userService.authenticate(null, "12345")).isFalse();
	}

	/**
	 * Calling registration with invalid values.
	 */
	@Test
	public void userRegistrationWithInvalidValuesTest() {

		assertThat(userService.registerUser(null, null, null)).isFalse();
		assertThat(userService.registerUser("_TestUser1_", null, null))
				.isFalse();
		assertThat(userService.registerUser(null, "a@a.com", null)).isFalse();
		assertThat(userService.registerUser(null, null, "12345")).isFalse();
	}
}