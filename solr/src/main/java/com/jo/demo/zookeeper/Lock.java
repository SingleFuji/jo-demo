package com.jo.demo.zookeeper;

import org.apache.zookeeper.KeeperException;

public interface Lock {
	
	public boolean lock() throws KeeperException;
	
	public void unLock();

}
