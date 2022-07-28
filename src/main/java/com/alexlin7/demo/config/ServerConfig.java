package com.alexlin7.demo.config;

import com.alexlin7.demo.repository.AppUserRepository;
import com.alexlin7.demo.repository.ProductRepository;
import com.alexlin7.demo.service.AppUserService;
import com.alexlin7.demo.service.MailService;
import com.alexlin7.demo.service.ProductService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ServerConfig {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ProductService productService(ProductRepository repository, MailService mailService) {
        System.out.println("Product Service is created.");

        return new ProductService(repository, mailService);
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AppUserService appUserService(AppUserRepository repository) {
        return new AppUserService(repository);
    }

}
