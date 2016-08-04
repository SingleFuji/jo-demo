package com.jo.demo.redis;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis处理类
 * 
 * @author zhouhang
 *
 */
public class RedisListService implements IRedisListService {

	private static final Logger logger = LoggerFactory.getLogger(RedisListService.class);

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
	@Override
	public long listSize(final String sKey) {
		logger.info("LISTSIZE KEY:{}" ,sKey);
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = redisTemplate.getStringSerializer().serialize(sKey);

				if (connection.exists(key)) {
					long listSize = connection.lLen(key);
					return listSize;
				}
				return 0L;
			}
		});
	}
	
	@Override
	public List<byte[]> listRange(final String sKey, final long begin, final long end) {
		logger.debug("LRANGE KEY:"+sKey +" BEGIN:"+ begin +" END:"+ end);
		return redisTemplate.execute(new RedisCallback<List<byte[]>>() {
			@Override
			public List<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = redisTemplate.getStringSerializer().serialize(sKey);
				if (connection.exists(key)) {
					DataType type = connection.type(key);
					logger.info(type.name());
					Long len = connection.lLen(key);
					logger.info(String.valueOf(len));
					List<byte[]> list = connection.lRange(key, begin, end);
					return list;
				}
				return null;
			}
		});
	}
	
	@Override
	public List<byte[]> listRangeAll(final String sKey) {
		logger.debug("LRANGE KEY:"+sKey);
		long size = listSize(sKey);
		if(size <= 0){
			return null;
		}
		return listRange(sKey, 0, size-1);
	}
}
