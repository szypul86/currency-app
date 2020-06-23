package com.szypulski.currencyapp.service.mail;

import com.szypulski.currencyapp.configuration.MailPropertiesConfig;
import com.szypulski.currencyapp.service.GoogleAuthenticationService;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

@Service
public class MailService {

  private final GoogleAuthenticationService authService;
  private final MailPropertiesConfig mailPropertiesConfig;

  public MailService(GoogleAuthenticationService authService,
      MailPropertiesConfig mailPropertiesConfig) {
    this.authService = authService;
    this.mailPropertiesConfig = mailPropertiesConfig;
  }

  public void send(String to, String subject, MailMessageBuilder mailMessageBuilder)
      throws MessagingException {

    String accessToken = authService.obtainAccessToken();

    Properties props = new Properties();
    props.put("mail.smtp.ssl.enable", mailPropertiesConfig.getSsl().isEnable());
    props.put("mail.smtp.sasl.enable", mailPropertiesConfig.getSasl().isEnable());
    props.put("mail.smtp.sasl.mechanisms", mailPropertiesConfig.getSasl().getMechanisms());
    props
        .put("mail.smtp.auth.login.disable", mailPropertiesConfig.getAuth().getLogin().isDisable());
    props
        .put("mail.smtp.auth.plain.disable", mailPropertiesConfig.getAuth().getPlain().isDisable());

    Session session = Session.getInstance(props);

    Message mimeMessage = new MimeMessage(session);
    mimeMessage.setFrom(new InternetAddress(mailPropertiesConfig.getMailFrom()));
    mimeMessage.addRecipient(RecipientType.TO, new InternetAddress(to));
    mimeMessage.setSubject(subject);
    mimeMessage.setSentDate(new Date());
    mimeMessage
        .setContent(mailMessageBuilder.buildMessage(), "text/html; charset=utf-8");
    mimeMessage.saveChanges();

    Transport transport = session.getTransport("smtp");
    transport.connect("smtp.gmail.com", mailPropertiesConfig.getMailFrom(), accessToken);
    transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
  }
}
