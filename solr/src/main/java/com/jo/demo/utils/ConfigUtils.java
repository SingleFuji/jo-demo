package com.jo.demo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 读取配置文件
 * @author wujun
 *
 */
public enum ConfigUtils {
	
	REDIS("prop/redis/redis.properties"),ZK("prop/zk/zk.properties"),QUARTZ("prop/quartz/quartzConfig.properties");
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigUtils.class);
	
	private Properties prop = new Properties();
	
	private String code = "";
	
	private ConfigUtils(String code) {
		this.code = code;
		loadFile(code, prop);
	}
	
	private void loadFile(String code, Properties prop){
		try {
			if(!StringUtils.isEmpty(code)) {
				code = code.replace("/", File.separator);
				String pathname = Thread.currentThread().getContextClassLoader().getResource("").getPath();
				pathname += code;
				try {
					prop.load(new FileInputStream(new File(pathname)));
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 重新加载配置
	 */
	public static void reLoads() {
		for(ConfigUtils cu : values()) {
			cu.loadFile(cu.code, cu.prop);
		}
	}
	
	public String getProperty(String key, String defaultValue) {
		return prop.getProperty(key, defaultValue);
	}
	
	public String getProperty(String key) {
		return prop.getProperty(key);
	}
	
	public <T> T getProperty(String key, String defaultValue, Class<T> clazz) {
		String str = getProperty(key, defaultValue);
		return TipUtils.StringToClass(str, clazz);
	}
	
	public <T> T getProperty(String key, Class<T> clazz) {
		String str = getProperty(key);
		return TipUtils.StringToClass(str, clazz);
	}
	
	
}
