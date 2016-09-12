package com.jo.demo.thread.threadpool.fixed;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jo.demo.thread.threadpool.AbortPolicyWithReport;
import com.jo.demo.thread.threadpool.NamedThreadFactory;
import com.jo.demo.thread.threadpool.ThreadPool;


/**
 * 
 * <p>
 * Description: 请简述功能介绍
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015
 * </p>
 * <p>
 * 固定线程池 Company: Shenzhen XGD SoftWare
 * </p>
 * 
 * @since 2015年7月27日
 * @version 1.0
 * @author WillYang
 */
public final class FixedThreadPool implements ThreadPool {

  private static Logger LOGGER = LoggerFactory.getLogger(FixedThreadPool.class);

  private int threads;

  private long keepAliveTime;

  private String poolName;

  private ThreadFactory threadFactory;

  private ThreadPoolExecutor threadPoolExecutor;

  private RejectedExecutionHandler rejectedExceptionHandler;

  private BlockingDeque<Runnable> workQueue = new LinkedBlockingDeque<Runnable>();

  public FixedThreadPool(String poolName) {
    this(poolName, DEFAULT_THREADS, KEEP_ALIVE_TIME);
  }

  public FixedThreadPool(String poolName, long keepAliveTime) {
    this(poolName, DEFAULT_THREADS, keepAliveTime);
  }

  public FixedThreadPool(String poolName, int threads, long keepAliveTime) {
    this(poolName, threads, null, keepAliveTime);
  }

  public FixedThreadPool(String poolName, int threads,
      RejectedExecutionHandler rejectedExecutionHandler, long keepAliveTime) {
    this.poolName = poolName;
    this.threads = threads;
    this.rejectedExceptionHandler = (rejectedExecutionHandler != null ? rejectedExecutionHandler
        : new AbortPolicyWithReport(poolName));
    this.keepAliveTime = (keepAliveTime < 0 ? KEEP_ALIVE_TIME : keepAliveTime);
    initialize();
  }

  private void initialize() {
    threadFactory = new NamedThreadFactory(poolName);
    threadPoolExecutor = new ThreadPoolExecutor(threads, threads, keepAliveTime, TimeUnit.SECONDS,
        workQueue, threadFactory, rejectedExceptionHandler) {
      @Override
      protected void beforeExecute(Thread t, Runnable r) {
        LOGGER.debug("Thread started:" + t.toString() + "| " + r.toString());
      }

      @Override
      protected void afterExecute(Runnable r, Throwable t) {
        LOGGER.debug("Thread end:" + r.toString());
        if (t != null) {
          LOGGER.error("Thead catch exception.", t);
        }
      }
    };
  }

  @Override
  public ThreadPoolExecutor getExecutor() {
    return threadPoolExecutor;
  }

  @Override
  public void execute(Runnable r) {
    threadPoolExecutor.execute(r);
  }

  @Override
  public Future<?> submit(Runnable r) {
    return threadPoolExecutor.submit(r);
  }

  @Override
  public <T> Future<T> submit(Callable<T> callable) {
    return threadPoolExecutor.submit(callable);
  }

  @Override
  public void destory() {
    if (threadPoolExecutor.isTerminated()) {
      LOGGER.info("======" + poolName + " is terminated======");
      return;
    }
    LOGGER.info("======Ready to shutdown thread pool " + poolName + "======");
    if (!threadPoolExecutor.isShutdown()) {
      threadPoolExecutor.shutdown();
    }
    try {
      threadPoolExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      LOGGER.warn("======The thread pool is interrupted======");
    }

    LOGGER.info("======Success to shutdown thread pool " + poolName + "======");
  }

}
