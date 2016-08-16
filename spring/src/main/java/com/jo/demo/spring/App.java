package com.jo.demo.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App 
{
	private static final Logger logger = LoggerFactory.getLogger(App.class);

	
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        String str = "test logger";
        logger.info("测试日志：{}", str);
    }
}
