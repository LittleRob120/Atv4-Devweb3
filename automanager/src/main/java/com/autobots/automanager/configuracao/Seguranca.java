package com.autobots.automanager.configuracao;

import com.autobots.automanager.filtros.AutenticadorJwt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class Seguranca {

    private final AutenticadorJwt autenticadorJwt;

    public Seguranca(AutenticadorJwt autenticadorJwt) {
        this.autenticadorJwt = autenticadorJwt;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .headers().frameOptions().sameOrigin()
            .and()
            .authorizeRequests()
            .antMatchers("/actuator/health", "/h2-console/**").permitAll()
            .antMatchers(HttpMethod.GET, "/clientes/**", "/usuarios/**").permitAll()
            .antMatchers(HttpMethod.POST, "/clientes/**").permitAll() // temporário
            .anyRequest().authenticated();
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
