package br.unipar.programacaointernet.vendaapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class VendaDTO {
    public String nome;
    public BigDecimal valorTotal;
}
