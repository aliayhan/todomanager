package com.todomanager.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Testing {@link UserRepository} CRUD interfaces on all methods which are used
 * in the application against DB. So no mocking! -> Testing this way also DB
 * schema created by separate SQL, see 01-CreateDBAndTables.sql.
 * 
 * AutoConfigureTestDatabase(replace=Replace.NONE) -> Conntect to DB. Although,
 * data JPA tests are transactional and rollback at the end of each test by
 * default.
 * 
 * @author Ayhan Dardagan
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {

	/**
	 * User name max char size.
	 */
	private static final int USER_NAME_MAX = 30;

	/**
	 * User email max char size.
	 */
	private static final int EMAIL_MAX = 50;

	/**
	 * User password max char size.
	 */
	private static final int PASSWORD_MAX_BYTES = 60;

	/**
	 * CRUD interface for user entity.
	 */
	@Autowired
	private UserRepository userRepository;

	/**
	 * Junit exception expecter.
	 */
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	// Standard cases

	/**
	 * Adding and deleting user.
	 */
	@Test
	public void addingDeletingTest() {

		// Simplifying, no password hashing
		User testUser = new User("_TestUser1_", "a@a.com", "1234");
		userRepository.save(testUser);
		userRepository.delete(testUser);
	}

	/**
	 * Reading an user by user name.
	 */
	@Test
	public void findByUserNameTest() {

		User testUser = new User("_TestUser1_", "a@a.com", "1234");
		userRepository.save(testUser);

		List<User> users = userRepository
				.findByUserName(testUser.getUserName());

		// Exactly one user must be found, that user must have same values
		assertThat(users).hasSize(1).extracting("userName", "email", "pwhash")
				.contains(tuple(testUser.getUserName(), testUser.getEmail(),
						testUser.getPwhash()));
	}

	/**
	 * Adding, finding and deleting an user with messages.
	 */
	@Test
	public void addingFindingDeletingUserWithMessagesTest() {

		User testUser = new User("_TestUser1_", "a@a.com", "1234");
		TodoEntry entry1 = new TodoEntry("Message 1");
		TodoEntry entry2 = new TodoEntry("Message 2");
		testUser.getTodoEntries().add(entry1);
		testUser.getTodoEntries().add(entry2);

		userRepository.save(testUser);

		List<User> users = userRepository
				.findByUserName(testUser.getUserName());

		// Exactly one user must be found with the two test messages
		assertThat(users).hasSize(1).flatExtracting("todoEntries").hasSize(2)
				.extracting("todoText")
				.contains(entry1.getTodoText(), entry2.getTodoText());

		// Testing on delete cascade
		userRepository.delete(testUser);
	}

	// Null and boundary cases

	/**
	 * Adding null user test.
	 * 
	 * @throws ConstraintViolationException
	 *             Thrown i.e. on not null constraint error
	 */
	@Test
	public void addingUserWithNullValuesTest()
			throws ConstraintViolationException {

		// Expected exception and messages
		thrown.expect(ConstraintViolationException.class);
		thrown.expectMessage("'may not be null', propertyPath=userName");
		thrown.expectMessage("'may not be null', propertyPath=email");
		thrown.expectMessage("'may not be null', propertyPath=pwhash");

		User testUser1 = new User();
		userRepository.save(testUser1);
	}

	/**
	 * Add a user with maximum char sizes in all fields.
	 */
	@Test
	public void addingUserWithMaxBoundryValuesTest() {
		User testUser = new User(StringUtils.repeat("a", USER_NAME_MAX),
				StringUtils.repeat("a", EMAIL_MAX),
				StringUtils.repeat("a", PASSWORD_MAX_BYTES)); // 'a'@UTF-8 = 1B
		userRepository.save(testUser);
	}

	/**
	 * Add a user with over max user name size.
	 */
	@Test
	public void addingUserWithOverMaxUserNameSizeTest() {

		// Expected exception and messages
		thrown.expect(DataIntegrityViolationException.class);
		thrown.expectMessage("DataException: could not execute statement");

		User testUser = new User(StringUtils.repeat("a", USER_NAME_MAX + 1),
				StringUtils.repeat("a", EMAIL_MAX),
				StringUtils.repeat("a", PASSWORD_MAX_BYTES));
		userRepository.save(testUser);
	}

	/**
	 * Add a user with over max email size.
	 */
	@Test
	public void addingUserWithOverMaxEmailSizeTest() {

		// Expected exception and messages
		thrown.expect(DataIntegrityViolationException.class);
		thrown.expectMessage("DataException: could not execute statement");

		User testUser = new User(StringUtils.repeat("a", USER_NAME_MAX),
				StringUtils.repeat("a", EMAIL_MAX + 1),
				StringUtils.repeat("a", PASSWORD_MAX_BYTES));
		userRepository.save(testUser);
	}

	/**
	 * Add a user with over max password size.
	 */
	@Test
	public void addingUserWithOverMaxPasswordSizeTest() {

		// Expected exception and messages
		thrown.expect(DataIntegrityViolationException.class);
		thrown.expectMessage("DataException: could not execute statement");

		User testUser = new User(StringUtils.repeat("a", USER_NAME_MAX),
				StringUtils.repeat("a", EMAIL_MAX),
				StringUtils.repeat("a", PASSWORD_MAX_BYTES + 1));
		userRepository.save(testUser);
	}

	// Unique field cases

	/**
	 * Adding two different user with same user name.
	 * 
	 * @throws DataIntegrityViolationException
	 *             Thrown i.e. on unique key constraint error
	 */
	@Test
	public void addingUserWithNameAlreadyInDbTest()
			throws DataIntegrityViolationException {

		// Expected exception and message
		thrown.expect(DataIntegrityViolationException.class);
		thrown.expectMessage("constraint [user_name_uk]");

		User testUser1 = new User("_TestUser1_", "a@a.com", "1234");
		User testUser2 = new User("_TestUser1_", "b@b.com", "5678");

		userRepository.save(testUser1);
		userRepository.save(testUser2);
	}

	/**
	 * Adding two different user with same email.
	 * 
	 * @throws DataIntegrityViolationException
	 *             Thrown i.e. on unique key constraint error
	 */
	@Test
	public void addingUserWithEmailAlreadyInDbTest()
			throws DataIntegrityViolationException {

		// Expected exception and message
		thrown.expect(DataIntegrityViolationException.class);
		thrown.expectMessage("constraint [email_uk]");

		User testUser1 = new User("_TestUser1_", "a@a.com", "1234");
		User testUser2 = new User("_TestUser2_", "a@a.com", "5678");
		userRepository.save(testUser1);
		userRepository.save(testUser2);
	}
}