package com._52org.gardenoftheheart.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
class ErrorResponse {

    private final String code;

    private final String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ValidationError> errors;

    static ErrorResponse of(final String code, final String message) {

        return of(code, message, Collections.emptyList());

    }

    static ErrorResponse of(final String code, final String message, List<ValidationError> errors) {

        return ErrorResponse.builder()
                .code(code)
                .message(message)
                .errors(errors)
                .build();

    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class ValidationError {

        private final String field;
        private final String message;

        public static ValidationError of(final FieldError fieldError) {

            return ValidationError.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();

        }

    }

}
