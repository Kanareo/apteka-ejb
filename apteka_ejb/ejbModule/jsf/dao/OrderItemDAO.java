package jsf.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jsf.entities.OrderItem;

@Stateless
public class OrderItemDAO {
    private final static String UNIT_NAME = "aptekaPU";;
    @PersistenceContext(unitName = UNIT_NAME)
    protected EntityManager em;

    public void create(OrderItem orderItem) {
        em.persist(orderItem);
    }

    public OrderItem merge(OrderItem orderItem) {
        return em.merge(orderItem);
    }
    
    public void remove(OrderItem orderItem) {
        em.remove(em.merge(orderItem));
    }

    public OrderItem find(Object id) {
        return em.find(OrderItem.class, id);
    }
    
    public List<OrderItem> getFullList() {
        List<OrderItem> list = null;

        Query query = em.createQuery("SELECT o FROM OrderItem o");

        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}