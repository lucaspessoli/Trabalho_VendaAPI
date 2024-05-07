package br.unipar.programacaointernet.vendaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuantidadeDTO {
    private String nome;
    private Long quantidadeVenda;
}
