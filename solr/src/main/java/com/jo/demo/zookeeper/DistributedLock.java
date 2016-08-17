package com.jo.demo.zookeeper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

/**
 * zk 分布式锁
 * @author wujun
 *
 */
public class DistributedLock implements Watcher, Lock {
	
	private static final Logger logger = LoggerFactory.getLogger(DistributedLock.class);
	
	private static final String SEPARATOR = "/";
	private ZooKeeper zk;
	private String root = SEPARATOR + "tradelock";//根
	private String lockName;//竞争资源的标志
//	private String waitNode;//等待前一个锁
	private String myZnode;//当前锁节点
	private Object myZnodeLock = new Object(); ////节点锁
//	private CountDownLatch latch;//计数器
	private int sessionTimeout = 30000;
//	private TimeUnit unit = TimeUnit.MILLISECONDS;
	private String splitStr = "_lock_";
	private String connectString = "";
	
	/**
	 * 
	 * @param connectString zk配置地址
	 * @param root 根节点
	 * @param lockName 竞争资源标志,lockName中不能包含单词lock
	 * @param sessionTimeout 超时时间  {@link TimeUnit#MILLISECONDS}
	 */
	public DistributedLock(String connectString, String root, String lockName, int sessionTimeout) throws KeeperException  {
		if(StringUtils.isEmpty(connectString) || StringUtils.isEmpty(lockName)) {
			return;
		}
		this.connectString = connectString;
		if(lockName.contains(splitStr)) {
			throw KeeperException.create(Code.INVALIDACL);
		}
		this.lockName = lockName;
		if(sessionTimeout > 0) {
			this.sessionTimeout = sessionTimeout;
		}
		if(!StringUtils.isEmpty(root)) {
			this.root = SEPARATOR + root;
		}
		connect();
	}
	
	/**
	 * 连接zk，创建根节点
	 * @param connectString
	 */
	private void connect() throws KeeperException {
		for(int i = 1; i <= 3; i++){
			try {
				if(null == zk) {
					zk = new ZooKeeper(connectString, sessionTimeout, this);
				}
				Stat stat = zk.exists(root, false);
				if(null == stat) {
					//创建跟节点
					zk.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				}
				break;
			} catch (Exception e) {
				if(i == 3) {
					//最后一次尝试还是失败
					logger.error(e.getMessage(), e);
					zk = null;
					throw KeeperException.create(Code.CONNECTIONLOSS);
				} else {
					try {
						//错误了停顿下
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
						logger.error(e1.getMessage(), e1);
					}
				}
			}
		}
	}

	@Override
	public void process(WatchedEvent arg0) {
		try {
//			if(null != this.latch) {
//				this.latch.countDown();
//			}
			//不接受通知，定时器主动查询zk配置列表
			lock();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@Override
	public boolean lock() throws KeeperException  {
		boolean flag = false;
		try {
			if(null == zk) {
				throw KeeperException.create(Code.CONNECTIONLOSS);
			}
			if(this.tryLock()) {
				//获取到锁
				logger.debug("Thread " + Thread.currentThread().getId() + " " +myZnode + " get lock true");
				flag = true;
			} else {
//				//等待锁
//				this.waitForLock(waitNode, sessionTimeout);
			}
		} catch (KeeperException e) {
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw KeeperException.create(Code.SYSTEMERROR);
		}
		return flag;
	}
	
	private boolean tryLock() throws KeeperException {
		boolean flag = false;
		try {
			List<String> lockObjNodes = checkNode();
		
			logger.debug(myZnode + "==" + lockObjNodes.get(0));
			if(myZnode.equals(root + SEPARATOR + lockObjNodes.get(0))) {
				//如果是最小的节点,则表示取得锁
				flag = true;
			} else {
				//如果不是最小的节点，找到比自己小1的节点
//				String subMyZnode = myZnode.substring(myZnode.lastIndexOf(SEPARATOR) + 1);
//				waitNode = lockObjNodes.get(Collections.binarySearch(lockObjNodes, subMyZnode) - 1);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw KeeperException.create(Code.SYSTEMERROR);
		}
		return flag;
	}
	
	private List<String> checkNode() {
		try {
			synchronized (myZnodeLock) {
				if(StringUtils.isEmpty(myZnode)) {
					//创建临时子节点:自增长的临时节点
					myZnode = zk.create(root + SEPARATOR + lockName + splitStr, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
					logger.debug(myZnode + "is created");
				}
				//取出所有子节点
				List<String> subNodes = zk.getChildren(root, false);
				//取出所有lockName的锁
				List<String> lockObjNodes = new ArrayList<String>();
				if(!CollectionUtils.isEmpty(subNodes)) {
					for (String node : subNodes) {
						String _node = node.split(splitStr)[0];
						if(_node.equals(lockName)){
							lockObjNodes.add(node);
						}
					}
				}
				Collections.sort(lockObjNodes);
				boolean flag = false;
				for(String node : lockObjNodes) {
					if(!StringUtils.isEmpty(node) && myZnode.endsWith(node)) {
						flag = true;
						break;
					}
				}
				if(!flag){
					//如果不存在节点重新连接
					zk.close();
					myZnode = "";
					zk = null;
					connect();
				}
				return lockObjNodes;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return Collections.emptyList();
	}
	
//	private boolean waitForLock(String lower, long waitTime) throws KeeperException {
//		boolean flag = false;
//		try {
//			Stat stat = zk.exists(root + SEPARATOR + lower,true);
//			//判断比自己小一个数的节点是否存在,如果不存在则无需等待锁,同时注册监听
//			if(null != stat) {
//				logger.debug("Thread " + Thread.currentThread().getId() + " waiting for " + root + SEPARATOR + lower);
//				this.latch = new CountDownLatch(1);
//				flag = this.latch.await(waitTime, unit);
//				this.latch = null;
//			}
//		} catch (KeeperException e) {
//			throw e;
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			throw KeeperException.create(Code.SYSTEMERROR);
//		}
//		return flag;
//	}
	
	@Override
	public void unLock() {
		try {
			if(null == zk) {
				return;
			}
			logger.debug("unlock " + myZnode);
			zk.delete(myZnode, -1);
			zk.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public static void main(String[] args) {
		DistributedLock lock = null;
		try {
			lock = new DistributedLock("/trade_right_sign_lock", "172.20.12.19:2190","test1", 300000);
			lock.lock();
		} catch (KeeperException e) {
			lock.unLock();
			logger.error(e.getMessage(), e);
		}
	}
	
}
