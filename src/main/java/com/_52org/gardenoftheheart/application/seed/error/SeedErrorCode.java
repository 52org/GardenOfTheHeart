package com._52org.gardenoftheheart.application.seed.error;

import com._52org.gardenoftheheart.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SeedErrorCode implements ErrorCode {

    DUPLICATED_PLANTNAME(HttpStatus.CONFLICT, "Plant name is duplicated"),
    NON_EXISTENT_SEED(HttpStatus.NOT_FOUND, "Seed does not exist");

    private final HttpStatus httpStatus;

    private final String message;

}
