package com.jo.demo.thread.threadpool.cached;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
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
 * 可伸缩线程池 Company: Shenzhen XGD SoftWare
 * </p>
 * 
 * @since 2015年7月27日
 * @version 1.0
 * @author WillYang
 */
public final class CachedThreadPool implements ThreadPool {
  private static Logger LOGGER = LoggerFactory.getLogger(CachedThreadPool.class);

  private long keepAliveTime;

  private String poolName;

  private ThreadFactory threadFactory;

  private ThreadPoolExecutor threadPoolExecutor;

  private RejectedExecutionHandler rejectedExceptionHandler;

  private SynchronousQueue<Runnable> workQueue = new SynchronousQueue<Runnable>();

  public CachedThreadPool(String poolName) {
    this(poolName, null, KEEP_ALIVE_TIME);
  }


  public CachedThreadPool(String poolName, long keepAliveTime) {
    this(poolName, null, keepAliveTime);
  }

  public CachedThreadPool(String poolName, RejectedExecutionHandler rejectedExecutionHandler,
      long keepAliveTime) {
    this.poolName = poolName;
    this.rejectedExceptionHandler = (rejectedExecutionHandler != null ? rejectedExecutionHandler
        : new AbortPolicyWithReport(poolName));
    this.keepAliveTime = (keepAliveTime < 0 ? KEEP_ALIVE_TIME : keepAliveTime);
    initialize();
  }

  private void initialize() {
    threadFactory = new NamedThreadFactory(poolName);
    threadPoolExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, keepAliveTime,
        TimeUnit.SECONDS, workQueue, threadFactory, rejectedExceptionHandler) {
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
