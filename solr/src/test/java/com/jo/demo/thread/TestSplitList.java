package com.jo.demo.thread;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

public class TestSplitList {

	private static final int MIN_SIZE = 200;
	
	private int num = 1;
	
	List<Integer> list = new ArrayList<Integer>();
	
	@Before
	public void init() {
		for(int i=1;i<=2000;i++){
			list.add(num++);
		}
	
	}

	@Test
	public void test(){
		int size = list.size();
		int split = size/MIN_SIZE;
		List<List<Integer>> doubleList = new ArrayList<List<Integer>>();
		for(int i=0;i<split;i++){
			List<Integer> tmpList = list.subList(i*MIN_SIZE, MIN_SIZE*(i+1));
			doubleList.add(tmpList);
			System.out.println("========"+i);
		}
		if(size%MIN_SIZE != 0){
			System.out.println("in");
			doubleList.add(list.subList(split*MIN_SIZE, size));
		}
		System.out.println(doubleList.size());
	}
}
