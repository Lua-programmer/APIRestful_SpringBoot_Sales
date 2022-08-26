package io.github.luaprogrammer.domain.repository;
import io.github.luaprogrammer.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsPedido extends JpaRepository<ItemPedido, Integer> {
}