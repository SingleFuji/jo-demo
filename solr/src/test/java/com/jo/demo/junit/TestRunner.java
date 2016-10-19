package com.jo.demo.junit;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {

	 public static void main(String[] args) {
		 testPrimeNumberCheckerTest();
	   }
	 
	 public static void testPrimeNumberCheckerTest(){
		 Result result = JUnitCore.runClasses(PrimeNumberCheckerTest.class);

	      for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
			
	      System.out.println(result.wasSuccessful());
	 }
	 
	 public static void testJunitTestSuite(){
		 Result result = JUnitCore.runClasses(JunitTestSuite.class);

	      for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
			
	      System.out.println(result.wasSuccessful());
	 }
}
