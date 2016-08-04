package com.jo.demo.redis;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 
 * @author zhouhang
 *
 */
public class RedisListTemplateService implements IRedisListService {

	private static final Logger logger = LoggerFactory.getLogger(RedisListTemplateService.class);

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
		logger.debug("LISTSIZE KEY:" + sKey);
		long size = redisTemplate.opsForList().size(sKey);
		System.out.println(size);
		
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = redisTemplate.getStringSerializer().serialize(sKey);

				if (connection.exists(key)) {
					long listSize = redisTemplate.opsForList().size(key);
					return listSize;
				}
				return 0L;
			}
		});
	}
	
	@Override
	public List<byte[]> listRange(String sKey, long begin, long end) {
		logger.debug("LRANGE KEY:"+sKey +" BEGIN:"+ begin +" END:"+ end);
		byte[] key = redisTemplate.getStringSerializer().serialize(sKey);
//		return redisTemplate.opsForList().range(key, begin, end);
		return null;
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
