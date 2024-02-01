package dev.harsh.architect.user_service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.harsh.architect.user_service.dtos.CreateRoleRequestDto;
import dev.harsh.architect.user_service.dtos.SetUserRolesRequestDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurity {
//    @Bean
//    public SecurityFilterChain filteringCriteria(HttpSecurity httpSecurity) throws Exception {
//         httpSecurity.authorizeHttpRequests(authorize->authorize.anyRequest().permitAll());
//         httpSecurity.csrf().disable();
//         httpSecurity.cors().disable();
////         httpSecurity.authorizeHttpRequests(authorize->authorize.requestMatchers("/auth/logout").authenticated());
//         return httpSecurity.build();
//    }

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper;
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
