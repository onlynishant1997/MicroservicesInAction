package com.spring.library.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.spring.book.model.Book;

@FeignClient("bookservice")
public interface BookClientUsingFeign {
	@GetMapping("/book/")
	public ResponseEntity<List<Book>> getAllBooks();
	
	@GetMapping("/book/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable int id);
	
	@PostMapping("/book/")
	public ResponseEntity<Book> saveBook(@RequestBody Book book);
	
	@DeleteMapping("/book/{id}")
	public ResponseEntity<Book> deleteBook(@PathVariable int id);
}
