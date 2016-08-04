package com.jo.demo.redis;

import java.util.ArrayList;
import java.util.List;

import com.jo.demo.utils.JsonUtils;
/**
 * 
 * @author zhouhang
 *
 */
public class TransRedisObjUtils {


	public static<T> List<T> trans(List<byte[]> src, Class<T> classes){
		return trans(src, classes, null);
	}
	
	/**
	 * 批量转换JSON对象
	 * 
	 * @param src
	 * @param classes
	 * @param code
	 * @return
	 */
	public static<T> List<T> trans(List<byte[]> src, Class<T> classes, String code){
		if(null == src || src.size()==0){
			return null;
		}
		if(null == code){
			code = "UTF-8";
		}
		List<T> list = new ArrayList<T>();
		for(byte[] arrs:src){
			String temp = new String(arrs);
			T tempObj = JsonUtils.fromJson(temp, classes);
			list.add(tempObj);
		}
		return list;
	}
}
