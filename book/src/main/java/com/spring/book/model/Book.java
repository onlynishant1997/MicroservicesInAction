package com.spring.book.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "books_details")
public class Book {
	@Id
	@GeneratedValue
	private int id;
	@NotEmpty(message = "Book name can't be empty.")
	@Column(nullable = false, unique = true)
	private String name;
	@NotEmpty(message = "Book author can't be empty.")
	@Column(nullable = false)
	private String author;
	@Column(nullable = false, precision = 3)
	private Double price;
	@Column(nullable = false)
	private int quantity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", author=" + author + ", price=" + price + ", quantity="
				+ quantity + "]";
	}

}
