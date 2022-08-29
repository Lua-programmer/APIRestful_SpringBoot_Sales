package io.github.luaprogrammer.service;

import io.github.luaprogrammer.domain.entity.Pedido;
import io.github.luaprogrammer.rest.dto.PedidoDTO;

public interface PedidoService {
    Pedido salvar (PedidoDTO dto);
}
