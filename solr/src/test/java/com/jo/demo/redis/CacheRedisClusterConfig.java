package com.jo.demo.redis;

import java.util.HashSet;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;

/**
 * redis 集群配置
 * @author wujun
 *
 */
public class CacheRedisClusterConfig extends RedisClusterConfiguration {
	
	
	public CacheRedisClusterConfig(String config) {
		if(StringUtils.isEmpty(config)) {
			return;
		}
		String[] array = config.split(",");
		if(null == array || array.length <= 0) {
			return;
		}
		HashSet<RedisNode> nodes = new HashSet<RedisNode>();
		for(String str : array) {
			if(StringUtils.isEmpty(str)) {
				continue;
			}
			String[] nodeArray = str.split(":");
			if(null == nodeArray || nodeArray.length != 2) {
				return;
			}
			RedisNode node = new RedisNode(nodeArray[0].trim(), Integer.valueOf(nodeArray[1].trim()));
			nodes.add(node);
		}
		setClusterNodes(nodes);
	}
	
	public CacheRedisClusterConfig(String config, int maxRedirects) {
		if(StringUtils.isEmpty(config)) {
			return;
		}
		String[] array = config.split(",");
		if(null == array || array.length <= 0) {
			return;
		}
		HashSet<RedisNode> nodes = new HashSet<RedisNode>();
		for(String str : array) {
			if(StringUtils.isEmpty(str)) {
				continue;
			}
			String[] nodeArray = str.split(":");
			if(null == nodeArray || nodeArray.length != 2) {
				return;
			}
			RedisNode node = new RedisNode(nodeArray[0].trim(), Integer.valueOf(nodeArray[1].trim()));
			nodes.add(node);
		}
		setClusterNodes(nodes);
		setMaxRedirects(maxRedirects);
	}
	
	
	@Override
	public void setClusterNodes(Iterable<RedisNode> arg0) {
		// TODO Auto-generated method stub
		super.setClusterNodes(arg0);
	}
	
	@Override
	public void setMaxRedirects(int maxRedirects) {
		super.setMaxRedirects(maxRedirects);
	}

}
