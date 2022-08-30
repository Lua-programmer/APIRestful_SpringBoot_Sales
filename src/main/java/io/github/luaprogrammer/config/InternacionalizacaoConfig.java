package io.github.luaprogrammer.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;

@Configuration
public class InternacionalizacaoConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");

        messageSource.setDefaultEncoding("ISO-8859-1"); //essa codificação permite reconhecer nosso acentos
        messageSource.setDefaultLocale(Locale.getDefault());

        return messageSource;
    }

    //resposnsavel por fazer a interpolação entre o messagesource e o messagesproperties
    public LocalValidatorFactoryBean validatorFactoryBean() {
        LocalValidatorFactoryBean  bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}
