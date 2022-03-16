package com.portfolio.blog.util;

import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage.RecipientType;

public class SMTP {

	public static void sendMail() {
		createMail();
		setAddress();
		sending();
	}
	
	public static Message message = null;
	public static void createMail() {
		try {
			// 메일 제목 넣기
			message.setSubject("테스트메일입니다");
			// 메일 본문을 넣기
			message.setContent("아이디 테스트입니다", "text/html");
			// 보내는 날짜
			message.setSentDate(new Date());
			// 보내는 메일 주소
			message.setFrom(new InternetAddress("lwh@koreapetroleum.com"));
			// 받는 메일 주소
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setAddress() {
		try {
			InternetAddress[] receive_address = new InternetAddress[1];
			receive_address[0] = new InternetAddress("lwh@koreapetroleum.com");
			message.setRecipients(RecipientType.TO, receive_address);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void sending() {
		try {
			Transport.send(message);
			System.out.println("lwh@koreapetroleum.com 으로 메일전송 성공 !!");
		} catch (MessagingException e) {
			System.out.println("메일전송 실패 !!");
			e.printStackTrace();
		}
	}
	
}
