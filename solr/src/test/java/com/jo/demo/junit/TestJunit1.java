package com.jo.demo.junit;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestJunit1 {

	String message = "Robert";
	MessageUtil messageUtil = new MessageUtil(message);

	@Test(timeout=1000,expected = InterruptedException.class)
	public void testPrintMessage() throws InterruptedException {
		System.out.println("Inside testPrintMessage()");
		assertEquals(message, messageUtil.printMessage());
		Thread.sleep(2000);
		throw new InterruptedException("lalal");
	}
	
	@Test
	public void testPrintNrr(){
		System.err.println("Errrrrrrr");
	}

}
