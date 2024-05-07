package br.unipar.programacaointernet.vendaapi.mapper;

import br.unipar.programacaointernet.vendaapi.dto.VendaDTO;
import br.unipar.programacaointernet.vendaapi.model.Venda;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VendaMapper {

    public VendaDTO toDto(Venda venda){
        VendaDTO vendaDTO = new VendaDTO();
        vendaDTO.setNome(venda.getCliente().getNome());
        vendaDTO.setValorTotal(venda.getTotal());
        return vendaDTO;
    }
}
