package com.alexlin7.demo.config;

import com.alexlin7.demo.repository.ProductRepository;
import com.alexlin7.demo.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {

    @Bean
    public ProductService productService(ProductRepository repository) {
        return new ProductService(repository);
    }
}
