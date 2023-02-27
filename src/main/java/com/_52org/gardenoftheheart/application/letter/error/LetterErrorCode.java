package com._52org.gardenoftheheart.application.letter.error;

import com._52org.gardenoftheheart.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LetterErrorCode implements ErrorCode {

    NON_EXISTENT_LETTER(HttpStatus.NOT_FOUND, "Letter does not exist");

    private final HttpStatus httpStatus;

    private final String message;

}
