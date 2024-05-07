package br.unipar.programacaointernet.vendaapi.controller;

import br.unipar.programacaointernet.vendaapi.dto.ClienteDTO;
import br.unipar.programacaointernet.vendaapi.dto.QuantidadeDTO;
import br.unipar.programacaointernet.vendaapi.dto.VendaDTO;
import br.unipar.programacaointernet.vendaapi.mapper.VendaMapper;
import br.unipar.programacaointernet.vendaapi.model.*;
import br.unipar.programacaointernet.vendaapi.service.ClienteService;
import br.unipar.programacaointernet.vendaapi.service.ProdutoService;
import br.unipar.programacaointernet.vendaapi.service.VendaService;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Path("/venda")
public class VendaController {

    @Inject
    private VendaService vendaService;

    @Inject
    private ClienteService clienteService;

    @Inject
    private ProdutoService produtoService;

    @Inject
    private VendaMapper vendaMapper;

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response listarVendas() {

        return Response.ok(vendaService.listar())
                .build();
    }


    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response listarVendaPorID(@PathParam("id") Integer id) {
        Venda venda = vendaService.buscarPorID(id);
        if (venda == null)
            return Response.status(204).entity("Venda não encontrada").build();

        return Response.ok(venda).build();
    }

    @GET
    @Path("/total")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response listarVendasPorValorTotal(@QueryParam("total") BigDecimal total) {
        List<Venda> vendas = vendaService.listarPorValorTotal(total);
        if (vendas.isEmpty())
            return Response.status(204).entity("Nenhuma venda encontrada com o valor total especificado").build();

        return Response.ok(vendas).build();
    }


    @POST
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response cadastrarVenda(Venda venda) {
        try {
            Cliente cliente = venda.getCliente();
            if (cliente != null && cliente.getId() == null) {
                clienteService.cadastrar(cliente);
            }

            for (ItensVenda item : venda.getItens()) {
                Produto produto = item.getProduto();
                if (produto != null && produto.getId() == null) {
                    produtoService.cadastrar(produto);
                }
            }

            vendaService.cadastrar(venda);

            return Response.status(201)
                    .entity(venda)
                    .build();
        } catch (Exception ex) {
            return Response.status(403).entity(ex.getMessage()).build();
        }
    }

    @PUT
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response editarVenda(Venda venda) {
        try {
            vendaService.editar(venda);
            return Response.status(200)
                    .entity(venda)
                    .build();
        } catch (Exception ex) {
            return Response.status(403).entity(ex.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response excluirVenda(@PathParam("id") Integer id) {
        try {
            Venda venda = vendaService.buscarPorID(id);
            if (venda == null) {
                return Response.status(204).entity("Venda não encontrado").build();
            }
            vendaService.excluir(venda);
            return Response.status(200)
                    .entity("Venda excluído com sucesso!")
                    .build();
        } catch (Exception ex) {
            return Response.status(403).entity(ex.getMessage()).build();
        }
    }

//    @GET
//    @Path("/vendaTotal")
//    @Produces(value = MediaType.APPLICATION_JSON)
//    public Response valorTotal() {
//        try {
//            ArrayList<VendaTotal> vendas = new ArrayList<>();
//            for(Venda objVenda: vendaService.listar()){
//                VendaTotal vendaTotal = new VendaTotal();
//                vendaTotal.setValorTotal(objVenda.getTotal());
//                vendaTotal.setNomeCliente(objVenda.getCliente().getNome());
//                vendas.add(vendaTotal);
//            }
//            return Response.status(200)
//                    .entity(vendas)
//                    .build();
//        } catch (Exception e) {
//            return null;
//        }
//    }

    @GET
    @Path("/vendaTotal")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response valorTotal() {
        try {
            List<Venda> listaVenda = vendaService.listar();

            ArrayList<VendaDTO> listaVendaDto = new ArrayList<>();

            for(Venda venda: listaVenda){
                VendaDTO vendaDTO = vendaMapper.toDto(venda);
                listaVendaDto.add(vendaDTO);
            }

            return Response.status(200)
                    .entity(listaVendaDto)
                    .build();
        } catch (Exception e) {
            return null;
        }
    }

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("/qtdVenda")
    public Response quantidadeVenda() {
        try {
            return Response.status(200).entity(vendaService.qtdVenda()).build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }


}
