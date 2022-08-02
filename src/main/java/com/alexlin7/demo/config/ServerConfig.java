package com.alexlin7.demo.config;

import com.alexlin7.demo.auth.UserIdentity;
import com.alexlin7.demo.repository.AppUserRepository;
import com.alexlin7.demo.repository.ProductRepository;
import com.alexlin7.demo.service.AppUserService;
import com.alexlin7.demo.service.ProductService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ServerConfig {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ProductService productService(ProductRepository repository, UserIdentity userIdentity) {
        return new ProductService(repository, userIdentity);
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AppUserService appUserService(AppUserRepository repository) {
        return new AppUserService(repository);
    }

}
