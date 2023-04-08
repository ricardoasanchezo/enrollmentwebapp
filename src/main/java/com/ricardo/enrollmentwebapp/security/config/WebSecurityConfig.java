package com.ricardo.enrollmentwebapp.security.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig
{
    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authProvider;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http.csrf().disable()
                .authorizeHttpRequests()
                    .requestMatchers("/auth/**")
                    .permitAll()
                    .anyRequest()
                    //.permitAll()
                    .authenticated()
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authenticationProvider(authProvider)
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                //.formLogin()
//                    .loginPage("/auth/login")
//                    .loginProcessingUrl("/auth/authenticate")
                    //.successForwardUrl("/homepage.html")
                    //.defaultSuccessUrl("/homepage.html")
                    //.failureUrl("/login.html?error=true")
                    //.failureHandler(authenticationFailureHandler())
                    //.and()
                    //.logout()
                    //.logoutUrl("/perform_logout")
                    //.deleteCookies("JSESSIONID")
                    //.logoutSuccessHandler(logoutSuccessHandler())
                ;

        return http.build();
    }
}
