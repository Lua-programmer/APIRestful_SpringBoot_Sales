package io.github.luaprogrammer.domain.repository;

import io.github.luaprogrammer.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Clientes extends JpaRepository<Cliente, Integer> {

    //select c from Clientes c where c.nome like :nome
    List<Cliente> findByNomeLike(String nome);

}
