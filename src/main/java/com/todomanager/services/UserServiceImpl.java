package com.todomanager.services;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todomanager.models.User;
import com.todomanager.models.UserRepository;

/**
 * Service interface implementation for user operations like authentication and
 * registration.
 * 
 * @author Ayhan Dardagan
 *
 */
@Service
public class UserServiceImpl implements UserService {

	/**
	 * The log2 of the number of rounds of hashing to apply, see
	 * {@link BCrypt#gensalt(int)}.
	 */
	private static final int SALT_LOG_ROUNDS = 12;

	/**
	 * Default logger.
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(UserServiceImpl.class);

	/**
	 * CRUD operations on user.
	 */
	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean authenticate(final String userName, final String password) {

		if (isBlank(userName) || isBlank(password)) {
			LOG.debug("Registering aborted, one or more parameters are blank.");
			return false;
		}

		try {
			List<User> users = userRepository.findByUserName(userName);
			if (users.size() == 0) {
				LOG.debug("No user found with that user name.");
				return false;
			} else if (users.size() > 1) {
				LOG.error("More than 1 user found - shouldnt be possible "
						+ "because user name is unique!");
				return false;
			}

			// Check hashed password with entered password
			// TODO: When you use SpringSecurity use BCryptPasswordEncoder
			// instead.
			if (!BCrypt.checkpw(password, users.get(0).getPwhash())) {
				LOG.debug("Password doesnt match.");
				return false;
			}

			return true;
		} catch (Exception e) {
			LOG.error("Error when authenticating user!", e);
			return false;
		}
	}

	@Override
	public boolean registerUser(final String userName, final String email,
			final String password) {

		if (isBlank(userName) || isBlank(email) || isBlank(password)) {
			LOG.debug("Registering aborted, one or more parameters are blank.");
			return false;
		}

		try {
			// TODO: Same as above todo. When you use SpringSecurity use
			// BCryptPasswordEncoder instead.
			userRepository.save(new User(userName, email,
					BCrypt.hashpw(password, BCrypt.gensalt(SALT_LOG_ROUNDS))));
			return true;
		} catch (Exception e) {
			LOG.error("Error when saving user!", e);
			return false;
		}
	}
}
