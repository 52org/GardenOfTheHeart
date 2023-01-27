package com._52org.gardenoftheheart.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
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

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handleBaseException(final BaseException e) {

        log.warn("handleBaseException", e);

        return handleExceptionInternal(e.getErrorCode());

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(final IllegalArgumentException e) {

        log.warn("handleIllegalArgument", e);

        return handleExceptionInternal(CommonErrorCode.INVALID_PARAMETER, e.getMessage());

    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException e,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request
    ) {

        log.warn("handleMethodArgumentNotValid", e);

        return handleExceptionInternal(e, CommonErrorCode.INVALID_PARAMETER);

    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAllException(final Exception e) {

        log.warn("handleAllException", e);

        return handleExceptionInternal(CommonErrorCode.INTERNAL_SERVER_ERROR);

    }

    private ResponseEntity<Object> handleExceptionInternal(final BindException e, final ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(e, errorCode));
    }

    private ResponseEntity<Object> handleExceptionInternal(final ErrorCode errorCode) {

        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ErrorResponse.of(errorCode.name(), errorCode.getMessage()));

    }

    private ResponseEntity<Object> handleExceptionInternal(final ErrorCode errorCode, final String message) {

        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ErrorResponse.of(errorCode.name(), message));

    }

    private ErrorResponse makeErrorResponse(final BindException e, final ErrorCode errorCode) {

        final List<ErrorResponse.ValidationError> validationErrorList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ErrorResponse.ValidationError::of)
                .collect(Collectors.toList());

        return ErrorResponse.of(errorCode.name(), errorCode.getMessage(), validationErrorList);

    }

}
