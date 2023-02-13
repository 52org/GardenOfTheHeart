package com._52org.gardenoftheheart.application.garden.error;

import com._52org.gardenoftheheart.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GardenErrorCode implements ErrorCode {

    DUPLICATED_GARDENNAME(HttpStatus.CONFLICT, "Garden name is duplicated"),
    NON_EXISTENT_GARDEN(HttpStatus.NOT_FOUND, "Garden does not exist"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Invalid password"),
    ;

    private final HttpStatus httpStatus;

    private final String message;

}
