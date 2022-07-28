package com.alexlin7.demo.config;

import com.alexlin7.demo.auth.JWTAuthenticationFilter;
import com.alexlin7.demo.auth.JWTServer;
import com.alexlin7.demo.filter.LogApiFilter;
import com.alexlin7.demo.filter.LogProcessTimeFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<LogApiFilter> logApiFilter() {
        FilterRegistrationBean<LogApiFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new LogApiFilter());
        bean.addUrlPatterns("/*");
        bean.setName("logApiFilter");
        bean.setOrder(0);

        return bean;
    }

    @Bean
    public FilterRegistrationBean<LogProcessTimeFilter> logProcessTimeFilter() {
        FilterRegistrationBean<LogProcessTimeFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new LogProcessTimeFilter());
        bean.addUrlPatterns("/*");
        bean.setName("logProcessTimeFilter");
        bean.setOrder(1);

        return bean;
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(JWTServer jwtServer,
                                                           UserDetailsService userDetailsService) {
        return new JWTAuthenticationFilter(jwtServer, userDetailsService);
    }
}
