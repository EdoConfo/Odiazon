package it.uniroma3.Odiazon.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disabilita la protezione CSRF (la riattiveremo quando avremo i form di login)
            .csrf(csrf -> csrf.disable())
            // Autorizza tutte le richieste HTTP
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/uploads/**", "/images/**", "/css/**", "/js/**").permitAll() // Permetti risorse statiche
                .anyRequest().permitAll() // Permetti tutto il resto per ora
            );
        return http.build();
    }
}