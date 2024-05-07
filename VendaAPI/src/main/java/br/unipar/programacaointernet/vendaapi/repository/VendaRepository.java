package br.unipar.programacaointernet.vendaapi.repository;

import br.unipar.programacaointernet.vendaapi.dto.QuantidadeDTO;
import br.unipar.programacaointernet.vendaapi.model.Venda;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Stateless
public class VendaRepository {

    @PersistenceContext(unitName = "HibernateMaven")
    private EntityManager em;


    public List<Venda> listar() {
        String jpql = "SELECT p FROM Venda p";
        return em.createQuery(jpql, Venda.class).getResultList();
    }

    public Venda buscarPorID(Integer id) {
        return em.find(Venda.class, id);
    }

    public List<Venda> buscarPorValorTotal(BigDecimal valor) {
        String jpql = "SELECT p FROM Venda p WHERE p.total = :valor";
        return em.createQuery(jpql, Venda.class).setParameter("valor", valor).getResultList();
    }

    public void cadastrar(Venda venda) throws Exception {
        try {
            em.persist(venda);
        } catch (Exception ex) {
            throw new Exception("Venda não pode ser cadastrado");
        }
    }

    public void editar(Venda venda) throws Exception {
        try {
            em.merge(venda);
        } catch (Exception ex) {
            throw new Exception("Venda não pode ser editado");
        }
    }

    public void excluir(Venda venda) throws Exception {
        try {
            em.remove(em.getReference(Venda.class, venda.getId()));
        } catch (Exception ex) {
            throw new Exception("Venda não pode ser excluído.");
        }
    }

    public List<QuantidadeDTO> qtdVenda() throws Exception{
      try {
           String jpql =
                   "SELECT c.nome, count(v.cliente.id) as qtdVenda FROM Venda v\n" +
                   "inner join Cliente c ON c.id = v.cliente.id\n" +
                   "group by c.nome";

           List<Object[]> listaObjeto = em.createQuery(jpql).getResultList();
           List<QuantidadeDTO> quantidadeDTOList = new ArrayList<>();

           for (Object[] objects : listaObjeto) {
               String nome = (String) objects[0];
               Long quantidadeVenda = (Long) objects[1];

               quantidadeDTOList.add(new QuantidadeDTO(nome, quantidadeVenda));
           }

           return quantidadeDTOList;
       }catch (NoResultException e){
          return null;
      }
    }
}
