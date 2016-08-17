package com.jo.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jo.demo.zookeeper.TaskLockUtils;

public class ServerLauncher {

	public static void main(String[] args) {
		TaskLockUtils.init();
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
}
