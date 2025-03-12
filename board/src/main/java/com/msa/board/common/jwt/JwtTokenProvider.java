package com.msa.board.common.jwt;

import com.msa.board.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtConfig jwtConfig;

    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public String getRole(String token) {
        return getClaims(token).get("role", String.class);
    }
    public Date getExpirationTime(String token) {
        return getClaims(token).getExpiration();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtConfig.getSecretKey()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ JWT에서 Claims(페이로드) 추출하는 메서드
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtConfig.getSecretKey())  // 서명 검증을 위한 키 설정
                .build()
                .parseClaimsJws(token)  // JWT 파싱 및 검증
                .getBody();  // Claims (페이로드) 반환
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtConfig.getSecretKey()).parseClaimsJws(token).getBody();
        String username = claims.getSubject();
        String role = claims.get("role", String.class);

        // Board 서버에서는 UserDetailsService 없이 직접 UserDetails 생성
        CustomUserDetails userDetails = new CustomUserDetails(username, role);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}