package com.jo.demo.spring.utils;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Gson 工具类
 * 
 * @author mango_jo
 *
 */
public class GsonUtils {

	public static final Gson gson = new Gson();
	
	public static final Gson gsonNull = new GsonBuilder().serializeNulls().create();
	
	public static String toJson(Object src){
		return gson.toJson(src);
	}
	
	public static String toJsonNull(Object src){
		return gsonNull.toJson(src);
	}
	
	public static<T> T fromJson(String json, Class<T> classOfT){
		return gson.fromJson(json, classOfT);
	}
	
	public static<T> T  fromJson(String json, Type typeOfT){
		return gson.fromJson(json, typeOfT);
	}
	
}
