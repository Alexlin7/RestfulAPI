package com.alexlin7.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

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

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public boolean isAuthEnable() {
        return authEnable;
    }

    public boolean isStarttlsEnable() {
        return starttlsEnable;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
