package br.unipar.programacaointernet.vendaapi.repository;

import br.unipar.programacaointernet.vendaapi.model.Produto;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class ProdutoRepository {

    @PersistenceContext(unitName = "HibernateMaven")
    private EntityManager em;
    
    public List<Produto> listar() {
        String jpql = "SELECT p FROM Produto p";
        return em.createQuery(jpql, Produto.class).getResultList();
    }

    public Produto buscarPorID(Integer id) {
        return em.find(Produto.class, id);
    }

    public void cadastrar(Produto produto) throws Exception {
        try {
            em.persist(produto);
        } catch (Exception ex) {
            throw new Exception("Produto não pode ser cadastrado");
        }
    }

    public void editar(Produto produto) throws Exception {
        try {
            em.merge(produto);
        } catch (Exception ex) {
            throw new Exception("Produto não pode ser editado");
        }
    }

    public void excluir(Produto produto) throws Exception {
        try {
            em.remove(em.getReference(Produto.class, produto.getId()));
        } catch (Exception ex) {
            throw new Exception("Produto não pode ser excluído.");
        }
    }
}
