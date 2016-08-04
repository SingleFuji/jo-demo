package com.jo.demo.logger.log4j;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Bar {
	static final Logger logger = LogManager.getLogger(Bar.class.getName());

	@SuppressWarnings("deprecation")
	public boolean doIt() {
		logger.entry();
		logger.error("Did it again!");
		return logger.exit(false);
	}
}
