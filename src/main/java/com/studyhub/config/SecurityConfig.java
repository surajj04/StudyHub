package com.studyhub.config;

import com.studyhub.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private MyUserDetailsService userService;
    @Autowired
    private PasswordEncoder endEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth.requestMatchers("/", "/login", "/register", "/discussion", "/help", "/study-material", "/search", "/css/**", "/js/**", "/webjars/**").permitAll().anyRequest().authenticated())
                .rememberMe(remember -> remember.key("uniqueAndSecret").tokenValiditySeconds(7 * 24 * 60 * 60))
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/login-success", true)
                        .failureUrl("/login-error")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .permitAll())

                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/").permitAll());

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(endEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }

}
