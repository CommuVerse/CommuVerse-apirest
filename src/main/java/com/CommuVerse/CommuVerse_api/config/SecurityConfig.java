package com.CommuVerse.CommuVerse_api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter; 

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Deshabilita CSRF (si no es necesario para tu aplicación)
            .authorizeHttpRequests(authorize -> 
                authorize
                    // Permitir acceso sin autenticación a los recursos de Swagger y OpenAPI
                    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                    
                    // Permitir acceso sin autenticación a los endpoints de usuarios y suscripciones
                    .requestMatchers("/users/**", "/subscriptions/**").permitAll()
                    .requestMatchers("/mail/**").permitAll()
                    // Cualquier otro endpoint debe ser autenticado
                    .anyRequest().authenticated()
            )
            // Añadir el filtro JWT para autenticar las solicitudes donde sea necesario
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Bean necesario para codificar contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
