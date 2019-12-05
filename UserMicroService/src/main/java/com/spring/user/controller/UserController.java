package com.spring.user.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.user.model.User;
import com.spring.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);


	@Autowired
	private UserService userService;

	@GetMapping("/{username}")
	@ResponseBody
	public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
		logger.info("Get User By Username Endpoint : " + username);
		Optional<User> optionalUser = userService.getUserByUsername(username);
		if (!optionalUser.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.status(HttpStatus.OK).body(optionalUser.get());
	}

	@GetMapping("/")
	public ResponseEntity<List<User>> getAllUsers() {
		logger.info("Get All Users Endpoint");
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@PostMapping("/")
	public ResponseEntity<User> createUserIfNotExist(@Valid @RequestBody User user) {
		logger.info("Save User Endpoint : " + user);
		boolean userSaved = userService.saveUserIfDoesNotExist(user);
		if (!userSaved) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping("/{username}")
	public ResponseEntity<User> deleteUser(@PathVariable String username) {
		logger.info("Delete User By Username Endpoint : " + username);
		boolean userDeleted = userService.deleteUserByUsername(username);
		if (!userDeleted)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
