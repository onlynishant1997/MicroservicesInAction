package com.spring.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.user.model.User;
import com.spring.user.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public boolean saveUserIfDoesNotExist(User user) {
		boolean userSaved = true;
		Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
		if (optionalUser.isPresent()) {
			userSaved = false;
		} else {
			userRepository.save(user);
			userSaved = true;
		}
		return userSaved;
	}

	public Optional<User> getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public boolean deleteUserByUsername(String username) {
		boolean userDeleted = true;
		Optional<User> optionalUser = userRepository.findByUsername(username);
		if (optionalUser.isPresent()) {
			userDeleted = true;
			userRepository.delete(optionalUser.get());
		} else {
			userDeleted = false;
		}
		return userDeleted;
	}
}
