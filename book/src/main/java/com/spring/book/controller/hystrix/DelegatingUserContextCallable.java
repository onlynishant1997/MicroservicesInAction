package com.spring.book.controller.hystrix;

import java.util.concurrent.Callable;

import com.spring.book.utils.UserContext;
import com.spring.book.utils.UserContextHolder;

public class DelegatingUserContextCallable<V> implements Callable<V> {

	private final Callable<V> delegate;
	private UserContext originalUserContext;

	public DelegatingUserContextCallable(Callable<V> callable, UserContext context) {
		delegate = callable;
		originalUserContext = context;
	}

	@Override
	public V call() throws Exception {
		UserContextHolder.setContext(originalUserContext);
		try {
			return delegate.call();
		} finally {
			this.originalUserContext = null;
		}
	}

	public static <V> Callable<V> create(Callable<V> delegate, UserContext userContext) {
		return new DelegatingUserContextCallable<V>(delegate, userContext);
	}

}
