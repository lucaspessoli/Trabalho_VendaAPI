package br.unipar.programacaointernet.vendaapi.repository;

import br.unipar.programacaointernet.vendaapi.model.Cliente;
import br.unipar.programacaointernet.vendaapi.model.Produto;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class ClienteRepository {

    @PersistenceContext(unitName = "HibernateMaven")
    private EntityManager em;

    public List<Cliente> listar() {
        String jpql = "SELECT p FROM Cliente p";
        return em.createQuery(jpql, Cliente.class).getResultList();
    }

    public Cliente buscarPorID(Integer id) {
        return em.find(Cliente.class, id);
    }

    public void cadastrar(Cliente cliente) throws Exception {
        try {
            em.persist(cliente);
        } catch (Exception ex) {
            throw new Exception("Cliente não pode ser cadastrado");
        }
    }

    public void editar(Cliente cliente) throws Exception {
        try {
            em.merge(cliente);
        } catch (Exception ex) {
            throw new Exception("Cliente não pode ser editado");
        }
    }

    public void excluir(Cliente cliente) throws Exception {
        try {
            em.remove(em.getReference(Cliente.class, cliente.getId()));
        } catch (Exception ex) {
            throw new Exception("Cliente não pode ser excluído.");
        }
    }
}
