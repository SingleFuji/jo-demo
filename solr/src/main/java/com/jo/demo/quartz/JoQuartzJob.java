package com.jo.demo.quartz;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jo.demo.zookeeper.TaskLockUtils;

public class JoQuartzJob {

	private static final Logger logger = LoggerFactory.getLogger(JoQuartzJob.class);
	
	public void execute() {
		
		boolean flag = TaskLockUtils.lock();
		if(!flag) {
			logger.info("no access to lock");
			return;
		}
		
//		System.out.println("Hello:" + new DateTime().toString());
		logger.info("Hello:" + new DateTime().toString());
	}

}
