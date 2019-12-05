package com.spring.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.library.entities.Library;

public interface LibraryRepository extends JpaRepository<Library, Integer> {

	Library getOneByUsername(String username);

}
