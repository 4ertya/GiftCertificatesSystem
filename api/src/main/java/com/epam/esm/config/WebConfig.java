package com.epam.esm.config;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Configuration
@ComponentScan("com.epam.esm")
@EnableWebMvc
@EnableTransactionManagement
@PropertySource("classpath:/message_source/configuration.properties")
public class WebConfig {
    @Value("${message_source.baseName}")
    private String baseName;

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(baseName);
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.setDefaultLocale(Locale.ENGLISH);
        messageSource.setUseCodeAsDefaultMessage(false);
        return messageSource;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public ResourceBundleMessageInterpolator interpolator() {
        return new ResourceBundleMessageInterpolator(resourceBundle());
    }

    @Bean
    public MessageSourceResourceBundleLocator resourceBundle() {
        return new MessageSourceResourceBundleLocator(messageSource());
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setMessageInterpolator(interpolator());
        return bean;
    }
}
