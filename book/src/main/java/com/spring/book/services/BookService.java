package com.spring.book.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.spring.book.model.Book;
import com.spring.book.repository.BookRepository;
import com.spring.book.utils.UserContextHolder;

/* 
 * In default properties we can define the common thread pool which will be shared by all the hystrix managed request comming to this service unless overriden inside the class.
 * threadPoolKey defines the name of the threadpool.
 * threadPoolProperties can be used to define the size of thread pool & the queue which sits in front of threadpool.
 * coreSize defines the size of thread pool.
 * maxQueueSize is queue sitting in front of coreSize which will be used if all the threads inside threadpool are busy then new incomming request will be waiting in the queue sitting in front.
 * execution.isolation.thread.timeoutInMilliseconds is used to define a timeout value. any request taking more time than the timeout defined will throw timeout exception or be forwarded to fallback method if defined.
 *  */
@DefaultProperties(threadPoolKey = "bookServicePoolForEntireClass", threadPoolProperties = {
		@HystrixProperty(name = "coreSize", value = "30"),
		@HystrixProperty(name = "maxQueueSize", value = "10") }, commandProperties = {
				@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "12000"), })
@Service
public class BookService {

	private static final Logger logger = LoggerFactory.getLogger(BookService.class);

	@Autowired
	private BookRepository bookRepository;

	@HystrixCommand(commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "12000"), }, fallbackMethod = "fallBackMethod", threadPoolKey = "poolSpecificToThisMethod", threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "30"),
					@HystrixProperty(name = "maxQueueSize", value = "10") })
	public List<Book> getAllBooks() {
		logger.info("BookService.getAllBooks CorrelationId : {}", UserContextHolder.getContext().getCorrelationId());
		try {
			Thread.sleep(1000); // Increase the timeout if you want to call the hystrix fallback method.
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return bookRepository.findAll();
	}

	@HystrixCommand(commandKey = "circuitBreakerKey", fallbackMethod = "fallBackMethod", commandProperties = {
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
			@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
			@HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5") }, threadPoolKey = "bookservicepool", threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "30"),
					@HystrixProperty(name = "maxQueueSize", value = "10") })
	public Optional<Book> getBookById(int id) {
		logger.info("BookService.getBookById CorrelationId : {}", UserContextHolder.getContext().getCorrelationId());
		Optional<Book> optionalBook = bookRepository.findById(id);
		return optionalBook;
	}

	public void saveBook(Book book) {
		logger.info("BookService.saveBook CorrelationId : {}", UserContextHolder.getContext().getCorrelationId());
		Optional<Book> optionalBook = bookRepository.findByName(book.getName());
		if (optionalBook.isPresent()) {
			Book bookRef = bookRepository.getOne(optionalBook.get().getId());
			bookRef.setQuantity(book.getQuantity());
			bookRef.setAuthor(book.getAuthor());
			bookRef.setPrice(book.getPrice());
			bookRepository.save(bookRef);
		} else {
			bookRepository.save(book);
		}
	}

	public boolean deleteBook(int id) {
		logger.info("BookService.deleteBook CorrelationId : {}", UserContextHolder.getContext().getCorrelationId());
		boolean bookDeleted = false;
		Optional<Book> optionalBook = bookRepository.findById(id);
		if (optionalBook.isPresent()) {
			bookRepository.deleteById(id);
			bookDeleted = true;
		} else {
			bookDeleted = false;
		}
		return bookDeleted;
	}

	private List<Book> fallBackMethod() {
		List<Book> list = new ArrayList<Book>();
		Book book = new Book();
		book.setName("Fallback method called");
		book.setAuthor("Hystrix Fallback");
		list.add(book);
		return list;
	}

	private Optional<Book> fallBackMethod(int id) {
		Book book = new Book();
		book.setName("Fallback method called");
		book.setAuthor("Hystrix Fallback");
		return Optional.of(book);
	}
}
