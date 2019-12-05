package com.spring.library.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Library {
	@Id
	@GeneratedValue
	private int id;
	@Column
	private String username;
	@ElementCollection
	List<Integer> idOfBooksIssued;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Integer> getIdOfBooksIssued() {
		return idOfBooksIssued;
	}

	public void setIdOfBooksIssued(List<Integer> idOfBooksIssued) {
		this.idOfBooksIssued = idOfBooksIssued;
	}

	@Override
	public String toString() {
		return "UserBook [id=" + id + ", username=" + username + ", idOfBooksIssued=" + idOfBooksIssued + "]";
	}

}
