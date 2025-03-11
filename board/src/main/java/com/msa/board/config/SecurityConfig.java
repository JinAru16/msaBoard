package com.msa.board.config;

import com.msa.board.common.exception.CustomAuthenticationEntryPoint;
import com.msa.board.common.jwt.JwtAuthenticationFilter;
import com.msa.board.common.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final CorsFilter corsFilter; // 🔥 CORS 필터 주입 (Spring Security 6.x 이후 방식)
    private final JwtTokenProvider jwtTokenProvider; // JWT 토큰 관리
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;// 필터단에서 발생한 에러 처림.

 // ✅ 블랙리스트 Redis만 사용하도록 지정
    private final RedisTemplate<String, Object> blackListRedisTemplate;

    // ✅ 생성자에서 @Qualifier 적용 (필드에는 X)
    public SecurityConfig(
            CorsFilter corsFilter,
            JwtTokenProvider jwtTokenProvider,
            CustomAuthenticationEntryPoint authenticationEntryPoint,
            @Qualifier("blacklistRedisTemplate") RedisTemplate<String, Object> blackListRedisTemplate) {
        this.corsFilter = corsFilter;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.blackListRedisTemplate = blackListRedisTemplate;
    }


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider, blackListRedisTemplate); // ✅ 직접 생성
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF 비활성화
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class) // CORS 필터 추가
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated() // 모든 요청에 대해서 인증 필요
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // JWT 필터 추가

        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}