package com._52org.gardenoftheheart.security.util;

import com._52org.gardenoftheheart.application.garden.dto.TokenDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtProvider {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_KEY = "auth";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final long EXPIRATION_TIME = 60 * 60 * 1000L;
    private final Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // Base64 로 구성된 키값
    public JwtProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // 토큰 생성
    public TokenDTO createToken(Authentication authentication) {

        // 권한을 하나의 String 으로 합침
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // 토큰 생성 시간
        Date tokenExpirationTime = new Date(new Date().getTime() + EXPIRATION_TIME);

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORIZATION_KEY, authorities)
                .setExpiration(tokenExpirationTime)
                .signWith(key, signatureAlgorithm)
                .compact();

        String refreshToken = Jwts.builder()
                .setExpiration(tokenExpirationTime)
                .signWith(key, signatureAlgorithm)
                .compact();

        return TokenDTO.builder()
                .grantType(BEARER_PREFIX)
                .accessToken(accessToken)
//                .refreshToken(refreshToken)
                .build();

    }

    public Authentication getAuthentication(String token) throws ExpiredJwtException {

        // 토큰 복호화
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

        if (claims.get(AUTHORIZATION_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORIZATION_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);

    }

    // 토큰 검증
    public boolean validateToken(String token) {

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 가 잘못되었습니다.");
        }

        return false;

    }

}
