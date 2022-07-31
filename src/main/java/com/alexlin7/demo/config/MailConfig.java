package com.alexlin7.demo.config;

import com.alexlin7.demo.auth.UserIdentity;
import com.alexlin7.demo.service.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration()
@PropertySource("classpath:mail.properties")
public class MailConfig {

    @Value("${mail.host}")
    private String host;

    @Value("${mail.port:25}")
    private int port;

    @Value("${mail.enable_auth}")
    private boolean authEnable;

    @Value("${mail.enabled_starttls}")
    private boolean starttlsEnable;

    @Value("${mail.protocol}")
    private String protocol;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public MailService mailService(UserIdentity userIdentity) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", authEnable);
        props.put("mail.smtp.starttls.enable", starttlsEnable);
        props.put("mail.transport.protocol", protocol);

        return new MailService(mailSender, userIdentity);
    }
}
