package io.github.luaprogrammer.rest.controller;

import io.github.luaprogrammer.domain.entity.Cliente;
import io.github.luaprogrammer.domain.entity.Produto;
import io.github.luaprogrammer.domain.repository.Produtos;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import static org.springframework.http.HttpStatus.*;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private Produtos produtos;

    public ProdutoController(Produtos produtos) {
        this.produtos = produtos;
    }

    @GetMapping("/{id}")
    public Produto getProdutoById(@PathVariable("id") Integer id) {
        return produtos.findById(id).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND, "Produto não encontrado"));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Transactional
    public Produto save(@RequestBody Produto produto) {
        return produtos.save(produto);
    }

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

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody Produto produto) {
        produtos.findById(id).map(produtoExistente -> {
            produto.setId(produtoExistente.getId());
            produtos.save(produto);
            return produtoExistente;
        }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado"));
    }

    @GetMapping
    public List<Produto> find(Produto filtro) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return produtos.findAll(example);
    }
}
