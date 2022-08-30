package io.github.luaprogrammer.service;

import io.github.luaprogrammer.domain.entity.Pedido;
import io.github.luaprogrammer.domain.enums.StatusPedido;
import io.github.luaprogrammer.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar (PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
