package br.unipar.programacaointernet.vendaapi.mapper;

import br.unipar.programacaointernet.vendaapi.dto.ClienteDTO;
import br.unipar.programacaointernet.vendaapi.model.Cliente;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteMapper {
    public ClienteDTO toDto(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNome(cliente.getNome());
        clienteDTO.setTelefone(cliente.getTelefone());
        clienteDTO.setAniversario(cliente.getAniversario());
        return clienteDTO;
    }
}
