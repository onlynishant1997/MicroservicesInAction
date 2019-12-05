package com.spring.book.utils;

import org.springframework.util.Assert;

public class UserContextHolder {
	private static final ThreadLocal<UserContext> userContextThreadLocal = new ThreadLocal<UserContext>();

	public final static UserContext getContext() {
		UserContext userContext = userContextThreadLocal.get();
		if (userContext == null) {
			userContext = createEmptyContext();
			userContextThreadLocal.set(userContext);
		}
		return userContext;
	}

	private static final UserContext createEmptyContext() {
		return new UserContext();
	}

	public static final void setContext(UserContext context) {
		Assert.notNull(context, "Only not null user context instances are permitted");
		userContextThreadLocal.set(context);
	}
}
