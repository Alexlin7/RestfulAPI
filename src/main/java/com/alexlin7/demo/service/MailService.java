package com.alexlin7.demo.service;

import com.alexlin7.demo.auth.UserIdentity;
import com.alexlin7.demo.entity.mail.SendMailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MailService {

    private final UserIdentity userIdentity;

    private final JavaMailSenderImpl mailSender;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public MailService(JavaMailSenderImpl mailSender, UserIdentity userIdentity) {
        this.mailSender = mailSender;
        this.userIdentity = userIdentity;
    }

    public void sendMail(SendMailRequest request) {
        sendMail(request.getSubject(), request.getContent(), request.getReceivers());
    }

    public void sendMail(String subject, String content, List<String> receivers) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(Objects.requireNonNull(mailSender.getUsername()));
        message.setTo(receivers.toArray(new String[0]));
        message.setSubject(subject);
        message.setText(content);

        try {
            mailSender.send(message);
        } catch (MailAuthenticationException e) {
            LOGGER.error(e.getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
    }

    public void sendNewProductMail(String id) {
        String message = String.format("Hi, %s. There is a new created product (%s)", userIdentity.getName(), id);
        sendMail("New Product", message,
                Collections.singletonList(userIdentity.getEmail()));
    }

    public void sendDeleteProductMail(String productId) {
        String content = String.format("Hi, %s. There's a product deleted (%s).", userIdentity.getName(), productId);
        sendMail("Product Deleted", content,
                Collections.singletonList(userIdentity.getEmail()));
    }

}
