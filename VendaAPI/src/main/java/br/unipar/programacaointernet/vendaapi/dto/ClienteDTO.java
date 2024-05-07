package br.unipar.programacaointernet.vendaapi.dto;


import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDTO {
    private String nome;
    private String telefone;
    private String aniversario;

}
