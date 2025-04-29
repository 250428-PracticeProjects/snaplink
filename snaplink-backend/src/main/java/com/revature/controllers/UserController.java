package com.revature.controllers;

import com.revature.exceptions.custom.users.UnauthenticatedException;
import com.revature.models.User;
import com.revature.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("accounts")
public class UserController  {
	private final UserService userService;
	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("register")
	public ResponseEntity<User> registerHandler( @RequestBody User user, HttpSession session ) {
		Optional<User> userToBeRegistered = userService.register(user);

		userToBeRegistered.ifPresent(value -> logger.info("A new user was created with the id : {}", value.getUserId()));

		session.setAttribute("userId", userToBeRegistered.get().getUserId());

		return userToBeRegistered.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED))
														 .orElseGet(() -> ResponseEntity.badRequest().build());
	}

	@PostMapping("login")
	public ResponseEntity<User> loginHandler( @RequestBody User user, HttpSession session ){
		Optional<User> userToLogin = userService.login(user);

		if(userToLogin.isEmpty()) {
			return null;
		}
		session.setAttribute("userId", userToLogin.get().getUserId());
		return userToLogin.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
											.orElseGet(() -> ResponseEntity.status(401).build());
	}

	@PostMapping("logout")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void logoutHandler(HttpSession session) {
		session.invalidate();
	}

	@PutMapping("update")
	public ResponseEntity<User> updateHandler( @RequestBody User user, HttpSession session ){
		if(session.getAttribute("userId") == null) {
			throw new UnauthenticatedException("You aren't logged in!");
		}

		int userId = (int) session.getAttribute("userId");
		Optional<User> userToUpdate = userService.update(userId, user);

		return userToUpdate.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
											.orElseGet(() -> ResponseEntity.status(401).build());
	}

	@GetMapping("id")
	public int getSessionIdHandler( HttpSession session ) {
		if(session.getAttribute("userId") == null) {
			throw new UnauthenticatedException("You aren't logged in!");
		}

		return (int) session.getAttribute("userId");
	}

	@DeleteMapping("delete")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteHandler(HttpSession session) {
		if(session.getAttribute(("userId")) == null) {
			throw new UnauthenticatedException("You aren't logged in!");
		}

		int userId = (int) session.getAttribute("userId");

		userService.delete(userId);
	}

}