package com.cskaoyan.utils;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendJMail {

	public static boolean sendMail(String emailTo, String emailMsg) {

        // 邮件发送人的邮件地址
		String from = "clixin@foxmail.com";
        // 邮件接收人的邮件地址
		String to = emailTo;

        // 发件人的邮件帐户
		final String username = "clixin@foxmail.com";
        // 发件人的邮件密码（不同于登录密码，需要单独设置）
		final String password = "himptuvlvnqrbcei";

		// 定义Properties对象，设置环境信息
		Properties props = System.getProperties();

		// 设置邮件服务器的地址
        // 指定的 SMTP 服务器
		props.setProperty("mail.smtp.host", "smtp.qq.com");
		// 设置开启验证权限
		props.setProperty("mail.smtp.auth", "true");
        // 设置发送邮件使用的协议  SMTP
		props.setProperty("mail.transport.protocol", "smtp");

		// 设置 QQ 邮箱 开启 SSL 加密传输，必须
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");

		// 创建 Session 对象，session 对象表示整个邮件的环境信息
		Session session = Session.getInstance(props);

        // 设置输出调试信息
		session.setDebug(true);

		try {
			// Message 的实例对象表示一封电子邮件，支持 text/html、img/jpeg
			MimeMessage message = new MimeMessage(session);
			// 设置发件人的地址
			message.setFrom(new InternetAddress(from));
			// 设置标题
			message.setSubject("webStore用户验证激活");
			// 设置邮件的文本内容
			// 纯文本： message.setText("Welcome to JavaMail World!");
            // text/html
			message.setContent((emailMsg),"text/html;charset=utf-8");
			// 从 session 的环境中获取发送邮件的对象
			Transport transport=session.getTransport();
			// 连接邮件服务器，SMTP 邮件服务器端口为 25
			transport.connect("smtp.qq.com",25, username, password);
			// 设置收件人地址,并发送消息
			transport.sendMessage(message,new Address[]{new InternetAddress(to)});
			transport.close();
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}

}
