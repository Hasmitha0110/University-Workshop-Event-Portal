package com.ruhuna.uniplus.configuration;

import com.ruhuna.uniplus.security.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Clock;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtRequestFilter jwtRequestFilter) throws Exception {
        http
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/admins").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/events/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/event-updates/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/events/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/events/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/events/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/event-updates/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/event-updates/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/event-updates/**").authenticated()
                        .requestMatchers("/api/admins/**").authenticated()
                        .requestMatchers("/", "/index.html").permitAll()
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
                        .allowCredentials(true);

            }
        };
    }

}
