package com.spring.book.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.book.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{
	public Optional<Book> findByName(String name);
}
