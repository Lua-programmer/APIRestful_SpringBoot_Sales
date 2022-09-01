package io.github.luaprogrammer;

import io.github.luaprogrammer.domain.entity.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Service
public class JwtService {

    @Value("${security.jwt.expiracao}")
    private String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    //método para fazer a assinatura do token
    public String gerarToken(Usuario usuario) {
        long expString = Long.valueOf(expiracao);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString); //pegando a hora atual e somando os minutos da expiração
        Date data = Date.from(dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant()); //transformou o LOcalDateTime em um objeto TimeZone
        return Jwts
                .builder()
                .setSubject(usuario.getLogin()) //identificação
                .setExpiration(data) //expiração
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura) // método que recebe dois parametros
                .compact(); //transforma tudo em uma string (token)
    }

//    public static void main(String[] args) {
//        ConfigurableApplicationContext contexto = SpringApplication.run(VendasApplication.class);
//        JwtService service = contexto.getBean(JwtService.class);
//        Usuario usuario = Usuario.builder().login("fulano").build();
//        String token = service.gerarToken(usuario);
//        System.out.println(token);
//    }

}
