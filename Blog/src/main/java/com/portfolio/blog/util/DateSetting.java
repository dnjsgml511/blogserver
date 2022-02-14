package com.portfolio.blog.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateSetting {
	public String now() {
		Date today = new Date();
		Locale currentLocale = new Locale("KOREAN", "KOREA");
		String pattern = "yyyy-MM-dd HH:mm:ss"; //hhmmss로 시간,분,초만 뽑기도 가능
		SimpleDateFormat formatter = new SimpleDateFormat(pattern,
				currentLocale);
		return formatter.format(today);
	}
}
