package com._52org.gardenoftheheart.exception.seed;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SeedErrorResult {

    DUPLICATED_SEED_REGISTER(HttpStatus.BAD_REQUEST, "Duplicated Seed Register Request"),
    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown Exception"),
    SEED_NOT_FOUND(HttpStatus.NOT_FOUND, "Seed Not Found");

    private final HttpStatus httpStatus;

    private final String message;

}
