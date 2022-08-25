package io.github.luaprogrammer.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/clientes")
public class ClienteController {

    @RequestMapping(value = "/hello/{nome}", method = RequestMethod.GET)
    @ResponseBody //diz que a string que vai retornar é o corpo da resposta
    public String helloCliente( @PathVariable("nome") String nomeCliente) {
        return String.format("Hello %s", nomeCliente);
    }
}
