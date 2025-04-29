package com.revature.services;

import com.revature.exceptions.custom.users.EmailAlreadyRegisteredException;
import com.revature.exceptions.custom.users.IdNotFoundException;
import com.revature.exceptions.custom.users.InvalidEmaiException;
import com.revature.exceptions.custom.users.InvalidPasswordException;
import com.revature.models.User;
import com.revature.repos.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
		private final UserDAO userDAO;

		@Autowired
		public UserService( UserDAO userDAO ) {
				this.userDAO = userDAO;
		}

		private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
		private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

	 // CREATE
	 public Optional<User> register(User newUser) {
				Optional<User> user = userDAO.findUserByEmail(newUser.getEmail());

				if(user.isPresent()) {
						throw new EmailAlreadyRegisteredException("There is an account with that email.");
				}

				if(!newUser.getEmail().matches(EMAIL_REGEX)) {
						throw new InvalidEmaiException("Your email is invalid. Your email needs to look like this: example@domain.com");
		  }

				if(!newUser.getPassword().matches(PASSWORD_REGEX)) {
						throw new InvalidPasswordException("Your Password is invalid. Must be at least 8 characters long and need to contain " +
														                                   "one uppercase letter (A-Z), one lowercase letter (a-z), " +
														                                   "one number (0-9), and one special character(@$!%*?&)");
				}

				String hashPassword = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());

				newUser.setPassword(hashPassword);

				return Optional.of(userDAO.save(newUser));
	}

	// Read
	public Optional<User>login(User userCredentials) {
				Optional<User> user = userDAO.findUserByEmail(userCredentials.getEmail());

				if(user.isEmpty()) {
						throw new InvalidEmaiException("There is no account with that email.");
				}

				User userToLogin = user.get();


				if(!BCrypt.checkpw(userCredentials.getPassword(), userToLogin.getPassword())) {
						throw new InvalidPasswordException("Wrong Password.");
				}

				return Optional.of(userToLogin);
		}

		// UPDATE
		public Optional<User> update(int userId, User updatedUser){
				Optional<User> userToUpdateOptional = userDAO.findById(userId);

				if(userToUpdateOptional.isEmpty()) {
						throw new IdNotFoundException("There is no account with the Id: " + userId);
				}

				User userToUpdate = userToUpdateOptional.get();

				if(updatedUser.getEmail() != null && !updatedUser.getEmail().matches(EMAIL_REGEX)) {
						throw new InvalidEmaiException("Your email is invalid. Your email needs to look like this: example@domain.com");
				}

				if(updatedUser.getPassword() != null && !updatedUser.getPassword().matches(PASSWORD_REGEX)) {
						throw new InvalidPasswordException("Your Password is invalid. Must be at least 8 characters long and need to contain " +
														                                   "one uppercase letter (A-Z), one lowercase letter (a-z), " +
														                                   "one number (0-9), and one special character(@$!%*?&)");
				}

				if(updatedUser.getEmail() != null) {
					userToUpdate.setEmail(updatedUser.getEmail());
				}

				if(updatedUser.getName() != null) {
					userToUpdate.setName(updatedUser.getName());
				}

				if(updatedUser.getPassword() != null) {
					String hashPassword = BCrypt.hashpw(updatedUser.getPassword(), BCrypt.gensalt());
					userToUpdate.setPassword(hashPassword);
				}

				return Optional.of(userDAO.save(userToUpdate));
		}

		// DELETE
		public void delete(int userId) {
				Optional<User> userToDeleteOptional = userDAO.findById(userId);

				if(userToDeleteOptional.isEmpty()) {
						throw new IdNotFoundException("There is no account with the Id: " + userId);
				}

				userDAO.deleteById(userId);
		}
}