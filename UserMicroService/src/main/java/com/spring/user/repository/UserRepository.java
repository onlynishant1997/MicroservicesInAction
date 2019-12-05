package com.spring.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	public Optional<User> findByUsername(String username);
	
	//Delete repository method is not being used since it was not working.
	public void deleteByUsername(String username);
}
