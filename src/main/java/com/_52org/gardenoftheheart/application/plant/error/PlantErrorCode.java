package com._52org.gardenoftheheart.application.plant.error;

import com._52org.gardenoftheheart.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PlantErrorCode implements ErrorCode {

    DUPLICATED_PLANTNAME(HttpStatus.CONFLICT, "Plant name is duplicated"),
    NON_EXISTENT_PLANT(HttpStatus.NOT_FOUND, "Plant does not exist");

    private final HttpStatus httpStatus;

    private final String message;

}
