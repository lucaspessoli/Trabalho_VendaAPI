package br.unipar.programacaointernet.vendaapi.service;

import br.unipar.programacaointernet.vendaapi.dto.QuantidadeDTO;
import br.unipar.programacaointernet.vendaapi.model.ItensVenda;
import br.unipar.programacaointernet.vendaapi.model.Venda;
import br.unipar.programacaointernet.vendaapi.repository.VendaRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.List;

@Stateless
public class VendaService {


    @Inject
    private VendaRepository vendaRepository;

    public List<Venda> listar() {
        return vendaRepository.listar();
    }
    
    public Venda buscarPorID(Integer id) {
        return vendaRepository.buscarPorID(id);
    }

    public List<Venda> listarPorValorTotal(BigDecimal valor) {
        return vendaRepository.buscarPorValorTotal(valor);
    }
    
    public void cadastrar(Venda venda) throws Exception {
        for (ItensVenda item : venda.getItens()) {
            item.setVenda(venda);
        }

        vendaRepository.cadastrar(venda);
    }

    public void editar(Venda venda) throws Exception {
        vendaRepository.editar(venda);
    }

    public void excluir(Venda venda) throws Exception {
        vendaRepository.excluir(venda);
    }

    public List<QuantidadeDTO> qtdVenda() throws Exception{
       return vendaRepository.qtdVenda();
    }
}
