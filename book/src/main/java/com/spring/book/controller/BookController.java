package com.spring.book.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;

import com.spring.book.model.Book;
import com.spring.book.services.BookService;

@RestController
@RequestMapping("/book")
public class BookController {
	private static final Logger logger = LoggerFactory.getLogger(BookController.class);

	@Autowired
	private BookService bookService;
	
	@GetMapping("/")
	public ResponseEntity<List<Book>> getAllBooks(){
		logger.info("Get All Books Endpoint");
		return ResponseEntity.ok(bookService.getAllBooks());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable int id){
		logger.info("Get Book By Id Endpoint " + id);
		Optional<Book> optionalBook = bookService.getBookById(id);
		if(!optionalBook.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(optionalBook.get());
	}
	
	@PostMapping("/")
	public ResponseEntity<Book> saveBook(@RequestBody Book book) {
		logger.info("Save Book Endpoint " + book);
		bookService.saveBook(book);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Book> deleteBook(@PathVariable int id){
		logger.info("Delete Book By Id Endpoint " + id);
		boolean isDelted = bookService.deleteBook(id);
		if(!isDelted) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.noContent().build();
	}
}
