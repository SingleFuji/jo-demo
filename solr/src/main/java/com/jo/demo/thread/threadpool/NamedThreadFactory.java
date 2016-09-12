package com.jo.demo.thread.threadpool;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NamedThreadFactory implements ThreadFactory {

  private String namePrefix;

  private ThreadGroup threadGroup;

  private AtomicInteger threadCount = new AtomicInteger(1);

  private UncaughtExceptionHandler exceptionHandler;

  public NamedThreadFactory(String poolName) {
    SecurityManager sm = System.getSecurityManager();
    if (sm != null) {
      threadGroup = sm.getThreadGroup();
    } else {
      threadGroup = Thread.currentThread().getThreadGroup();
    }
    this.namePrefix = poolName + "-thread-";
    this.exceptionHandler = new DefaultExceptionHandler();
  }

  public NamedThreadFactory(String poolName, UncaughtExceptionHandler exceptionHandler) {
    SecurityManager sm = System.getSecurityManager();
    if (sm != null) {
      threadGroup = sm.getThreadGroup();
    } else {
      threadGroup = Thread.currentThread().getThreadGroup();
    }
    this.namePrefix = poolName + "-thread-";
    this.exceptionHandler = exceptionHandler;
  }

  @Override
  public Thread newThread(Runnable r) {
    String threadName = namePrefix + threadCount.getAndIncrement();
    Thread thread = new Thread(threadGroup, r, threadName, 0);
    thread.setDaemon(false);
    thread.setPriority(Thread.NORM_PRIORITY);
    thread.setUncaughtExceptionHandler(exceptionHandler);
    return thread;
  }

  private class DefaultExceptionHandler implements UncaughtExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @Override
    public void uncaughtException(Thread t, Throwable e) {
      logger.error("Thread caught exception. Thread[threadName = " + t.getName() + ", isDaemin="
          + t.isDaemon() + "]", e);
    }

  }

}
