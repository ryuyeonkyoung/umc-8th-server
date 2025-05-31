package org.umc.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // URL별 접근 권한 설정
            .authorizeHttpRequests((requests) -> requests
                    .requestMatchers("/", "/home", "/signup", "/members/signup", "/css/**").permitAll()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
            )
                // 로그인 폼 기반 인증 설정
            .formLogin((form) -> form
                    .loginPage("/login")
                    .defaultSuccessUrl("/home", true)
                    .permitAll()
            )
                // 로그아웃 처리 경로 설정
            .logout((logout) -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
            );

        return http.build();
    }

    // BCrypt 해시 알고리즘을 사용하여 비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
