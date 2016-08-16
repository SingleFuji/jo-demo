package com.jo.demo.redis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis处理类
 * 
 * @author zhouhang
 *
 */
public class RedisBaseService {

	private static final Logger logger = LoggerFactory.getLogger(RedisBaseService.class);

	private RedisTemplate<Serializable, Serializable> redisTemplate;

	public RedisTemplate<Serializable, Serializable> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<Serializable, Serializable> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 
	 * @param sKey
	 * @return
	 */
	public List<byte[]> keys(final String sKey) {
		logger.debug("LISTSIZE KEY:" + sKey);
		return redisTemplate.execute(new RedisCallback<List<byte[]>>() {
			@Override
			public List<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = redisTemplate.getStringSerializer().serialize(sKey);
				Set<byte[]> keys = connection.keys(key);
				List<byte[]> result = new ArrayList<byte[]>();
				for(byte[] tmpKey:keys){
					byte[]value = connection.get(tmpKey);
//					System.out.println(new String(value));
					result.add(value);
				}
				return result;
			}
		});
	}
}
