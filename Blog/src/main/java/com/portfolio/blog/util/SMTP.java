package com.portfolio.blog.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class SMTP {

	public static void sendIdMail(String mail, String id) {
		connectSMTP();
		createMail("아이디는 " + id + " 입니다");
		setAddress(mail);
		sending();
	}
	
	public static void sendPasswordMail(String mail, String password) {
		connectSMTP();
		createMail("비밀번호는 " + password + " 입니다");
		setAddress(mail);
		sending();
	}

	private static Message message = null;

	private static void connectSMTP() {
		Properties prop = new Properties();

		// 사내 메일 망일 경우 smtp host 만 설정해도 됨 (특정 포트가 아닐경우)
		prop.put("mail.smtp.host", "203.224.22.2");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.port", "25");
		prop.put("mail.smtp.password", "kopeco6972");
		prop.put("mail.smtp.ssl.trust", "203.224.22.2");

		Authenticator auth = new MyAuth();
		Session session = Session.getInstance(prop, auth);

		try {
			message = new MimeMessage(session);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createMail(String text) {
		try {
			// 메일 제목 넣기
			message.setSubject("테스트메일입니다");
			// 메일 본문을 넣기
			message.setText(text);
			// 보내는 날짜
			message.setSentDate(new Date());
			// 보내는 메일 주소
			message.setFrom(new InternetAddress("lwh@koreapetroleum.com"));
			// 받는 메일 주소
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void setAddress(String mail) {
		try {
			InternetAddress[] receive_address = new InternetAddress[1];
			receive_address[0] = new InternetAddress(mail);
			message.setRecipients(RecipientType.TO, receive_address);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private static void sending() {
		try {
			Transport.send(message);
		} catch (MessagingException e) {
			System.out.println("메일전송 실패");
			e.printStackTrace();
		}
	}

}

class MyAuth extends Authenticator {
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication("정보운영팀 테스트메일", "qwer4321");
	}
}
