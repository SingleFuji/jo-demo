package com.jo.demo.thread.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public interface ThreadPool {

  public static final int DEFAULT_THREADS = 30;

  public static final int KEEP_ALIVE_TIME = 30;

  ThreadPoolExecutor getExecutor();

  void execute(Runnable r);

  Future<?> submit(Runnable r);

  <T> Future<T> submit(Callable<T> callable);

  void destory();
}
