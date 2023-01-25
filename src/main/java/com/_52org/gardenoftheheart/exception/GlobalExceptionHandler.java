package com._52org.gardenoftheheart.exception;

import com._52org.gardenoftheheart.core.jpa.common.ApiResponse;
import com._52org.gardenoftheheart.exception.seed.SeedErrorResult;
import com._52org.gardenoftheheart.exception.seed.SeedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request
    ) {

        final List<String> errorList = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        log.warn("Invalid DTO Parameter Errors : {}", errorList);

        return this.makeErrorResponseEntity(errorList.toString());

    }

    private ResponseEntity<Object> makeErrorResponseEntity(final String errorDescription) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail(errorDescription));

    }

    @ExceptionHandler({SeedException.class})
    public ResponseEntity<ApiResponse> handleRestApiException(final SeedException exception) {

        log.warn("SeedException Occur: ", exception);

        return this.makeErrorResponseEntity(exception.getErrorResult());

    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiResponse> handleException(final Exception exception) {

        log.warn("Exception Occur: ", exception);

        return this.makeErrorResponseEntity(SeedErrorResult.UNKNOWN_EXCEPTION);

    }

    private ResponseEntity<ApiResponse> makeErrorResponseEntity(final SeedErrorResult errorResult) {

        return ResponseEntity.status(errorResult.getHttpStatus()).body(ApiResponse.fail(errorResult.getMessage()));

    }

}
