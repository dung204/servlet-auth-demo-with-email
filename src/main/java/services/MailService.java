package services;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public final class MailService {
  // TODO: replace with your own email, password, SMTP server, and port
  private static final String EMAIL_FROM = "your-email-from-here";
  private static final String PASSWORD = "your-password-here";
  private static final String SMTP_HOST = "your-smtp-server-here";
  private static final int PORT = 12345;

  private static final Logger logger = Logger.getLogger(MailService.class.getName());
  private static Session session = null;
  private static MailService instance = null;

  private MailService() {
    try {
      Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", SMTP_HOST);
      props.put("mail.smtp.port", PORT);

      session = Session.getInstance(props, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(EMAIL_FROM, PASSWORD);
        }
      });
      logger.log(Level.INFO, "Mail service is ready");
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage(), e);
    }
  }

  public static MailService getInstance() {
    if (instance == null) {
      instance = new MailService();
    }
    return instance;
  }

  public void sendMail(String emailTo, String subject, String body) {
    try {
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(EMAIL_FROM));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
      message.setSubject(subject);
      message.setContent(body, "text/html");
      Transport.send(message);
      logger.log(Level.INFO, "Mail sent to " + emailTo);
    } catch (MessagingException ex) {
      logger.log(Level.SEVERE, ex.getMessage(), ex);
    }
  }
}