package io.github.luaprogrammer.config.security;

import io.github.luaprogrammer.config.security.jwt.JwtAuthFilter;
import io.github.luaprogrammer.config.security.jwt.JwtService;
import io.github.luaprogrammer.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private UsuarioServiceImpl usuarioService;
    @Autowired
    private JwtService jwtService;

    public SecurityConfig( @Lazy UsuarioServiceImpl usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter(jwtService, usuarioService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //desabilita a proteção csrf
                .authorizeHttpRequests().antMatchers(HttpMethod.POST, "/api/usuarios/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()//volta para a raiz do objeto
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //TODA REQUISIÇÃO TERÁ QUE PASSAR O TOKEN
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);//vai fazer nosso filtro rodar antes do filtro do spring security

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return usuarioService;
    }
}
