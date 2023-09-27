package com.ricardo.enrollmentwebapp.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

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
                .csrf().disable()
                .authorizeHttpRequests()
                    .requestMatchers("/auth/**", "/homepage", "/login")
                    .permitAll()
                    .requestMatchers(staticResources)
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
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
