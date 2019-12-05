package com.spring.book.controller.hystrix;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableLifecycle;
import com.netflix.hystrix.strategy.properties.HystrixProperty;
import com.spring.book.utils.UserContextHolder;

public class ThreadLocalAwareStrategy extends HystrixConcurrencyStrategy {
	private HystrixConcurrencyStrategy hystrixConcurrencyStrategy;

	public ThreadLocalAwareStrategy(HystrixConcurrencyStrategy concurrencyStrategy) {
		hystrixConcurrencyStrategy = concurrencyStrategy;
	}

	@Override
	public BlockingQueue<Runnable> getBlockingQueue(int maxQueueSize) {
		return hystrixConcurrencyStrategy != null ? hystrixConcurrencyStrategy.getBlockingQueue(maxQueueSize)
				: super.getBlockingQueue(maxQueueSize);
	}

	@Override
	public <T> HystrixRequestVariable<T> getRequestVariable(HystrixRequestVariableLifecycle<T> rv) {
		return super.getRequestVariable(rv);
	}

	@Override
	public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey, HystrixProperty<Integer> corePoolSize,
			HystrixProperty<Integer> maximumPoolSize, HystrixProperty<Integer> keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		return super.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	@Override
	public <T> Callable<T> wrapCallable(Callable<T> callable) {
		return hystrixConcurrencyStrategy != null
				? hystrixConcurrencyStrategy
						.wrapCallable(new DelegatingUserContextCallable<T>(callable, UserContextHolder.getContext()))
				: super.wrapCallable(new DelegatingUserContextCallable<T>(callable, UserContextHolder.getContext()));
	}
}
