package com.spring.book.utils;

import org.springframework.stereotype.Component;

@Component
public class UserContext {
	public final static String CORRELATION_ID = "tmx-correlation-id";
	public final static String AUTH_TOKEN = "tmx-auth-token";
	public final static String USER_ID = "tmx-user-id";

	private String correlationId = new String();
	private String authToken = new String();
	private String userId = new String();

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
