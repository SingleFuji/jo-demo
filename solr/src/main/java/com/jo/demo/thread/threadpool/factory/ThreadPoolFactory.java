package com.jo.demo.thread.threadpool.factory;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.jo.demo.thread.threadpool.ThreadPool;
import com.jo.demo.thread.threadpool.fixed.FixedThreadPool;

public class ThreadPoolFactory {

  private static Map<String, ThreadPool> threadPools = new ConcurrentHashMap<String, ThreadPool>();

  private ThreadPoolFactory() {}

  public static ThreadPool getFixedThreadPool(String poolName, int threads) {
    return getFixedThreadPool(poolName, threads, 0L);
  }

  public static ThreadPool getFixedThreadPool(String poolName, int threads, long keepAliveTime) {
    ThreadPool threadPool = threadPools.get(poolName);
    if (threadPool == null) {
      synchronized (ThreadPoolFactory.class) {
        threadPool = threadPools.get(poolName);
        if (threadPool == null) {
          threadPool = new FixedThreadPool(poolName, threads, keepAliveTime);
          threadPools.put(poolName, threadPool);
        }

      }
    }
    return threadPool;
  }

  public static ThreadPool getFixedThreadPool(String poolName) {
    return getFixedThreadPool(poolName, ThreadPool.DEFAULT_THREADS, 0L);
  }


  public static void destory() {
    Iterator<Map.Entry<String, ThreadPool>> iterator = threadPools.entrySet().iterator();
    while (iterator.hasNext()) {
      Entry<String, ThreadPool> entry = iterator.next();
      threadPools.remove(entry.getKey());
      ThreadPool threadPool = entry.getValue();
      threadPool.destory();
    }
  }

  public static void destory(String poolName) {
    ThreadPool threadPool = threadPools.get(poolName);
    if (threadPool == null) {
      return;
    }
    threadPools.remove(poolName);
    threadPool.destory();
  }
}
