package com.spring.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user_details")
public class User {

	@Id
	@GeneratedValue
	private int id;

	@NotNull(message = "Username can't be empty")
	@Column(nullable = false)
	private String username;

	@NotNull(message = "Password can't be empty")
	@Column(nullable = false,unique = true)
	private String password;

	@NotNull(message = "Role can't be empty")
	@Column(nullable = false)
	private String role;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
