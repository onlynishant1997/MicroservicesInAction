package com.spring.library.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.book.model.Book;
import com.spring.library.clients.BookClientUsingFeign;
import com.spring.library.clients.UserClientUsingFeign;
import com.spring.library.services.LibraryService;
import com.spring.user.model.User;

@RestController
@RequestMapping("/library")
public class LibraryController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserClientUsingFeign userClientUsingFeign;

	@Autowired
	private BookClientUsingFeign bookClientUsingFeign;
	
	@Autowired
	private LibraryService libraryService;

	@GetMapping("/user/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
		logger.info("In LibraryController.getUserByUsername " + username);
		return userClientUsingFeign.getUserByUsername(username);
	}

	@GetMapping("/user/")
	public ResponseEntity<List<User>> getAllUsers() {
		logger.info("In LibraryController.getAllUsers");
		return userClientUsingFeign.getAllUsers();
	}

	@PostMapping("/user/")
	public ResponseEntity<User> createUserIfNotExist(@Valid @RequestBody User user) {
		logger.info("In LibraryController.createUserIfNotExist " + user);
		return userClientUsingFeign.createUserIfNotExist(user);
	}

	@DeleteMapping("/user/{username}")
	public ResponseEntity<User> deleteUser(@PathVariable String username) {
		logger.info("In LibraryController.deleteUser " + username);
		return userClientUsingFeign.deleteUser(username);
	}

	@GetMapping("/book/")
	public ResponseEntity<List<Book>> getAllBooks() {
		logger.info("In LibraryController.getAllBooks");
		return bookClientUsingFeign.getAllBooks();
	}

	@GetMapping("/book/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable int id) {
		logger.info("In LibraryController.getBookById " + id);
		return bookClientUsingFeign.getBookById(id);
	}

	@PostMapping("/book/")
	public ResponseEntity<Book> saveBook(@RequestBody Book book) {
		logger.info("In LibraryController.saveBook " + book);
		return bookClientUsingFeign.saveBook(book);
	}

	@DeleteMapping("/book/{id}")
	public ResponseEntity<Book> deleteBook(@PathVariable int id) {
		logger.info("In LibraryController.deleteBook " + id);
		return bookClientUsingFeign.deleteBook(id);
	}
	
	@PutMapping("/users/{user_id}/books/{book_id}")
	public ResponseEntity issueBook(@PathVariable(name = "user_id") String username,@PathVariable int book_id){
		logger.info("In LibraryController.issueBook to user " + username + "& bookId " + book_id);
		libraryService.foo(username, book_id);
		return ResponseEntity.ok("");
	}

}
