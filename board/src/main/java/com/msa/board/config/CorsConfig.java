//package com.msa.board.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//import java.util.List;
//
//@Configuration
//public class CorsConfig {
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//
//        config.setAllowCredentials(true);
//        config.setAllowedOrigins(List.of("http://localhost:3000")); // Next.js 프론트엔드 허용
//        config.addAllowedOriginPattern("*"); // 모든 Origin 허용 (보안상 필요시 특정 Origin만 허용)
//        config.addAllowedHeader("*"); // 모든 Header 허용
//        config.addAllowedMethod("*"); // 모든 HTTP Method 허용
//        source.registerCorsConfiguration("/**", config);
//
//        return new CorsFilter(source);
//    }
//}