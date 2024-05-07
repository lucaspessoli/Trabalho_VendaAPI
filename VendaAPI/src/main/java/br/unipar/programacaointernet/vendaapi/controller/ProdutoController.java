package br.unipar.programacaointernet.vendaapi.controller;

import br.unipar.programacaointernet.vendaapi.model.Produto;
import br.unipar.programacaointernet.vendaapi.service.ProdutoService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Stateless
@Path("/produto")
public class ProdutoController {

    @Inject
    private ProdutoService produtoService;

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response listarProdutos() {
        return Response.ok(produtoService.listar()).build();
    }

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response listarProdutoPorID(@PathParam("id") Integer id) {
        Produto produto = produtoService.buscarPorID(id);
        if(produto == null)
            return Response.status(204).entity("Produto não encontrado").build();

        return Response.ok(produto).build();
    }
    
    @POST
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response cadastrarProduto(Produto produto) {
        try {
            produtoService.cadastrar(produto);
            return Response.status(201)
                    .entity(produto)
                    .build();
        } catch (Exception ex) {
            return Response.status(403).entity(ex.getMessage()).build();
        }
    }

    @PUT
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response editarProduto(Produto produto) {
        try {
            produtoService.editar(produto);
            return Response.status(200)
                    .entity(produto)
                    .build();
        } catch (Exception ex) {
            return Response.status(403).entity(ex.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response excluirProduto(@PathParam("id") Integer id) {
        try {
            Produto produto = produtoService.buscarPorID(id);
            if (produto == null) {
                return Response.status(404).entity("Produto não encontrado").build();
            }
            produtoService.excluir(produto);
            return Response.status(200)
                    .entity("Produto excluído com sucesso!")
                    .build();
        } catch (Exception ex) {
            return Response.status(403).entity(ex.getMessage()).build();
        }
    }
}
