package io.github.luaprogrammer.service;

import io.github.luaprogrammer.domain.entity.Cliente;
import io.github.luaprogrammer.repository.ClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientesService {

    //Criando a dependência entre o ClienteRepository e o ClienteService
    final ClienteRepository clienteRepository;

    public ClientesService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public void salvarCliente(Cliente cliente) {
        validarCliente(cliente);
        // return clienteRepository.persistir(cliente);
    }

    public void validarCliente(Cliente cliente) {
        //aplica validações
    }
}
