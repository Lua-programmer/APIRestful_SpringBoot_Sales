package io.github.luaprogrammer.service.impl;

import io.github.luaprogrammer.domain.repository.Pedidos;
import io.github.luaprogrammer.service.PedidoService;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImpl implements PedidoService {
    private Pedidos repository;

    public PedidoServiceImpl(Pedidos repository) {
        this.repository = repository;
    }
}
