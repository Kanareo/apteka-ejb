package jsf.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jsf.entities.Order;
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
    
    public List<OrderItem> getOrderItemsByID(Integer id){
    	List<OrderItem> list = null;
    	
    	Query query = em.createQuery("SELECT o FROM OrderItem o where o.order.idOrder=:id");
    	query.setParameter("id", id);

        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    	return list;
    }
    
    public List<OrderItem> getOrderItems(Order order){
    	List<OrderItem> list = null;
    	
    	list = this.getOrderItemsByID(order.getIdOrder());
    	
    	return list;
    }
}