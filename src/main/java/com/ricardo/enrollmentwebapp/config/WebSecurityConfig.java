package com.ricardo.enrollmentwebapp.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static com.ricardo.enrollmentwebapp.user.Permission.*;
import static com.ricardo.enrollmentwebapp.user.Role.ADMIN;
import static org.springframework.http.HttpMethod.*;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig
{
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        String[] staticResources = {
                "/style/**",
                "/images/**",
                "/script/**",};

        http
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/homepage", "/login")
                        .permitAll()
                        .requestMatchers(staticResources)
                        .permitAll()

                        .requestMatchers("/admin/**").hasRole(ADMIN.name())
                        .requestMatchers(GET,"/admin/**").hasAuthority(ADMIN_READ.name())
                        .requestMatchers(POST,"/admin/**").hasAuthority(ADMIN_CREATE.name())
                        .requestMatchers(PUT,"/admin/**").hasAuthority(ADMIN_UPDATE.name())
                        .requestMatchers(DELETE,"/admin/**").hasAuthority(ADMIN_DELETE.name())

                        .anyRequest()
                        .authenticated())
                .formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/homepage", true)
                .and()
                .logout(Customizer.withDefaults())
        ;

        return http.build();
    }
}
