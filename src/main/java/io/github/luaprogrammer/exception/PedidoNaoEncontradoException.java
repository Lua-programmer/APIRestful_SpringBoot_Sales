package io.github.luaprogrammer.exception;

public class PedidoNaoEncontradoException extends RuntimeException {
    public PedidoNaoEncontradoException() {
        super("Pedido não econtrado! ");
    }
}
