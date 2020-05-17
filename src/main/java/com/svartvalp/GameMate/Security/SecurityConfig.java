package com.svartvalp.GameMate.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    private JWTAuthenticationConverter authenticationConverter;

    @Autowired
    public void setAuthenticationConverter(JWTAuthenticationConverter authenticationConverter) {
        this.authenticationConverter = authenticationConverter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        var authFilter = new AuthenticationWebFilter((ReactiveAuthenticationManager) authentication -> Mono.just(authentication));
        authFilter.setServerAuthenticationConverter(authenticationConverter);
        return http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/user/verify", "/user/register", "/user/home").permitAll()
                .anyExchange().authenticated()
                .and()
                .addFilterAt(authFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(4);
    }
}
