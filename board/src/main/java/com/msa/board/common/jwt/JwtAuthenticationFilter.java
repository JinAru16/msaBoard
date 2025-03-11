package com.msa.board.common.jwt;

import com.msa.board.common.exception.UserException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Qualifier("blacklistRedisTemplate")  // ✅ 블랙리스트 Redis만 사용하도록 지정
    private final RedisTemplate<String, Object> blackListRedisTemplate;

    // ✅ 생성자에서 @Qualifier 적용
    public JwtAuthenticationFilter(
            JwtTokenProvider jwtTokenProvider,
            @Qualifier("blacklistRedisTemplate") RedisTemplate<String, Object> blackListRedisTemplate) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.blackListRedisTemplate = blackListRedisTemplate;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = getTokenFromCookie(request);


        if(token == null && blackListRedisTemplate.hasKey(token)){
            throw new UserException("로그아웃한 사용자입니다.");
        }
        if (token != null && jwtTokenProvider.validateToken(token)) { // JWT 검증
            Authentication auth = jwtTokenProvider.getAuthentication(token); // SecurityContext에 인증 정보 저장
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private String getTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        for (Cookie cookie : request.getCookies()) {
            if ("jwt".equals(cookie.getName())) { // 쿠키에서 "jwt" 토큰 찾기
                return cookie.getValue();
            }
        }
        return null;
    }
}
