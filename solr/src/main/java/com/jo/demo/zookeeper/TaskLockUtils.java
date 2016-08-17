package com.jo.demo.zookeeper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jo.demo.utils.ConfigUtils;

/**
 * web分布式
 * @author wujun
 *
 */
public class TaskLockUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskLockUtils.class);
	
	private static Lock lock;
	
	/**
	 * 初始化连接
	 */
	public static void init() {
		try {
			logger.info("web task connecting zk...");
			String connectString = ConfigUtils.ZK.getProperty("web.task.zkhost", ""); 
			logger.info("web.task.zkhost: " + connectString);
			String root = ConfigUtils.ZK.getProperty("web.lock.rootpath", "openplatformlock");
			logger.info("web.lock.rootpath: " + root);
			String lockName = ConfigUtils.ZK.getProperty("web.lock.node", "web"); 
			logger.info("lockName: " + lockName);
			int sessionTimeout = ConfigUtils.ZK.getProperty("web.task.timeout", "30000", Integer.class);
			logger.info("web.task.timeout: " + sessionTimeout);
			lock = new DistributedLock(connectString, root, lockName, sessionTimeout);
			logger.info("web task connecting zk success.");
			return;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("web task connecting zk fail.");
	}
	
	/**
	 * 获取锁
	 * @return 如果返回false说明获取锁失败,不要走下面的锁流程
	 */
	public static boolean lock() {
		try {
			if(null == lock) {
				return false;
			}
			return lock.lock();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		unLock();
		return false;
	}
	
	public static void unLock() {
		try {
			if(null == lock) {
				return;
			}
			lock.unLock();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private TaskLockUtils() {}

}
