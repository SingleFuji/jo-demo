package com.jo.demo.redis;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import com.jo.demo.common.Constants;
import com.jo.demo.utils.JsonUtils;

/**
 * redis处理类
 * 
 * @author wujun
 *
 */
public class RedisStringService implements IRedisStringService {

	private static final Logger logger = LoggerFactory.getLogger(RedisStringService.class);

	private RedisTemplate<Serializable, Serializable> redisTemplate;

	public RedisTemplate<Serializable, Serializable> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<Serializable, Serializable> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void putString(final String sessionId, final String body) {
		logger.debug("putString data:" + body);
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = redisTemplate.getStringSerializer().serialize(sessionId);
				connection.set(key, redisTemplate.getStringSerializer().serialize(body));
				// connection.expire(key, Constants.DEFAULT_CACHE_TIME_OUT);
				return null;
			}
		});
	}

	@Override
	public String getString(final String sessionId) {
		logger.debug("getString sessionid:" + sessionId);
		return redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = redisTemplate.getStringSerializer().serialize(sessionId);
				if (connection.exists(key)) {
					byte[] value = connection.get(key);
					String body = redisTemplate.getStringSerializer().deserialize(value);
					return body;
				}
				return null;
			}
		});
	}

	@Override
	public void deleteString(final String sessionId) {
		logger.debug("deleteString sessionid:" + sessionId);
		redisTemplate.execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection) {
				connection.del(redisTemplate.getStringSerializer().serialize(sessionId));
				return null;
			}
		});
	}

	@Override
	public void putString(final String sessionId, final String body, final long seconds) {
		logger.debug("putString data:" + body);
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = redisTemplate.getStringSerializer().serialize(sessionId);
				connection.set(key, redisTemplate.getStringSerializer().serialize(body));
				if (seconds > 0) {
					connection.expire(key, seconds);
				} else {
					connection.expire(key, Constants.DEFAULT_CACHE_TIME_OUT);
				}
				return null;
			}
		});
	}

	@Override
	public boolean setNX(final String sessionId, final String body, final long seconds) {
		logger.debug("setNX data:" + body);
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = redisTemplate.getStringSerializer().serialize(sessionId);

				byte[] value = redisTemplate.getStringSerializer().serialize(body);
				boolean bool = connection.setNX(key, value);
				if (bool) {
					// 如果设置成功了，设置过期时间
					connection.expire(key, seconds > 0 ? seconds : Constants.DEFAULT_CACHE_TIME_OUT);
				}
				return bool;
			}
		});
	}

	@Override
	public long incr(final String sessionId, final long seconds) {
		logger.debug("incr data:" + sessionId);
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = redisTemplate.getStringSerializer().serialize(sessionId);
				Long seq = connection.incr(key);
				if (seq != null && seq > 0) {
					// 如果设置成功了，设置过期时间
					connection.expire(key, seconds > 0 ? seconds : Constants.DEFAULT_CACHE_TIME_OUT);
				}
				return seq;
			}
		});
	}

	@Override
	public long incr(final String sessionId) {
		logger.debug("incr data:" + sessionId);
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = redisTemplate.getStringSerializer().serialize(sessionId);
				Long seq = connection.incr(key);
				return seq;
			}
		});
	}

	public long lpush(final String sKey, Object oValue) {
		final String jsonValue = JsonUtils.toJson(oValue);
		logger.debug("push data:" + oValue.toString() + " into" + sKey);
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = redisTemplate.getStringSerializer().serialize(sKey);
				byte[] value = redisTemplate.getStringSerializer().serialize(jsonValue);
				Long seq = connection.lPush(key, value);
				return seq;
			}
		});
	}

	/**
	 * 
	 * @param sKey
	 * @return
	 */
	public long listSize(final String sKey) {
		logger.debug("LISTSIZE KEY:" + sKey);
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

}
