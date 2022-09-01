package io.github.luaprogrammer.rest.controller;

import io.github.luaprogrammer.domain.entity.Cliente;
import io.github.luaprogrammer.domain.repository.Clientes;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private Clientes clientes;

    public ClienteController(Clientes clientes) {
        this.clientes = clientes;
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/{id}")
    public Cliente getClienteById(@PathVariable("id") Integer id) {
        return clientes
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save(@RequestBody @Valid  Cliente cliente) {
        return clientes.save(cliente);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        clientes.findById(id)
                .map(cliente -> {
                    clientes.delete(cliente);
                    return cliente;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable @Valid Integer id, @RequestBody Cliente cliente) {
        clientes.findById(id).map(clienteExistente -> {
            cliente.setId(clienteExistente.getId());
            clientes.save(cliente);
            return clienteExistente;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping
    public List<Cliente> find(Cliente filtro) {
        ExampleMatcher matcher = ExampleMatcher.matching() //faz as configurações para clientes através das propriedades
                .withIgnoreCase() //Ignora os cases das strings, caixa alta ou baixa será a mesma coisa
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); //fora como a string será encontrada,

        Example example = Example.of(filtro, matcher);
        return clientes.findAll(example);
    }

}
