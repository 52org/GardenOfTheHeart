package com._52org.gardenoftheheart.application.garden.service;

import com._52org.gardenoftheheart.application.garden.domain.Garden;
import com._52org.gardenoftheheart.application.garden.dto.GardenLoginResponseDTO;
import com._52org.gardenoftheheart.application.garden.dto.GardenRequestDTO;
import com._52org.gardenoftheheart.application.garden.dto.GardenSignUpResponseDTO;
import com._52org.gardenoftheheart.application.garden.dto.TokenDTO;
import com._52org.gardenoftheheart.application.garden.error.GardenErrorCode;
import com._52org.gardenoftheheart.application.garden.error.GardenException;
import com._52org.gardenoftheheart.application.garden.repository.GardenRepository;
import com._52org.gardenoftheheart.security.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GardenService {

    private final GardenRepository gardenRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final JwtProvider jwtProvider;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiredTimeMs;

    @Transactional
    public GardenSignUpResponseDTO createGarden(final GardenRequestDTO gardenRequestDTO) {

        gardenRepository.findByGardenName(gardenRequestDTO.getGardenName())
                .ifPresent(e -> {
                    throw new GardenException(GardenErrorCode.DUPLICATED_GARDENNAME);
                });

        final Garden garden = gardenRepository.save(
                Garden.builder()
                        .gardenName(gardenRequestDTO.getGardenName())
                        .password(passwordEncoder.encode(gardenRequestDTO.getPassword()))
                        .uuid(UUID.randomUUID().toString())
                        .roles(new ArrayList<>(Collections.singleton("USER")))
                        .build()
        );

        return GardenSignUpResponseDTO.toDTO(garden);

    }

    @Transactional
    public GardenLoginResponseDTO login(final GardenRequestDTO gardenRequestDTO) {

        // 1. ID, PW 기반 Authentication 객체 생성
        // authentication 은 인증 여부를 확인하는 authenticated 이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(gardenRequestDTO.getGardenName(), gardenRequestDTO.getPassword());

        // 2. 실제 검증 (비밀번호 체크)
        // authenticate 메소드가 실행될 때 CustomUserDetailsService 에서 만든 loaduserByUsername 메소드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 생성
        TokenDTO tokenDTO = jwtProvider.createToken(authentication);

        String uuid = gardenRepository.findByGardenName(gardenRequestDTO.getGardenName())
                .orElseThrow(() -> new GardenException(GardenErrorCode.NON_EXISTENT_GARDEN))
                .getUuid();

        return GardenLoginResponseDTO.builder()
                .tokenDTO(tokenDTO)
                .uuid(uuid)
                .build();

    }

}
