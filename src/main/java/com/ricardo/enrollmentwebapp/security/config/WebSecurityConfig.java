package com.ricardo.enrollmentwebapp.security.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig
{
    private final UserDetailsService userDetailsService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        String[] staticResources = {
                "/style/**",
                "/images/**",
                "/script/**",};

        http.csrf().disable()
                .authorizeHttpRequests()
                    .requestMatchers("/auth/**", "/usertest", "/homepage", "/login")
                    .permitAll()
                    .requestMatchers(staticResources)
                    .permitAll()
                    .anyRequest()
                    //.permitAll()
                    .authenticated()
                    .and()
                .formLogin()
                    .loginPage("/auth/login")
                    .loginProcessingUrl("/login")
                    //.successForwardUrl("/homepage.html")
                    .defaultSuccessUrl("/homepage", true)
                    //.failureUrl("/login.html?error=true")
                    //.failureHandler(authenticationFailureHandler())
                    .and()
                    .logout()
                    //.logoutUrl("/perform_logout")
                    .deleteCookies("JSESSIONID")
                    //.logoutSuccessHandler(logoutSuccessHandler())
                ;

        return http.build();
    }
}
