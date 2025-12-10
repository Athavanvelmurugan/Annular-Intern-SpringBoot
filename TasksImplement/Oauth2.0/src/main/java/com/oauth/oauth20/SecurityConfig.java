package com.oauth.oauth20;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    //https://github.com/login/oauth/select_account?client_id=Ov23liNy8oO6SA6xhrlk&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Flogin%2Foauth2%2Fcode%2Fgithub&response_type=code&scope=read%3Auser+user%3Aemail&state=rfp7JZIk8TLcjjBwZlAVOMHl92JoPAxsqJcdCbwf0EE%3D

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
       http.csrf(AbstractHttpConfigurer::disable)
               .authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
            //   .formLogin(form->form.defaultSuccessUrl("/hello",true));
               .oauth2Login(oauth2->oauth2
                       .defaultSuccessUrl("/hello", true))
               .formLogin(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
