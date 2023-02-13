package com._52org.gardenoftheheart.application.garden;

import com._52org.gardenoftheheart.application.garden.domain.Garden;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithAuthUserSecurityContextFactory implements WithSecurityContextFactory<WithAuthUser> {

    @Override
    public SecurityContext createSecurityContext(WithAuthUser annotation) {

        String gardenName = annotation.gardenName();

        Garden garden = Garden.builder()
                .gardenName(gardenName)
                .uuid("123")
                .build();

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(garden, "password", List.of(new SimpleGrantedAuthority("USER")));

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(token);

        return securityContext;

    }

}
