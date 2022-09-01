package io.github.luaprogrammer.config.security.jwt;

import io.github.luaprogrammer.domain.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;


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

//        HashMap<String, Object> claims = new HashMap<>();
//        claims.put("emaildousuario", "usuario@gmail.com");
//        claims.put("roles", "admin");

        return Jwts
                .builder()
                .setSubject(usuario.getLogin()) //identificação
                .setExpiration(data) //expiração
                //.setClaims(claims)  //Para adicionar informações personalizadas ao token, recebe um hashMap
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura) // método que recebe dois parametros
                .compact(); //transforma tudo em uma string (token)
    }

    //metodo para obter as informações do token
    public Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser() //vai decodificar o token
                .setSigningKey(chaveAssinatura) //define a chave de assinatura
                .parseClaimsJws(token) //passa o token que vai codificar
                .getBody(); //vai retornar os claims do token
    }

    public String obterLoginUsuario(String token) throws ExpiredJwtException{
        return (String) obterClaims(token).getSubject();
    }

    public boolean tokenValido(String token) {
        try {
            Claims claims = obterClaims(token);
            Date dataExpiracao = claims.getExpiration();
            LocalDateTime localDateTime = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();//converter o objeto do tipo date em DateTime
            return !LocalDateTime.now().isAfter(localDateTime);
        } catch (Exception e) {
            return false;
        }
    }

    //Verificando se o token funciona
//    public static void main(String[] args) {
//        ConfigurableApplicationContext contexto = SpringApplication.run(VendasApplication.class);
//        JwtService service = contexto.getBean(JwtService.class);
//        Usuario usuario = Usuario.builder().login("fulano").build();
//        String token = service.gerarToken(usuario);
//        System.out.println(token);
//
//        boolean isTokenValido = service.tokenValido(token);
//        System.out.println("O token está válido? " + isTokenValido);
//
//        System.out.println(service.obterLoginUsuario(token));
//    }

}
