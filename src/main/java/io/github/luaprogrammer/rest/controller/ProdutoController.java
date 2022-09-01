package io.github.luaprogrammer.rest.controller;

import io.github.luaprogrammer.domain.entity.Produto;
import io.github.luaprogrammer.domain.repository.Produtos;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import static org.springframework.http.HttpStatus.*;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private Produtos produtos;

    public ProdutoController(Produtos produtos) {
        this.produtos = produtos;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/{id}")
    public Produto getProdutoById(@PathVariable("id") Integer id) {
        return produtos.findById(id).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND, "Produto não encontrado"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(CREATED)
    @Transactional
    public Produto save(@RequestBody @Valid Produto produto) {
        return produtos.save(produto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        produtos.findById(id)
                .map(cliente -> {
                    produtos.delete(cliente);
                    return cliente;
                })
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado"));

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable @Valid Integer id, @RequestBody Produto produto) {
        produtos.findById(id).map(produtoExistente -> {
            produto.setId(produtoExistente.getId());
            produtos.save(produto);
            return produtoExistente;
        }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado"));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping
    public List<Produto> find(Produto filtro) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return produtos.findAll(example);
    }
}
