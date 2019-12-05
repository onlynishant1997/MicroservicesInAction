package com.spring.library.clients;

import java.util.List;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.spring.user.model.User;


@FeignClient("userservice")
public interface UserClientUsingFeign {
	
	@GetMapping("/user/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable String username);
	
	@GetMapping("/user/")
	public ResponseEntity<List<User>> getAllUsers();
	
	@PostMapping("/user/")
	public ResponseEntity<User> createUserIfNotExist(@Valid @RequestBody User user) ;
	
	@DeleteMapping("/user/{username}")
	public ResponseEntity<User> deleteUser(@PathVariable String username);
	
	
}
