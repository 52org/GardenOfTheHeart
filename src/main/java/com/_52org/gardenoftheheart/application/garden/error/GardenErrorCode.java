package com._52org.gardenoftheheart.application.garden.error;

import com._52org.gardenoftheheart.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GardenErrorCode implements ErrorCode {

    DUPLICATED_GARDENNAME(HttpStatus.BAD_REQUEST, "Garden name is duplicated"),
    NOT_EXIST_GARDEN(HttpStatus.NOT_FOUND, "Garden does not exist");

    private final HttpStatus httpStatus;

    private final String message;

}
