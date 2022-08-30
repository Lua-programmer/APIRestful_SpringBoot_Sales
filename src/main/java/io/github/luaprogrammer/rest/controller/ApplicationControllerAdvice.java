package io.github.luaprogrammer.rest.controller;


import io.github.luaprogrammer.exception.PedidoNaoEncontradoException;
import io.github.luaprogrammer.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleException(Exception e) {
        String msg = e.getMessage();
        return new ApiErrors(msg);
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlePedidoNotFoundException(PedidoNaoEncontradoException e) {
        return new ApiErrors(e.getMessage());
    }
}
