package com.jo.demo.codec;

import org.apache.commons.codec.digest.DigestUtils;

//import org.springframework.data.redis.core.script.DigestUtils;

public class EncUtils {

	public static void main(String[] args) {
//		String sha1 = DigestUtils.sha1DigestAsHex("rrhh");
		String sha1 = DigestUtils.sha1Hex("rrhh");
		System.out.println(sha1);
	}
}
