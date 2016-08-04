package com.jo.demo.redis;

import java.util.List;

/**
 * redis处理类
 * 
 * @author wujun
 *
 */
public interface IRedisListService {
	
	/**
	 * 获取list的长度
	 * 
	 * @param sKey
	 * @return
	 */
	public long listSize(final String sKey);
	
	
	/**
	 * 
	 * @param sKey
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<byte[]> listRange(final String sKey, final long begin, final long end);
	
	
	/**
	 * 获取整个list中的数据
	 * 
	 * @param sKey
	 * @return
	 */
	public List<byte[]> listRangeAll(final String sKey);
}
