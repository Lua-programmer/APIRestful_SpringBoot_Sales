package io.github.luaprogrammer.domain.repository;

import io.github.luaprogrammer.domain.entity.Cliente;
import io.github.luaprogrammer.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Pedidos extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);
}