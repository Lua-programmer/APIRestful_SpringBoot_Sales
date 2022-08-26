package io.github.luaprogrammer.domain.repository;

import io.github.luaprogrammer.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto,Integer> {
}