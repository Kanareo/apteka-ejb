package jsf.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jsf.entities.Order;

@Stateless
public class OrderDAO {
	Query query;
    private final static String UNIT_NAME = "aptekaPU";;
    @PersistenceContext(unitName = UNIT_NAME)
    protected EntityManager em;

    public void create(Order order) {
        em.persist(order);
    }

    public Order merge(Order order) {
        return em.merge(order);
    }
    
    public void remove(Order order) {
        em.remove(em.merge(order));
    }

    public Order find(Object id) {
        return em.find(Order.class, id);
    }
    
    public int getLastID() {
    	int lastID = 0;
    	List<Order> list = null;
    	Query query = em.createQuery("SELECT o FROM Order o Order By o.idOrder desc").setFirstResult(0).setMaxResults(1);
    	try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    	Order order = list.get(0);
    	lastID = order.getIdOrder();
    	//System.out.println(lastID);
    	return lastID;
    }
    
    public List<Order> getFullList() {
        List<Order> list = null;

        Query query = em.createQuery("SELECT o FROM Order o");

        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public List<Order> getLazyList(Map<String, Object> filterParams, int offset, int pageSize) {
		List<Order> list = null;

		String where = "where o.orderStatus =:param";
		query = em.createQuery("SELECT o FROM Order o " + where).setFirstResult(offset).setMaxResults(pageSize);
		query.setParameter("param", "Zamówione");
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public long countLazyList(Map<String, Object> filterParams) {
		long count = 0;

		String where = "where o.orderStatus =:param";
		query = em.createQuery("SELECT COUNT(o) FROM Order o " + where);
		query.setParameter("param", "Zamówione");

		try {
			count = (long) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}
	
	public boolean showDelivery() {
		long count = this.countLazyList(null);
		if(count >= 1) {
			return true;
		} 
		return false;
	}
}