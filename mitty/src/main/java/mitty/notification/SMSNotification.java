package mitty.notification;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//sms notifiation.
public class SMSNotification {
	public static void notify(String toAddress, String whatmessage) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", true);

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("mitty.talks", "mitty2323");
			}
		});

		try {

			Message message = new MimeMessage(session);
			// message.setFrom(new
			// InternetAddress("vengateswaran.c@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
			// message.setSubject("Testing Subject");
			message.setText(whatmessage);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}