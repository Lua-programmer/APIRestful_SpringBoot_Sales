package io.github.luaprogrammer.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/clientes")
public class ClienteController {

    @RequestMapping(value = {"/hello/{nome}", "api/helo"}, //o value pode receber um array de strings
            method = RequestMethod.GET,
            consumes = { "application/json", "application/xml" }, //o consumer vai falar que tipo de dados essa requisição vai receber, mais é colocado em requisições POST
            produces = { "application/json", "application/xml" } //forma de retorno do Cliente
    )
    @ResponseBody //diz que a string que vai retornar é o corpo da resposta
    public String helloCliente( @PathVariable("nome") String nomeCliente) {
        return String.format("Hello %s", nomeCliente);
    }
}
