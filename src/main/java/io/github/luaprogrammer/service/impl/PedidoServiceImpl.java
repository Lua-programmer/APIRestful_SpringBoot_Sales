package io.github.luaprogrammer.service.impl;

import io.github.luaprogrammer.domain.entity.Cliente;
import io.github.luaprogrammer.domain.entity.ItemPedido;
import io.github.luaprogrammer.domain.entity.Pedido;
import io.github.luaprogrammer.domain.entity.Produto;
import io.github.luaprogrammer.domain.repository.Clientes;
import io.github.luaprogrammer.domain.repository.ItemsPedido;
import io.github.luaprogrammer.domain.repository.Pedidos;
import io.github.luaprogrammer.domain.repository.Produtos;
import io.github.luaprogrammer.exception.Exception;
import io.github.luaprogrammer.rest.dto.ItemsPedidoDTO;
import io.github.luaprogrammer.rest.dto.PedidoDTO;
import io.github.luaprogrammer.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Service
public class PedidoServiceImpl implements PedidoService {
    private final Pedidos pedidosRepository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;

    private final ItemsPedido itemsPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository.findById(idCliente).orElseThrow(() -> new Exception(NOT_FOUND, "Código de cliente inválido"));


        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);

        List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());
        pedidosRepository.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);

        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidosRepository.findByIdFetchItens(id);
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemsPedidoDTO> items) {
        if (items.isEmpty()) {
            throw new Exception(NOT_FOUND, "Não é possível realizar um pedido sem items");
        }

        return items.stream().map(dto -> {
            Integer idProduto = dto.getProduto();
            Produto produto = produtosRepository.findById(idProduto).orElseThrow(() -> new Exception(NOT_FOUND, "Código de produto inválido: " + idProduto));

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setQuantidade(dto.getQuantidade());
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            return itemPedido;
        }).collect(Collectors.toList());
    }
}
