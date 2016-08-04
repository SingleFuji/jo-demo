package com.jo.demo.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sl4jHelloWorld {
  public static void main(String[] args) {
    Logger logger = LoggerFactory.getLogger(Sl4jHelloWorld.class);
    logger.info("Hello World");
  }
}
