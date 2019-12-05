package com.spring.user.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.user.model.User;
import com.spring.user.repository.UserRepository;

@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	public boolean saveUserIfDoesNotExist(User user) {
		logger.info("Inside UserService.saveUserIfDoesNotExist");
		boolean userSaved = true;
		Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
		if (optionalUser.isPresent()) {
			logger.debug("User Already Exists");
			userSaved = false;
		} else {
			logger.info("Saving User");
			userRepository.save(user);
			userSaved = true;
		}
		return userSaved;
	}

	public Optional<User> getUserByUsername(String username) {
		logger.info("Inside UserService.getUserByUsername");
		return userRepository.findByUsername(username);
	}

	public List<User> getAllUsers() {
		logger.info("Inside UserService.getAllUsers");
		return userRepository.findAll();
	}

	public boolean deleteUserByUsername(String username) {
		logger.info("Inside UserService.deleteUserByUsername");
		boolean userDeleted = true;
		Optional<User> optionalUser = userRepository.findByUsername(username);
		if (optionalUser.isPresent()) {
			logger.info("Deleting user");
			userDeleted = true;
			userRepository.delete(optionalUser.get());
		} else {
			logger.debug("User not exist therefore cant be deleted.");
			userDeleted = false;
		}
		return userDeleted;
	}
}
