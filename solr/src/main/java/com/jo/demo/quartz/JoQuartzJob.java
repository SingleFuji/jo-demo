package com.jo.demo.quartz;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JoQuartzJob {

	private static final Logger logger = LoggerFactory.getLogger(JoQuartzJob.class);
	
	public void execute() {
		System.out.println("Hello:" + new DateTime().toString());
//		logger.info("Hello:" + new DateTime().toString());
	}

}
