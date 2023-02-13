package com._52org.gardenoftheheart.application.garden.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class TokenDTO {

    private final String grantType;

    private final String accessToken;

//    private final String refreshToken;

}
