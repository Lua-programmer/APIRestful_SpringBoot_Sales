package io.github.luaprogrammer.exception;

import org.springframework.http.HttpStatus;

public class Exception extends RuntimeException{
    public Exception(HttpStatus notFound, String message) {
        super(message);
    }
}
