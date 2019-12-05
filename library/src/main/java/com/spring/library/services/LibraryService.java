package com.spring.library.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring.book.model.Book;
import com.spring.library.clients.BookClientUsingFeign;
import com.spring.library.clients.UserClientUsingFeign;
import com.spring.library.entities.Library;
import com.spring.library.repository.LibraryRepository;
import com.spring.user.model.User;

@Service
public class LibraryService {
	@Autowired
	private UserClientUsingFeign userClientUsingFeign;

	@Autowired
	private BookClientUsingFeign bookClientUsingFeign;

	@Autowired
	private LibraryRepository libraryRepository;

	public String foo(String username, int bookId) {
		ResponseEntity<User> userResponseEntity = userClientUsingFeign.getUserByUsername(username);
		ResponseEntity<Book> bookResponseEntity = bookClientUsingFeign.getBookById(bookId);
		Book book = null;
		User user = null;
		if (userResponseEntity.getStatusCode() != HttpStatus.OK) {
			return "User not valid";
		}
		if (bookResponseEntity.getStatusCode() != HttpStatus.OK) {
			return "Book not valid";
		}
		book = bookResponseEntity.getBody();
		user = userResponseEntity.getBody();
		if (book.getQuantity() <= 0) {
			return "All books are issued.";
		}
		try {
			Library library = libraryRepository.getOneByUsername(username);
			if (library == null) {
				Library newLibrary = new Library();
				newLibrary.setUsername(user.getUsername());
				List<Integer> list = new ArrayList<Integer>();
				list.add(book.getId());
				newLibrary.setIdOfBooksIssued(list);
				libraryRepository.save(newLibrary);
			} else {
				List<Integer> listOfBookId = library.getIdOfBooksIssued();
				listOfBookId.add(book.getId());
				library.setIdOfBooksIssued(listOfBookId);
				libraryRepository.save(library);
			}
		} catch (EntityNotFoundException exception) {
			System.out.println(exception.getMessage());

		}
		book.setQuantity(book.getQuantity() - 1);
		bookClientUsingFeign.saveBook(book);
		return "";
	}

}
