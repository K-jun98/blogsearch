package com.blog.search.web.exception;

public record ApiErrorResponse(int status, String error, String message) {

    public static ApiErrorResponse of(int status, String error, String message) {
        return new ApiErrorResponse(status, error, message);
    }

}
