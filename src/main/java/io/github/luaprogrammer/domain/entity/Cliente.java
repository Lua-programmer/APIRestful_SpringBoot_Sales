package io.github.luaprogrammer.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table( name = "cliente" )
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", length = 100)
    @NotEmpty(message = "Campo nome é obrigatório")
    private String nome;

    @Column(name = "cpf", length = 11)
    private String cpf;

    @JsonIgnore //ignora a propriedade
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY )
    private Set<Pedido> pedidos;
}