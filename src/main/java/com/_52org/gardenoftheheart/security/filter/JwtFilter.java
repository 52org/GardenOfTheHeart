package com._52org.gardenoftheheart.security.filter;

import com._52org.gardenoftheheart.security.util.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtProvider jwtProvider;

    public JwtFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. Request Header 에서 JWT 추출
        String jwt = resolveToken(request);

        // 2. 토큰 유효성 검사
        if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)) {
            // 토큰에서 userName, 권한을 뽑아 Spring Security 유저를 만들어 Authentication 반환
            Authentication authentication = jwtProvider.getAuthentication(jwt);

            // 해당 Spring Security 유저를 Security Context 에 저장 (DB 거치지 않음)
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);

    }

    // 헤더에서 토큰 정보 꺼내기
    private String resolveToken(HttpServletRequest request) {

        String token = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }

        return null;

    }

}
