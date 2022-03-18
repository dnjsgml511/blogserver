package com.portfolio.blog.util;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class Encryption {
	public static String encode(String password) {
		Encoder encoder = Base64.getEncoder();
		return new String(encoder.encodeToString(password.getBytes()));
	}

	public static String decode(String password) {
		Decoder decoder = Base64.getDecoder();
		return new String(decoder.decode(password));
	}
}
