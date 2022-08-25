package io.github.luaprogrammer.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@Development
public class HomeConfig {
    //@Bean(name = "applicationName")
    //public String applicationName() {
    //    return "Sistema de Vendas";
    //}

    @Bean
    public CommandLineRunner executar() {
        return args -> {
            System.out.println("RODANDO A CONFIGURAÇÃO DE DESENVOLVIMENTO");
        };
    }
}