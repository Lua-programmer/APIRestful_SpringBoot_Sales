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
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private  UsuarioServiceImpl usuarioService;
    private  JwtService jwtService;

    public SecurityConfig( @Lazy UsuarioServiceImpl usuarioService) {
        this.usuarioService = usuarioService;
    }

    public SecurityConfig(JwtService jwtService) {
        this.jwtService = jwtService;
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
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //TODA REQUISIÇÃO TERÁ QUE PASSAR O TOKEN
                .and()
                .authorizeHttpRequests().antMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                .anyRequest()
                .authenticated().and()//volta para a raiz do objeto
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)//vai fazer nosso filtro rodar antes do filtro do spring security
                .csrf().disable(); //desabilita a proteção csrf
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return usuarioService;
    }
}
