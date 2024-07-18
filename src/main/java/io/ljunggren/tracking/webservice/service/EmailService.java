package io.ljunggren.tracking.webservice.service;

import java.nio.file.Path;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    
    public void sendEmail(String to, String subject, String body) throws MessagingException {
        sendEmail(to, subject, body, null);
    }
    
    public void sendEmail(String to, String subject, String body, Path attachmentPath) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("tracking@ljunggren.io");
        message.setSubject(subject);
        message.setText(body);
        if (attachmentPath == null) {
            mailSender.send(message);
            return;
        }
        sendEmail(message, attachmentPath);
    }
    
    private void sendEmail(SimpleMailMessage message, Path attachmentPath) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        FileSystemResource file = new FileSystemResource(attachmentPath);
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(message.getFrom());
        helper.setTo(message.getTo());
        helper.setSubject(message.getSubject());
        helper.setText(String.format(message.getText()));
        helper.addAttachment(file.getFilename(), file);
        mailSender.send(mimeMessage);
    }
    
}
