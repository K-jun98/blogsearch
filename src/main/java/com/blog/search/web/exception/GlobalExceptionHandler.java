package com.blog.search.web.exception;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {


    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErrorResponse> handleBusinessException(ResponseStatusException exception, HttpServletRequest request) {
        log.error("{} occurs, {}", exception.getClass().getSimpleName(), exception.getMessage(), exception);

        ApiErrorResponse errorResponse = getNormalizeExceptionBody(exception.getStatusCode(), exception.getReason());
        return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
    }

    private ApiErrorResponse getNormalizeExceptionBody(HttpStatusCode httpStatusCode, String reason) {
        HttpStatus httpStatus = HttpStatus.valueOf(httpStatusCode.value());
        return new ApiErrorResponse(httpStatus.value(), httpStatus.getReasonPhrase(), reason);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleAll(Exception exception, HttpServletRequest request) {
        log.error("unknown exception occurs. request<{}>, body<{}>", request, exception.getMessage(), exception);

        ApiErrorResponse errorResponse = getNormalizeExceptionBody(INTERNAL_SERVER_ERROR, "Internal Server Error");
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
